package com.little.g.user;

import com.little.g.common.error.ErrorCodeDiv;
import com.little.g.common.error.ErrorCodes;

/**
 * Created by lengligang on 2019/3/12.
 */
public class UserErrorCodes extends ErrorCodes{

    public static final Integer USER_NOT_EXIST = 30001;
    
    public UserErrorCodes() {
        super(ErrorCodeDiv.USER);
    }

    @Override
    protected void addCodes() {
        addCode2Map(USER_NOT_EXIST,"user.not.exist");
    }
}
