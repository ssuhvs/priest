package com.little.g.user.api;

import com.little.g.user.dto.UserDTO;

import javax.validation.constraints.NotEmpty;

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

}
