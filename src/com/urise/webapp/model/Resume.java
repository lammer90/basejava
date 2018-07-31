package com.urise.webapp.model;

import com.urise.webapp.model.section.Section;
import com.urise.webapp.model.section.SectionType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * com.urise.webapp.model.com.urise.webapp.model.Resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;
    private String fullname;
    private Map<Contacts, String> contacts = new HashMap<>();
    private Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName, String uuid) {
        this.fullname = fullName;
        this.uuid = uuid;
        //initializeFields();
    }

    public Resume(String fullName){
        this.fullname = fullName;
        this.uuid = UUID.randomUUID().toString();
        //initializeFields();
    }

    private void initializeFields(){
        for (Contacts contact : Contacts.values()){
            contacts.put(contact, "");
        }

        for (SectionType sectionType : SectionType.values()){
            try {
                Section section = (Section) sectionType.getaClass().newInstance();
                sections.put(sectionType, section);
            }
            catch (Exception e){}
        }
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullname='" + fullname + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
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

    public Map<Contacts, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public void setContacts(Map<Contacts, String> contacts) {
        this.contacts = contacts;
    }

    public void setSections(Map<SectionType, Section> sections) {
        this.sections = sections;
    }
}
