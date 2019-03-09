package com.little.g.common.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;

/**
 * support multi-jars property file scan
 * @author wangzhen
 */
public class ReloadableResourceBundleMessageSource extends AbstractMessageSource implements ResourceLoaderAware {
    private static final Logger logger = LoggerFactory.getLogger(ReloadableResourceBundleMessageSource.class);

    private static final String PROPERTIES_SUFFIX = ".properties";
    private static final String XML_SUFFIX = ".xml";
    private String[] basenames = new String[0];
    private String defaultEncoding;
    private Properties fileEncodings;
    private boolean fallbackToSystemLocale = true;
    private long cacheMillis = -1;
    private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    /**
     * Cache to hold filename lists per Locale
     */
    private final Map<String, Map<Locale, List<String>>> cachedFilenames =
            new HashMap<String, Map<Locale, List<String>>>();
    /**
     * Cache to hold already loaded properties per filename
     */
    private final Map<String, PropertiesHolder> cachedProperties =
            new HashMap<String, PropertiesHolder>();
    /**
     * Cache to hold merged loaded properties per locale
     */
    private final Map<Locale, PropertiesHolder> cachedMergedProperties =
            new HashMap<Locale, PropertiesHolder>();


    /**
     * Set a single basename, following the basic ResourceBundle convention of not specifying file
     * extension or language codes, but in contrast to
     * {@link org.springframework.context.support.ResourceBundleMessageSource} referring to a Spring resource location: e.g.
     * "WEB-INF/messages" for "WEB-INF/messages.properties", "WEB-INF/messages_en.properties", etc.
     * <p>XML properties files are also supported: .g. "WEB-INF/messages" will find and load
     * "WEB-INF/messages.xml", "WEB-INF/messages_en.xml", etc as well.
     *
     * @param basename the single basename
     * @see #setBasenames
     * @see org.springframework.core.io.ResourceEditor
     * @see ResourceBundle
     */
    public void setBasename(String basename) {
        setBasenames(new String[]{basename});
    }

    /**
     * Set an array of basenames, each following the basic ResourceBundle convention of not
     * specifying file extension or language codes, but in contrast to
     * {@link org.springframework.context.support.ResourceBundleMessageSource} referring to a Spring resource location: e.g.
     * "WEB-INF/messages" for "WEB-INF/messages.properties", "WEB-INF/messages_en.properties", etc.
     * <p>XML properties files are also supported: .g. "WEB-INF/messages" will find and load
     * "WEB-INF/messages.xml", "WEB-INF/messages_en.xml", etc as well. <p>The associated resource
     * bundles will be checked sequentially when resolving a message code. Note that message
     * definitions in a <i>previous</i> resource bundle will override ones in a later bundle, due to
     * the sequential lookup.
     *
     * @param basenames an array of basenames
     * @see #setBasename
     * @see ResourceBundle
     */
    public void setBasenames(String[] basenames) {
        if (basenames != null) {
            this.basenames = new String[basenames.length];
            for (int i = 0; i < basenames.length; i++) {
                String basename = basenames[i];
                Assert.hasText(basename, "Basename must not be empty");
                this.basenames[i] = basename.trim();
            }
        } else {
            this.basenames = new String[0];
        }
    }

    /**
     * Set the default charset to use for parsing properties files. Used if no file-specific charset
     * is specified for a file. <p>Default is none, using the
     * <code>java.util.Properties</code> default encoding. <p>Only applies to classic properties
     * files, not to XML files.
     *
     * @param defaultEncoding the default charset
     * @see #setFileEncodings
     * @see PropertiesPersister#load
     */
    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    /**
     * Set per-file charsets to use for parsing properties files. <p>Only applies to classic
     * properties files, not to XML files.
     *
     * @param fileEncodings Properties with filenames as keys and charset names as values. Filenames
     *                      have to match the basename syntax, with optional locale-specific appendices: e.g.
     *                      "WEB-INF/messages" or "WEB-INF/messages_en".
     * @see #setBasenames
     * @see PropertiesPersister#load
     */
    public void setFileEncodings(Properties fileEncodings) {
        this.fileEncodings = fileEncodings;
    }

    /**
     * Set whether to fall back to the system Locale if no files for a specific Locale have been
     * found. Default is "true"; if this is turned off, the only fallback will be the default file
     * (e.g. "messages.properties" for basename "messages"). <p>Falling back to the system Locale is
     * the default behavior of
     * <code>java.util.ResourceBundle</code>. However, this is often not desirable in an application
     * server environment, where the system Locale is not relevant to the application at all: Set
     * this flag to "false" in such a scenario.
     */
    public void setFallbackToSystemLocale(boolean fallbackToSystemLocale) {
        this.fallbackToSystemLocale = fallbackToSystemLocale;
    }

    /**
     * Set the number of seconds to cache loaded properties files. <ul> <li>Default is "-1",
     * indicating to cache forever (just like
     * <code>java.util.ResourceBundle</code>). <li>A positive number will cache loaded properties
     * files for the given number of seconds. This is essentially the interval between refresh
     * checks. Note that a refresh attempt will first check the last-modified timestamp of the file
     * before actually reloading it; so if files don't change, this interval can be set rather low,
     * as refresh attempts will not actually reload. <li>A value of "0" will check the last-modified
     * timestamp of the file on every message access. <b>Do not use this in a production
     * environment!</b> </ul>
     */
    public void setCacheSeconds(int cacheSeconds) {
        this.cacheMillis = (cacheSeconds * 1000);
    }

    /**
     * Set the PropertiesPersister to use for parsing properties files. <p>The default is a
     * DefaultPropertiesPersister.
     *
     * @see DefaultPropertiesPersister
     */
    public void setPropertiesPersister(PropertiesPersister propertiesPersister) {
        this.propertiesPersister =
                (propertiesPersister != null ? propertiesPersister
                        : new DefaultPropertiesPersister());
    }

    /**
     * Set the ResourceLoader to use for loading bundle properties files. <p>The default is a
     * DefaultResourceLoader. Will get overridden by the ApplicationContext if running in a context,
     * as it implements the ResourceLoaderAware interface. Can be manually overridden when running
     * outside of an ApplicationContext.
     *
     * @see DefaultResourceLoader
     * @see ResourceLoaderAware
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader != null ? resourceLoader
                : new DefaultResourceLoader());
    }

    /**
     * Resolves the given message code as key in the retrieved bundle files, returning the value
     * found in the bundle as-is (without MessageFormat parsing).
     */
    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        if (this.cacheMillis < 0) {
            PropertiesHolder propHolder = getMergedProperties(
                    locale);

            if (propHolder != null) {
                logger.info("bundle expire,reload bundle messages," + propHolder.getProperties());

                String result = propHolder.getProperty(code);
                if (result != null) {
                    return result;
                }
            }

        } else {
            for (String basename : this.basenames) {
                List<String> filenames = calculateAllFilenames(basename, locale);

                for (String filename : filenames) {
                    PropertiesHolder propHolder =
                            getProperties(filename);
                    String result = propHolder.getProperty(code);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Resolves the given message code as key in the retrieved bundle files, using a cached
     * MessageFormat instance per message code.
     */
    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        if (this.cacheMillis < 0) {
            PropertiesHolder propHolder = getMergedProperties(
                    locale);

            if (propHolder != null) {
                logger.info("bundle expire,reload bundle messages," + propHolder.getProperties());

                MessageFormat result = propHolder.getMessageFormat(code, locale);
                if (result != null) {
                    return result;
                }
            }

        } else {
            for (String basename : this.basenames) {
                List<String> filenames = calculateAllFilenames(basename, locale);
                for (String filename : filenames) {
                    PropertiesHolder propHolder =
                            getProperties(filename);
                    MessageFormat result = propHolder.getMessageFormat(code, locale);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Get a PropertiesHolder that contains the actually visible properties for a Locale, after
     * merging all specified resource bundles. Either fetches the holder from the cache or freshly
     * loads it. <p>Only used when caching resource bundle contents forever, i.e. with cacheSeconds
     * < 0. Therefore, merged properties are always cached forever.
     */
    protected PropertiesHolder getMergedProperties(
            Locale locale) {
        synchronized (this.cachedMergedProperties) {
            PropertiesHolder mergedHolder =
                    this.cachedMergedProperties.get(locale);
            if (mergedHolder != null) {
                return mergedHolder;
            }
            Properties mergedProps = new Properties();
            mergedHolder = new PropertiesHolder(mergedProps,
                    -1);
            for (int i = this.basenames.length - 1; i >= 0; i--) {
                List filenames = calculateAllFilenames(this.basenames[i], locale);
                for (int j = filenames.size() - 1; j >= 0; j--) {
                    String filename = (String) filenames.get(j);
                    PropertiesHolder propHolder =
                            getProperties(filename);
                    if (propHolder.getProperties() != null) {
                        mergedProps.putAll(propHolder.getProperties());
                    }
                }
            }
            this.cachedMergedProperties.put(locale, mergedHolder);
            return mergedHolder;
        }
    }

    /**
     * Calculate all filenames for the given bundle basename and Locale. Will calculate filenames
     * for the given Locale, the system Locale (if applicable), and the default file.
     *
     * @param basename the basename of the bundle
     * @param locale   the locale
     * @return the List of filenames to check
     * @see #setFallbackToSystemLocale
     * @see #calculateFilenamesForLocale
     */
    protected List<String> calculateAllFilenames(String basename, Locale locale) {
        synchronized (this.cachedFilenames) {
            Map<Locale, List<String>> localeMap = this.cachedFilenames.get(basename);
            if (localeMap != null) {
                List<String> filenames = localeMap.get(locale);
                if (filenames != null) {
                    return filenames;
                }
            }
            List<String> filenames = new ArrayList<String>(7);
            filenames.addAll(calculateFilenamesForLocale(basename, locale));
            if (this.fallbackToSystemLocale && !locale.equals(Locale.getDefault())) {
                List<String> fallbackFilenames = calculateFilenamesForLocale(basename, Locale.
                        getDefault());
                for (String fallbackFilename : fallbackFilenames) {
                    if (!filenames.contains(fallbackFilename)) {
                        // Entry for fallback locale that isn't already in filenames list.
                        filenames.add(fallbackFilename);
                    }
                }
            }
            filenames.add(basename);
            if (localeMap != null) {
                localeMap.put(locale, filenames);
            } else {
                localeMap = new HashMap<Locale, List<String>>();
                localeMap.put(locale, filenames);
                this.cachedFilenames.put(basename, localeMap);
            }
            return filenames;
        }
    }

    /**
     * Calculate the filenames for the given bundle basename and Locale, appending language code,
     * country code, and variant code. E.g.: basename "messages", Locale "de_AT_oo" ->
     * "messages_de_AT_OO", "messages_de_AT", "messages_de". <p>Follows the rules defined by {@link Locale#toString()}.
     *
     * @param basename the basename of the bundle
     * @param locale   the locale
     * @return the List of filenames to check
     */
    protected List<String> calculateFilenamesForLocale(String basename, Locale locale) {
        List<String> result = new ArrayList<String>(3);
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        StringBuilder temp = new StringBuilder(basename);

        temp.append('_');
        if (language.length() > 0) {
            temp.append(language);
            result.add(0, temp.toString());
        }

        temp.append('_');
        if (country.length() > 0) {
            temp.append(country);
            result.add(0, temp.toString());
        }

        if (variant.length() > 0 && (language.length() > 0 || country.length() > 0)) {
            temp.append('_').append(variant);
            result.add(0, temp.toString());
        }

        return result;
    }

    /**
     * Get a PropertiesHolder for the given filename, either from the cache or freshly loaded.
     *
     * @param filename the bundle filename (basename + Locale)
     * @return the current PropertiesHolder for the bundle
     */
    protected PropertiesHolder getProperties(String filename) {
        synchronized (this.cachedProperties) {
            PropertiesHolder propHolder =
                    this.cachedProperties.get(filename);
            if (propHolder != null && (propHolder.getRefreshTimestamp() < 0 || propHolder.
                    getRefreshTimestamp() > System.currentTimeMillis() - this.cacheMillis)) {
                // up to date
                return propHolder;
            }
            return refreshProperties(filename, propHolder);
        }
    }

    /**
     * Refresh the PropertiesHolder for the given bundle filename. The holder can be
     * <code>null</code> if not cached before, or a timed-out cache entry (potentially getting
     * re-validated against the current last-modified timestamp).
     *
     * @param filename   the bundle filename (basename + Locale)
     * @param propHolder the current PropertiesHolder for the bundle
     */
    protected PropertiesHolder refreshProperties(
            String filename, PropertiesHolder propHolder) {
        long refreshTimestamp = (this.cacheMillis < 0) ? -1 : System.currentTimeMillis();

        Resource[] resources = null;
        try {
            if (this.resourceLoader instanceof ResourcePatternResolver) {
                logger.debug("resolve property files by pattern:{},resourceLoader:{}", filename
                        + PROPERTIES_SUFFIX, resourceLoader);
                resources = ((ResourcePatternResolver) this.resourceLoader).getResources(filename
                        + PROPERTIES_SUFFIX);
                if (resources == null || resources.length == 0) {
                    resources = ((ResourcePatternResolver) this.resourceLoader).getResources(filename
                            + XML_SUFFIX);
                }
            } else {
                Resource resource = this.resourceLoader.getResource(filename + PROPERTIES_SUFFIX);
                if (!resource.exists()) {
                    resource = this.resourceLoader.getResource(filename + XML_SUFFIX);
                }
                resources = new Resource[1];
                resources[0] = resource;
            }

            if (resources != null && resources.length > 0) {
                propHolder = new PropertiesHolder();
                for (Resource resource : resources) {
                    if (resource.exists()) {
                        long fileTimestamp = -1;
                        if (this.cacheMillis >= 0) {
                            fileTimestamp = resource.lastModified();

                            /*if (propHolder.getFileTimestamp() == fileTimestamp) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Re-caching properties for filename [" + filename + "] - file hasn't been modified");
                                }
                                propHolder.setRefreshTimestamp(refreshTimestamp);
                                return propHolder;
                            }*/
                        }

                        try {
                            Properties props = loadProperties(resource, filename);
                            if (propHolder.getProperties() != null) {
                                propHolder.getProperties().putAll(props);
                            } else {
                                propHolder.properties = props;
                            }
                            propHolder.fileTimestamp = fileTimestamp;
                        } catch (IOException ex) {
                            if (logger.isWarnEnabled()) {
                                logger.warn("Could not parse properties file [" + resource.
                                        getFilename()
                                        + "]", ex);
                            }
                        }
                    } else {
                        // Resource does not exist.
                        if (logger.isDebugEnabled()) {
                            logger.debug("No properties file found for [" + resource.getFilename()
                                    + "] - neither plain properties nor XML");
                        }
                    }
                }
            } else {
                // Resource does not exist.
                if (logger.isInfoEnabled()) {
                    logger.info("No properties files found for [" + filename
                            + "] - neither plain properties nor XML");
                }
                // Empty holder representing "not found".
                propHolder = new PropertiesHolder();
            }
        } catch (IOException iOException) {
            if (logger.isWarnEnabled()) {
                logger.warn("Could not match pattern [" + filename
                        + "]", iOException);
            }
            // Empty holder representing "not valid".
            propHolder = new PropertiesHolder();
        }

        propHolder.setRefreshTimestamp(refreshTimestamp);
        this.cachedProperties.put(filename, propHolder);
        return propHolder;
    }

    /**
     * Load the properties from the given resource.
     *
     * @param resource the resource to load from
     * @param filename the original bundle filename (basename + Locale)
     * @return the populated Properties instance
     * @throws IOException if properties loading failed
     */
    protected Properties loadProperties(Resource resource, String filename) throws IOException {
        InputStream is = resource.getInputStream();
        Properties props = new Properties();
        try {
            if (resource.getFilename().endsWith(XML_SUFFIX)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Loading properties [" + resource.getFilename() + "]");
                }
                this.propertiesPersister.loadFromXml(props, is);
            } else {
                String encoding = null;
                if (this.fileEncodings != null) {
                    encoding = this.fileEncodings.getProperty(filename);
                }
                if (encoding == null) {
                    encoding = this.defaultEncoding;
                }
                if (encoding != null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Loading properties [" + resource.getFilename()
                                + "] with encoding '" + encoding + "'");
                    }
                    this.propertiesPersister.load(props, new InputStreamReader(is, encoding));
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Loading properties [" + resource.getFilename() + "]");
                    }
                    this.propertiesPersister.load(props, is);
                }
            }
            return props;
        } finally {
            is.close();
        }
    }

    /**
     * Clear the resource bundle cache. Subsequent resolve calls will lead to reloading of the
     * properties files.
     */
    public void clearCache() {
        logger.debug("Clearing entire resource bundle cache");
        synchronized (this.cachedProperties) {
            this.cachedProperties.clear();
        }
        synchronized (this.cachedMergedProperties) {
            this.cachedMergedProperties.clear();
        }
    }

    /**
     * Clear the resource bundle caches of this MessageSource and all its ancestors.
     *
     * @see #clearCache
     */
    public void clearCacheIncludingAncestors() {
        clearCache();
        if (getParentMessageSource() instanceof ReloadableResourceBundleMessageSource) {
            ((ReloadableResourceBundleMessageSource) getParentMessageSource()).
                    clearCacheIncludingAncestors();
        }
    }

    @Override
    public String toString() {
        return getClass().getName() + ": basenames=["
                + StringUtils.arrayToCommaDelimitedString(this.basenames) + "]";
    }

    /**
     * PropertiesHolder for caching. Stores the last-modified timestamp of the source file for
     * efficient change detection, and the timestamp of the last refresh attempt (updated every time
     * the cache entry gets re-validated).
     */
    protected class PropertiesHolder {

        private Properties properties;
        private long fileTimestamp = -1;
        private long refreshTimestamp = -1;
        /**
         * Cache to hold already generated MessageFormats per message code
         */
        private final Map<String, Map<Locale, MessageFormat>> cachedMessageFormats =
                new HashMap<String, Map<Locale, MessageFormat>>();

        public PropertiesHolder(Properties properties, long fileTimestamp) {
            this.properties = properties;
            this.fileTimestamp = fileTimestamp;
        }

        public PropertiesHolder() {
        }

        public Properties getProperties() {
            return properties;
        }

        public long getFileTimestamp() {
            return fileTimestamp;
        }

        public void setRefreshTimestamp(long refreshTimestamp) {
            this.refreshTimestamp = refreshTimestamp;
        }

        public long getRefreshTimestamp() {
            return refreshTimestamp;
        }

        public String getProperty(String code) {
            if (this.properties == null) {
                return null;
            }
            return this.properties.getProperty(code);
        }

        public MessageFormat getMessageFormat(String code, Locale locale) {
            if (this.properties == null) {
                return null;
            }
            synchronized (this.cachedMessageFormats) {
                Map<Locale, MessageFormat> localeMap = this.cachedMessageFormats.get(code);
                if (localeMap != null) {
                    MessageFormat result = localeMap.get(locale);
                    if (result != null) {
                        return result;
                    }
                }
                String msg = this.properties.getProperty(code);
                if (msg != null) {
                    if (localeMap == null) {
                        localeMap = new HashMap<Locale, MessageFormat>();
                        this.cachedMessageFormats.put(code, localeMap);
                    }
                    MessageFormat result = createMessageFormat(msg, locale);
                    localeMap.put(locale, result);
                    return result;
                }
                return null;
            }
        }
    }
}
