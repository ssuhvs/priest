package com.little.g.user.web.form;

import com.google.common.base.Strings;
import com.little.g.common.utils.PhoneUtil;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * User: liuling
 * Date: 16/2/24
 * Time: 下午5:12
 */
public class BaseMobileParams extends BaseDeviceParams {

    @NotNull
    protected String mobile;

    @Length(min = 1, max = 10)
    private String countryCode = PhoneUtil.DEFAULT_COUNTRY_CODE;//手机号码的国际区号

    @AssertTrue(message = "手机号码格式有误")
    private boolean isLegalNumber() {
        if (!Strings.isNullOrEmpty(mobile) && !Strings.isNullOrEmpty(countryCode)) {
            return PhoneUtil.verifyNationalNumber(mobile, countryCode);
        }
        return true;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = PhoneUtil.preCheck(mobile);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        if (!Strings.isNullOrEmpty(countryCode)) {
            this.countryCode = PhoneUtil.preCheck(countryCode);
        } else {
            this.countryCode = PhoneUtil.DEFAULT_COUNTRY_CODE;
        }
    }

    public String getNationalMobile() {
        return PhoneUtil.mergeCountryCodePhone(countryCode, mobile);
    }


    @Override
    public String toString() {
        return "BaseParams{" +
                "mobile='" + mobile + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
