package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{

    private static final int STORAGE_LIMIT = 10000;

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void arrayIsFull() throws Exception{
        int i = 4;
        try{
            for (i = 4; i <= STORAGE_LIMIT + 1; i++){
                storage.save(new Resume("uuid" + i));
            }
        }
        catch (StorageException e){
            if (i == STORAGE_LIMIT + 1) {
                assertTrue(true);
            }
            else {
                fail("something went wrong");
            }
        }
        catch (Exception e){
            fail("something else went wrong");
        }
    }
}