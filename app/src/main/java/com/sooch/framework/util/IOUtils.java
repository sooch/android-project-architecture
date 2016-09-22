package com.sooch.framework.util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/**
 * このクラスは下記パーミッションを使用する.
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * Created by Takashi Sou on 2015/05/12.
 */
public class IOUtils {

    /**
     * 文字の読み込み・書き込みに使用するエンコーディング.
     */
    private static final String ENCODING = "UTF-8";

    /**
     * {@code src}をbyte配列で返却する.
     *
     * @param src InputStream object.
     * @return byte array.
     * @throws IOException 書き込みに失敗した場合.
     */
    public static byte[] readAsBytes(InputStream src) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream os = new BufferedOutputStream(baos);
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = src.read(buf)) != -1) {
                os.write(len);
            }
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
        return baos.toByteArray();
    }

    /**
     * {@code src}をStringで返却する.
     * <p>
     * {@link InputStreamReader#InputStreamReader(InputStream)} (InputStream, Charset)}により,
     * 文字エンコーディング{@value #ENCODING}を指定しています.
     * <p>
     * このメソッドは{@link StringBuilder}を使用しているため, 排他制御が掛かっていません.
     * 複数のスレッドから同時に呼び出しを行わないで下さい.
     *
     * @param src InputStream object.
     * @return byte array.
     * @throws IOException 書き込みに失敗した場合.
     */
    public static String readAsString(InputStream src) throws IOException {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            reader = new BufferedReader(new InputStreamReader(src, ENCODING));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return sb.toString();
    }

    /**
     * {@code file}をStringで返却する.
     *
     * @param file 読み込み元.
     * @return String object.
     * @throws IOException ファイルを開けない場合.
     */
    public static String readFileAsString(File file) throws IOException {
        return readAsString(new FileInputStream(file));
    }

    /**
     * {@code file}をbyte配列で返却する.
     *
     * @param file 読み込み元.
     * @return byte array.
     * @throws IOException ファイルを開けない場合.
     */
    public static byte[] readFileAsBytes(File file) throws IOException {
        return readAsBytes(new FileInputStream(file));
    }

    /**
     * assets配下の{@code fileName}をStringで返却する.
     *
     * @param context  コンテキスト.
     * @param fileName 読み込むファイル名.
     * @return String object.
     * @throws IOException ファイルを開けない場合.
     */
    public static String readAssetsAsString(Context context, String fileName) throws IOException {
        return readAsString(context.getAssets().open(fileName));
    }

    /**
     * assets配下の{@code fileName}をbyte配列で返却する.
     *
     * @param context  コンテキスト.
     * @param fileName 読み込むファイル名.
     * @return byte array.
     * @throws IOException ファイルを開けない場合.
     */
    public static byte[] readAssetsAsBytes(Context context, String fileName) throws IOException {
        return readAsBytes(context.getAssets().open(fileName));
    }

    /**
     * assets配下の{@code fileName}をInputStreamで返却する.
     *
     * @param context  コンテキスト.
     * @param fileName 読み込むファイル名.
     * @return InputStream Object.
     * @throws IOException ファイルを開けない場合.
     */
    public static InputStream readAssetsAsStream(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }

    /**
     * {@code text}をテキストファイルとして書き込む.
     *
     * @param text    文字列.
     * @param dstPath 出力先パス.
     * @throws IOException 書き込みに失敗した場合.
     */
    public static void writeAsText(String text, @NonNull String dstPath) throws IOException {
        File file = makeFileIfNecessary(dstPath);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), ENCODING));
        try {
            bw.write(text);
            bw.flush();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    /**
     * {@code src}を{@code dst}に書き込む.
     * <p>
     * {@code src}はメソッド内で{@link InputStream#close()}を行う.
     * <p>
     * {@code os#write, os#flush}において書き込み処理に失敗した場合, {@code os#close}において
     * 未処理のバッファが存在した場合, {@link IOException}がthrowされる.
     *
     * @param src 書き込み内容.
     * @param out 書き込み先.
     * @throws IOException 書き込みに失敗した場合.
     */
    public static void copy(InputStream src, OutputStream out) throws IOException {
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = src.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.flush();
        } finally {
            if (src != null) {
                src.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * {@code src}を{@code dst}に書き込む.
     *
     * @param src 書き込み内容.
     * @param dst 書き込み先.
     * @throws IOException 書き込みに失敗した場合.
     */
    public static void copy(File src, File dst) throws IOException {
        copy(new FileInputStream(src), new FileOutputStream(dst));
    }

    /**
     * {@code srcPath}を{@code dstPath}に書き込む.
     *
     * @param srcPath 書き込み元ファイルパス.
     * @param dstPath 書き込み先ファイルパス.
     * @throws IOException 書き込みに失敗した場合.
     */
    public static void copy(@NonNull String srcPath, @NonNull String dstPath) throws IOException {
        copy(new File(srcPath), makeFileIfNecessary(dstPath));
    }

    /**
     * {@code filePath}が存在しない場合はFileを作成する.
     * <p>
     * ディレクトリが存在しない場合は, ディレクトリごと作成する.
     *
     * @param filePath ファイルパス.
     * @return File object.
     */
    public static File makeFileIfNecessary(@NonNull String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }
}
