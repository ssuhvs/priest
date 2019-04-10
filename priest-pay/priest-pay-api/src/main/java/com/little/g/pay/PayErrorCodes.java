package com.little.g.pay;

import com.little.g.common.error.ErrorCodeDiv;
import com.little.g.common.error.ErrorCodes;

/**
 * Created by lengligang on 2019/3/12.
 */
public class PayErrorCodes extends ErrorCodes{

    public static final Integer PAY_ERROR = 40000;


    public PayErrorCodes() {
        super(ErrorCodeDiv.PAY);
    }

    @Override
    protected void addCodes() {
        addCode2Map(PAY_ERROR,"pay error");
    }
}
