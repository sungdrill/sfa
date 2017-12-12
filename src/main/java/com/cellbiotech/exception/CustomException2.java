package com.cellbiotech.exception;

import com.cellbiotech.error.ErrorCodes;

/**
 * Created by user on 2017-08-29.
 */
public class CustomException2 extends BaseException {
    public CustomException2() {
        super(ErrorCodes.CUSTOM_EXCEPTION_2);
    }
}
