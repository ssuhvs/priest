package com.little.g.admin.web.util;

import com.google.common.collect.Lists;
import com.little.g.admin.common.annotation.Column;
import com.little.g.admin.common.annotation.ModuleManage;
import com.little.g.admin.common.annotation.ModuleOperation;
import com.little.g.admin.common.annotation.Table;
import com.little.g.admin.common.enums.LogType;
import com.little.g.admin.common.utils.FutureTaskUtil;
import com.little.g.admin.dto.LoggerActionDTO;
import com.little.g.admin.dto.LoggerContentDTO;
import com.little.g.admin.dto.LoggerDTO;
import com.little.g.admin.web.concurrent.CheckLoggerActionTask;
import com.little.g.admin.web.concurrent.CreateLoggerActionTask;
import com.little.g.admin.web.concurrent.CreateLoggerTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class LoggerUtil {
	
	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_GMT_MODIFIED = "gmt_modified";
	public static final String STRING_NULL_VALUE = "null";
	
	private LoggerUtil(){}
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LoggerUtil.class);
	
	private static final ThreadLocal<Future<LoggerActionDTO>> localLoggerActionFuture = new ThreadLocal<>();
	
	private static final ThreadLocal<List<Future<?>>> localFutureList = new ThreadLocal<>();

	public static <T> LoggerDTO getLogger(LogType logType, T oldObject, T newObject, String comment){
		if(oldObject == null && newObject == null){
			return null;
		}
		if(oldObject == null){
			logType = LogType.ADD;
		}else if(newObject == null){
			logType = LogType.DELETE;
		}else{
			logType = LogType.UPDATE;
		}
		LoggerDTO loggerDO = new LoggerDTO();
		loggerDO.setComment(comment);
		loggerDO.setLogType(logType.getCode());
		
		Class<?> clazz = getObjectClass(oldObject, newObject);
		if(!clazz.isAnnotationPresent(Table.class)){
			return null;
		}
		Table table = clazz.getAnnotation(Table.class);
		loggerDO.setTableName(table.name());
		loggerDO.setTableDescription(table.description());
		return loggerDO;
	}
	
	public static <T> List<LoggerContentDTO> getLoggerContents(T oldObject, T newObject) {
		if(oldObject == null && newObject == null){
			return null;
		}
		List<LoggerContentDTO> contents = new ArrayList<>();
		Class<?> clazz = getObjectClass(oldObject, newObject);
		Field[] fields = clazz.getDeclaredFields();
		
		Stream.of(fields)
			.filter(field -> field.isAnnotationPresent(Column.class))
			.forEach(field -> {
				Method method = getFieldReadMethod(field, clazz);
				String oldValue = invokeGetMethod(method, oldObject);
				String newValue = invokeGetMethod(method, newObject);
				if(STRING_NULL_VALUE.equals(String.valueOf(oldValue)) && 
					STRING_NULL_VALUE.equals(String.valueOf(newValue))){
					return;
				}
				Column column = field.getAnnotation(Column.class);
				if(StringUtils.equals(oldValue, newValue) &&
					!COLUMN_NAME_ID.equals(column.name()) || COLUMN_NAME_GMT_MODIFIED.equals(column.name())){
					return;
				}
				LoggerContentDTO content = getContent(column, newValue, oldValue);
				contents.add(content);
			});
		
		return contents;
	}
	
	public static void createLoggerAction(JoinPoint joinPoint, String userName, Integer uId){
		Future<LoggerActionDTO> future = FutureTaskUtil.getFuture(new CreateLoggerActionTask(joinPoint, userName,uId));
		setLocalLoggerActionFuture(future);
	}
	
	public static void checkLoggerAction(){
		FutureTaskUtil.getFuture(new CheckLoggerActionTask(getFutureList(), getLocalLoggerActionFuture()));
		clearFutureList();
		clearLocalLoggerActionFuture();
	}
	
	/**
	 * 检查是否存在Future<LoggerAction>对象，用在记录增删改日志之前的检查。
	 * 因为只有通过controller的操作才会记录人员操作日志，也只有这样的操作才会存在Future<LoggerAction>对象。
	 * 而AOP对于service的log不区分是否是由Controller发起的调用，
	 * 对于dubbo，MQ，scheduler的调用同样会记录，如果不加判断，会导致不必要的性能消耗。
	 * */
	public static boolean isNotExistLoggerActionFuture(){
	    return getLocalLoggerActionFuture() == null;
	}
	
	public static <T> void createLoggerForAdding(T after, String comment){
	    if(isNotExistLoggerActionFuture()){
	        return;
	    }
		Future<?> future = FutureTaskUtil.getFuture(new CreateLoggerTask(getLocalLoggerActionFuture(), LogType.ADD, null, after, comment));
		addFuture(future);
	}
	
	public static <T> void createLoggerForUpdating(T before, T after, String comment){
	    if(isNotExistLoggerActionFuture()){
            return;
        }
	    Future<?> future = FutureTaskUtil.getFuture(new CreateLoggerTask(getLocalLoggerActionFuture(), LogType.UPDATE, before, after, comment));
		addFuture(future);
	}
	
	public static <T> void createLoggerForDeleting(T before, String comment){
	    if(isNotExistLoggerActionFuture()){
            return;
        }
	    Future<?> future = FutureTaskUtil.getFuture(new CreateLoggerTask(getLocalLoggerActionFuture(), LogType.DELETE, before, null, comment));
		addFuture(future);
	}
	
	//检查contents信息是否合法
	public static boolean validContents(List<LoggerContentDTO> contents){
		//日志信息为空
		if(CollectionUtils.isEmpty(contents)){
			return false;
		}
		
		//日志信息除了"ID"和"最后修改时间"之外,什么都没有了,那么这个日志没什么意义
		boolean noUseful = contents.stream()
				.map(LoggerContentDTO::getColumnName)
				.filter(columnName -> !COLUMN_NAME_ID.equals(columnName))
				.filter(columnName -> !COLUMN_NAME_GMT_MODIFIED.equals(columnName))
				.count() <= 0;
		return !noUseful;
	}
	
	public static ModuleManage getModuleManage(JoinPoint joinPoint){
		return joinPoint.getTarget().getClass().getAnnotation(ModuleManage.class);
	}
	
	public static ModuleOperation getModuleOperation(JoinPoint joinPoint){
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(ModuleOperation.class);
	}
	
	public static void addFuture(Future<?> future){
		if(localFutureList.get() == null){
			localFutureList.set(Lists.newArrayList());
		}
		localFutureList.get().add(future);
	}
	
	public static List<Future<?>> getFutureList(){
		return localFutureList.get();
	}
	
	public static void clearFutureList(){
		localFutureList.remove();
	}
	
	public static void setLocalLoggerActionFuture(Future<LoggerActionDTO> future){
	    localLoggerActionFuture.set(future);
	}
	
	public static Future<LoggerActionDTO> getLocalLoggerActionFuture(){
		return localLoggerActionFuture.get();
	}
	
	public static void clearLocalLoggerActionFuture(){
	    localLoggerActionFuture.remove();
	}
	
	public static Method getFieldReadMethod(Field field, Class<?> clazz){
		try {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
			return pd.getReadMethod();
		} catch (Exception e) {
			LOG.info("getFieldReadMethod exception: -> {}", e.getMessage());
			return null;
		}
	}

	private static String invokeGetMethod(Method method, Object object){
		try {
			if(method == null || object == null){
				return null;
			}
			Object returnValue = method.invoke(object);
			if(returnValue == null){
				return null;
			}
			if(returnValue instanceof Date){
				return DateFormatUtils.ISO_DATETIME_FORMAT.format(returnValue);
			}
			return String.valueOf(returnValue);
		} catch (Exception e) {
			LOG.info("invokeGetMethod exception: -> {}", e);
			return null;
		}
	}
	
	private static LoggerContentDTO getContent(Column column, String newValue, String oldValue){
		LoggerContentDTO content = new LoggerContentDTO();
		content.setColumnName(column.name());
		content.setColumnDescription(column.description());
		content.setOldValue(oldValue);
		content.setNewValue(newValue);
		return content;
	}
	
	private static <T> Class<?> getObjectClass(T oldObject, T newObject){
		if(oldObject != null){
			return oldObject.getClass();
		}
		if(newObject != null){
			return newObject.getClass();
		}
		return null;
	}
}
