package com.sooch.framework.util;

import android.util.AndroidRuntimeException;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by Takashi Sou on 2016/09/12.
 */
public class AESUtils {

    /**
     * デフォルト暗号化キー(256bit)
     * 128, 192, 256bitから選択可能.
     */
    private static final String DEFAULT_ENCRYPT_KEY = "e8ffc7e56311679f12b6fc91aa77a5eb";

    /**
     * CBC(Cipher Block Chain)モード用の初期化ベクトル.
     * <p>
     * <p>この初期化ベクトルは最初の平文ブロックに対してのXOR演算に使用される.
     * AES暗号化のブロック長が128bitのため, 初期化ベクトルは128bitを指定する.
     */
    private static final String DEFAULT_IV = "abcdefghijklmnop";

    /**
     * 文字を取り扱う際のデフォルトエンコード.
     */
    private static final String ENCODING = "UTF-8";

    /**
     * コンストラクタ.
     */
    private AESUtils() {
    }

    /**
     * 暗号化
     *
     * @param value 暗号化対象文字列
     * @return 暗号化文字列
     */
    public static String encrypt(String value) {
        return encrypt(value, DEFAULT_ENCRYPT_KEY, DEFAULT_IV);
    }

    /**
     * 文字列の暗号化を行う. 暗号化した文字列を取得するために, Base64を使用して暗号文をエンコードします.
     *
     * @param value 暗号化対象文字列
     * @param key 共通キー
     * @param iv 初期化ベクトル(IV)
     * @return 暗号化文字列
     */
    public static String encrypt(String value, String key, String iv) {
        return Base64.encodeToString(encrypt(getBytes(value), key, iv), Base64.NO_WRAP);
    }

    /**
     * 暗号化
     *
     * @param value 暗号化対象文字列
     * @param key 共通キー
     * @param iv 初期化ベクトル(IV)
     * @return 暗号化文字列
     */
    public static byte[] encrypt(byte[] value, String key, String iv) {
        try {
            Cipher cipher = createCipher(Cipher.ENCRYPT_MODE, key, iv);
            return cipher.doFinal(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 復号化
     *
     * @param value 復号化対象文字列
     * @return 復号化文字列
     */
    public static String decrypt(String value) {
        return decrypt(value, DEFAULT_ENCRYPT_KEY, DEFAULT_IV);
    }

    /**
     * 文字列をBase64デコードした後に復号化します.
     *
     * @param value 暗号化対象文字列
     * @param key 共通キー
     * @param iv 初期化ベクトル(IV)
     * @return 復号化文字列
     */
    public static String decrypt(String value, String key, String iv) {
        return new String(decrypt(Base64.decode(value, Base64.NO_WRAP), key, iv));
    }

    /**
     * byte列を復号化する.
     * <p>対象のブロックのサイズが不正な場合, パディングが不正な場合 は{@code value}の値が不正です.
     *
     * @param value 復号化対象のbyte列.
     * @param key 暗号化に使用したキー.
     * @param iv 暗号化に使用した初期化ベクトル.
     * @return 復号化されたbyte列.
     */
    public static byte[] decrypt(byte[] value, String key, String iv) {
        try {
            Cipher cipher = createCipher(Cipher.DECRYPT_MODE, key, iv);
            return cipher.doFinal(value);
        } catch (Exception e) {
            throw new AndroidRuntimeException(e);
        }
    }

    /**
     * 暗号化クラスのオブジェクトを取得
     * <p>{@code Cipher.getInstance("AES/CBC/PKCS5Padding")}に指定しているアルゴリズムが不正な場合,
     * もしくはパディングが不正な場合, 例外を投げプログラムを終了する. この場合, 指定しているパラメータを見直すこと.
     *
     * @param encryptMode 暗号化モード
     * @param key 暗号化キー
     * @param iv 初期化ベクトル
     * @return 初期化されたCipherオブジェクト.
     * @throws InvalidKeyException キー長が128, 192, 256bitでない場合.
     * @throws InvalidAlgorithmParameterException 初期化ベクトルが128bitでない場合.
     */
    private static Cipher createCipher(int encryptMode, String key, String iv)
            throws InvalidKeyException, InvalidAlgorithmParameterException {

        try {
            SecretKeySpec sksSpec = new SecretKeySpec(getBytes(key), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(getBytes(iv));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(encryptMode, sksSpec, ivSpec);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new AndroidRuntimeException(e);
        }
    }

    /**
     * {@link String#getBytes(Charset)}のラッパーメソッド.
     * <p>{@link #ENCODING}がサポートしていない文字エンコードの場合, 例外を投げプログラムを終了する.
     * この場合は文字エンコードを見直すこと.
     *
     * @param key 文字列.
     * @return {@code key}のバイト列.
     */
    private static byte[] getBytes(String key) {
        try {
            return key.getBytes(ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new AndroidRuntimeException(e);
        }
    }
}
