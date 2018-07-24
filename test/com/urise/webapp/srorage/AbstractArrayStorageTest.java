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

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final int STORAGE_LIMIT = 10000;

    @Before
    public void reset(){
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void update() throws Exception{
        Resume r = new Resume(UUID_1);
        storage.update(r);
        assertTrue(storage.get(UUID_1) == r);
    }

    @Test
    public void get() throws Exception{
        assertTrue(storage.get(UUID_1).getUuid().equals(UUID_1));
        assertTrue(storage.get(UUID_2).getUuid().equals(UUID_2));
        assertTrue(storage.get(UUID_3).getUuid().equals(UUID_3));
    }

    @Test
    public void save() throws Exception{
        Resume r = new Resume("uuid4");
        storage.save(r);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(storage.get("uuid4"), r);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception{
        storage.delete(UUID_2);
        Assert.assertEquals(2,storage.size());
        storage.get(UUID_2);
    }

    @Test
    public void clear() throws Exception{
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() throws Exception{
        Assert.assertEquals(3,storage.getAll().length);
        Assert.assertEquals(storage.getAll()[1].getUuid(), UUID_2);
    }

    @Test
    public void size() throws Exception{
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception{
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception{
        storage.save(new Resume(UUID_2));
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