package com.sooch.framework.data.exception;

/**
 * Created by Takashi Sou on 2016/08/23.
 */
public class SomeException extends Exception {

    public SomeException(String detailMessage) {
        super(detailMessage);
    }

    public SomeException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }
}
