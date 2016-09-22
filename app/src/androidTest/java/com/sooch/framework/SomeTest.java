package com.sooch.framework;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;


/**
 * Created by Takashi Sou on 2016/09/12.
 */
@RunWith(AndroidJUnit4.class)
public class SomeTest {

    @Test
    public void test() throws Exception {
        String actual = "android";
        assertThat(actual, is("android"));
    }

}
