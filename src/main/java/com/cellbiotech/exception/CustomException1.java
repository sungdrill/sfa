package com.cellbiotech.exception;

import com.cellbiotech.error.ErrorCodes;

/**
 * Created by user on 2017-08-29.
 */
public class CustomException1 extends BaseException {
    public CustomException1() {
        super(ErrorCodes.CUSTOM_EXCEPTION_1);
    }
}
