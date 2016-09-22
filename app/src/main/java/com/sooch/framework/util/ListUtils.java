package com.sooch.framework.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Takashi Sou on 2016/09/12.
 */
public class ListUtils {

    /**
     * 引数のListがnullか長さが0ならtrueを返す
     *
     * @param list 対象
     * @param <T>  型パラメータ
     * @return 真偽
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    /**
     * Listから配列に変換
     *
     * @param list 変換元
     * @param ts   可変長引数
     * @param <T>  型パラメータ
     * @return
     */
    public static <T> T[] toArray(List<T> list, T... ts) {
        return list.toArray(ts);
    }

    /**
     * 配列からListに変換
     *
     * @param array 変換元
     * @param <T>   型パラメータ
     * @return
     */
    public static <T> List<T> toList(T... array) {
        List<T> l = new ArrayList<>();
        l.addAll(Arrays.asList(array));
        return l;
    }

    /**
     * Listの連結
     *
     * @param list1 対象1
     * @param list2 対象2
     * @param <T>   型パラメータ
     * @return
     */
    public static <T> List<T> union(List<T> list1, List<T> list2) {
        List<T> dst = new ArrayList<>(list1);
        dst.addAll(list2);
        return dst;
    }

    /**
     * 要素の反転
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> invert(List<T> list) {
        if (isEmpty(list)) {
            return list;
        }

        List<T> invertList = new ArrayList<T>(list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            invertList.add(list.get(i));
        }
        return invertList;
    }
}
