package com.little.g.user.web.form;


import com.little.g.common.validate.annatations.DeviceId;
import com.little.g.common.validate.annatations.DeviceType;

import javax.validation.constraints.NotNull;

/**
 * 设备基类
 * User: erin
 * Date: 14-10-17
 * Time: 下午4:11
 */
public class BaseDeviceParams extends BaseParams {

    @NotNull(message = "deviceId not allowed null")
    @DeviceId
    protected String deviceId;  //设备id
//    @NotNull(message = "deviceType not allowed null")
    @DeviceType
    protected Integer deviceType;//用户的设备类型

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }
}
