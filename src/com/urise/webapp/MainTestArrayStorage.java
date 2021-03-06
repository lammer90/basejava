package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.srorage.*;

import java.io.File;

/**
 * Test for com.urise.webapp.storage.com.urise.webapp.srorage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "1234lammer");

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "uuid1");
        Resume r2 = new Resume("uuid2", "uuid2");
        Resume r3 = new Resume("uuid3", "uuid3");

        Resume r4 = new Resume("uuid3", "uuid3");


        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r1);


        printAll();
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get r3: " + ARRAY_STORAGE.get("uuid3"));
        ARRAY_STORAGE.update(r4);
        System.out.println("Get r3: " + ARRAY_STORAGE.get("uuid3"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
