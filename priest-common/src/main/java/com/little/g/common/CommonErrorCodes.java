package com.little.g.common;

import com.little.g.common.error.ErrorCodeDiv;
import com.little.g.common.error.ErrorCodes;

/**
 * Created by lengligang on 2019/3/12.
 */
public  class CommonErrorCodes extends ErrorCodes{

    public static final Integer SYSTEM_LIMIT = 10001;
    public static final Integer NOT_LOGIN = 10010;

    public CommonErrorCodes(ErrorCodeDiv.CodeBorder border) {
        super(border);
    }




    @Override
    protected void addCodes() {

        addCode2Map(NOT_LOGIN,"user.not.login");
        addCode2Map(SYSTEM_LIMIT,"user.not.exist");
    }
}
