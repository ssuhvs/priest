package com.little.g.user.api;

import com.little.g.user.dto.TokenCache;
import com.little.g.user.dto.UserDeviceTokenDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by lengligang on 2019/3/27.
 */
public interface TokenService {

    /**
     * 生成token，并存储在缓存中
     *
     * @param uid 用户的唯一标识
     * @return
     */
    UserDeviceTokenDTO createToken(@NotBlank @Min(1) Long uid, @NotBlank @Size(min = 3, max = 50) String deviceId,
                                   @NotBlank Byte deviceType, @Size(min = 1, max = 100) String os);

    /**
     * token 验证 逻辑
     * @param deviceId
     * @param token
     * @return
     */
    TokenCache verify(@NotBlank @Size(min = 3, max = 50) String deviceId, @NotBlank @Size(min = 3,max = 50) String token);

    /**
     * 刷新token
     * @param uid
     * @param deviceId
     * @param deviceType
     * @param os
     * @param refreshToken
     * @return
     */
    UserDeviceTokenDTO refreshToken(@NotBlank @Min(1) Long uid, @NotBlank @Size(min = 3, max = 50) String deviceId,
                                    @NotBlank Byte deviceType, @Size(min = 1, max = 100) String os,String refreshToken);

    /**
     * 登出token
     * @param uid
     * @param deviceId
     * @param deviceType
     * @param os
     * @return
     */
    boolean logout(@NotBlank @Min(1) Long uid, @NotBlank @Size(min = 3, max = 50) String deviceId,
                   @NotBlank Byte deviceType, @Size(min = 1, max = 100) String os);

}
