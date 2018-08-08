package com.urise.webapp.srorage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        ObjectStreamStorageTestFile.class,
        ObjectStreamStorageTestPath.class,
        JSONStreamStorageTestFile.class,
        DataStreamStorageTestFile.class
} )
public class AllTests {

}