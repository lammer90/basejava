package com.urise.webapp.srorage;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest{
    public SqlStorageTest() {
        super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "1234lammer"));
    }
}