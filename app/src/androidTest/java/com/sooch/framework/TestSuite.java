package com.sooch.framework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Takashi Sou on 2016/07/22.
 */
// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationTest.class,
        SomeTest.class,
})
public class TestSuite {
}
