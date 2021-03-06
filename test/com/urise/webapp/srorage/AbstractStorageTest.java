package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.*;
import com.urise.webapp.util.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final String file = "C:\\git_tutorial\\work\\hello\\basejava\\storage";

    protected Storage storage;

    protected static final String UUID_1 = "uuid1_______________________________";
    protected static final String UUID_2 = "uuid2_______________________________";
    protected static final String UUID_3 = "uuid3_______________________________";

    protected static final Resume resume1;
    protected static final Resume resume2;
    protected static final Resume resume3;

    static {
        resume1 = newResume("Name 1", UUID_1);
        resume2 = newResume("Name 2", UUID_2);
        resume3 = newResume("Name 3", UUID_3);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void reset() throws Exception{
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    public static Resume newResume(String str, String uuid){
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
        Resume r = newResume("New name", UUID_1);
        storage.update(r);
        assertTrue(storage.get(UUID_1).equals(r));
    }

    @Test
    public void get() throws Exception {
        assertTrue(storage.get(UUID_1).equals(resume1));
        assertTrue(storage.get(UUID_2).equals(resume2));
        assertTrue(storage.get(UUID_3).equals(resume3));
    }

    @Test
    public void save() throws Exception {
        Resume r = newResume("Name 4", "uuid4_______________________________");
        storage.save(r);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(storage.get("uuid4_______________________________"), r);
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
        Assert.assertEquals(storage.getAllSorted().get(1), resume2);
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
        storage.save(newResume("Name 2", UUID_2));
    }
}