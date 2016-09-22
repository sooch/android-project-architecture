package com.sooch.framework.data.network;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * リトライファンクション {@link Func1}. 使用する際は{@link Observable}に対して,
 * {@link Observable#retryWhen(Func1)}オペレータを指定する.
 *
 * <p>サンプルコード {@code Observable.retryWhen(new HttpRetryPolicy(maxRetries, interval))...}.
 *
 * Created by Takashi Sou on 2016/08/19.
 */
public class HttpRetryPolicy implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final long retryInterval;
    private int retryCount;

    /**
     * {@link HttpRetryPolicy(int, long)}
     */
    public HttpRetryPolicy() {
        this(2, 1000);
    }

    /**
     * コンストラクタ
     * @param maxRetries リトライ数
     * @param retryInterval リトライインターバル(ミリ秒)
     */
    public HttpRetryPolicy(final int maxRetries, final long retryInterval) {
        this.maxRetries = maxRetries;
        this.retryInterval = retryInterval;
        this.retryCount = 0;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (++retryCount < maxRetries) {
                            return Observable.timer(retryInterval, TimeUnit.MILLISECONDS);
                        }

                        return Observable.error(throwable);
                    }
                });
    }
}
