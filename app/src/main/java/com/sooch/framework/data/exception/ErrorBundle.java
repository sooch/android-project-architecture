package com.sooch.framework.data.exception;

/**
 * Created by Takashi Sou on 2016/08/09.
 */
public interface ErrorBundle {

    /**
     * 発生したExceptionのインスタンスを取得
     * @return
     */
    Exception getException();

    /**
     * 定義されたエラーメッセージを取得
     * @return
     */
    String getErrorMessage();
}
