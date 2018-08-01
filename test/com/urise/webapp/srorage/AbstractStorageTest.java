package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.*;
import com.urise.webapp.model.util.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File file = new File("C:\\git_tutorial\\work\\hello\\basejava\\storage");

    protected Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void reset() throws Exception{
        storage.clear();
        storage.save(newResume("Name 1", UUID_1));
        storage.save(newResume("Name 2", UUID_2));
        storage.save(newResume("Name 3", UUID_3));
    }

    public Resume newResume(String str, String uuid) throws Exception {
        Resume resume = new Resume("Name " + str, uuid);

        for (SectionType type : SectionType.values()){
            if (type.getaClass() == StringSection.class) {
                resume.addSectionn(type, new StringSection(str));
            }
            else if (type.getaClass() == ArraySection.class) {
                resume.addSectionn(type, new ArraySection(str + "*1", str + "*2", str + "*3"));
            }
            else {
                resume.addSectionn(type, new ConteinerSection(
                        new Conteiner("http// " + str, str + "*name",
                                new Conteiner.Period(DateUtil.of(2015, Month.APRIL), DateUtil.of(2016, Month.APRIL), str + "*title1",str + "*text1"),
                                new Conteiner.Period(DateUtil.of(2016, Month.APRIL), DateUtil.of(2017, Month.APRIL), str + "*title2",str + "*text2")),
                        new Conteiner("http// " + str, str + "*name2",
                                new Conteiner.Period(DateUtil.of(2015, Month.AUGUST), DateUtil.of(2016, Month.AUGUST), str + "*title4",str + "*text7"),
                                new Conteiner.Period(DateUtil.of(2016, Month.AUGUST), DateUtil.of(2017, Month.AUGUST), str + "*title5",str + "*text8")
                )));
            }
        }
        for (Contacts contact : Contacts.values()){
            resume.addContact(contact,contact.getTitle() + " " + str);
        }
        return resume;
    }

    @Test
    public void update() throws Exception {
        Resume r = new Resume("New name", UUID_1);
        storage.update(r);
        assertTrue(storage.get(UUID_1).equals(r));
    }

    @Test
    public void get() throws Exception {
        assertTrue(storage.get(UUID_1).getUuid().equals(UUID_1));
        assertTrue(storage.get(UUID_2).getUuid().equals(UUID_2));
        assertTrue(storage.get(UUID_3).getUuid().equals(UUID_3));
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume("Name 4", "uuid4");
        storage.save(r);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(storage.get("uuid4"), r);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(3, storage.getAllSorted().size());
        Assert.assertEquals(storage.getAllSorted().get(1).getUuid(), UUID_2);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception {
        storage.save(new Resume(UUID_2, UUID_2));
    }
}