package com.little.g.common;

import com.little.g.common.error.ErrorCodeDiv;
import com.little.g.common.error.ErrorCodes;

/**
 * Created by lengligang on 2019/3/12.
 */
public class CommonErrorCodes extends ErrorCodes{

    public static final Integer SYSTEM_LIMIT = 10001;

    static {
        start=ErrorCodeDiv.COMMON.getStart();
        end = ErrorCodeDiv.COMMON.getEnd();

        addCode2Map(SYSTEM_LIMIT,"user.not.exist");
    }

    public CommonErrorCodes(ErrorCodeDiv.CodeBorder border) {
        super(border);
    }
}
