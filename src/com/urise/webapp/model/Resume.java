package com.urise.webapp.model;

import java.util.UUID;

/**
 * com.urise.webapp.model.com.urise.webapp.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid + " " + super.toString();
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.hashCode() - o.uuid.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;

        Resume resume = (Resume) o;

        return uuid != null ? uuid.equals(resume.uuid) : resume.uuid == null;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
