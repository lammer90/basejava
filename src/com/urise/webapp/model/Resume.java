package com.urise.webapp.model;

import java.util.UUID;

/**
 * com.urise.webapp.model.com.urise.webapp.model.Resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;
    private String fullname;

    public Resume(String fullName, String uuid) {
        this.fullname = fullName;
        this.uuid = uuid;
    }

    public Resume(String fullName) {
        this.fullname = fullName;
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return fullname + " " + super.toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    //@Override
    //public int compareTo(Resume o) {
    //    return uuid.hashCode() - o.uuid.hashCode();
    //}

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
