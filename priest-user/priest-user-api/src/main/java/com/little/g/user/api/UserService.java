package com.little.g.user.api;

import com.little.g.common.ResultJson;
import com.little.g.common.validate.annatations.DeviceId;
import com.little.g.common.validate.annatations.DeviceType;
import com.little.g.user.dto.UserDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by lengligang on 2019/3/22.
 */
public interface UserService {
    /**
     * 根据手机号查询g用户
     * @param mobile
     * @return
     */
    UserDTO getUserInfoByMobile(@NotEmpty String mobile);

    /**
     * 创建用户
     * @param mobile
     * @param smscode
     * @param deviceId
     * @param deviceType
     * @return
     */
    ResultJson joinin(@NotEmpty String mobile, @NotBlank @Size(min = 4, max = 6) String smscode,@NotBlank @DeviceId String deviceId,@DeviceType Byte deviceType,@Size(max = 30) String os);
}
