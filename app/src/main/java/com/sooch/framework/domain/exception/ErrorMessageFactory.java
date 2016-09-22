package com.sooch.framework.domain.exception;

import android.content.Context;

import com.sooch.framework.R;
import com.sooch.framework.data.exception.SomeException;

/**
 * Created by Takashi Sou on 2016/08/24.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
        //empty
    }

    /**
     * エラーメッセージの生成
     * @param context
     * @param exception
     * @return
     */
    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof SomeException) {
            message = context.getString(R.string.exception_message_something);
        }
        return message;
    }
}
