package com.urise.webapp.model;

import com.urise.webapp.model.section.Section;
import com.urise.webapp.model.section.SectionType;

import java.io.Serializable;
import java.util.*;

/**
 * com.urise.webapp.model.com.urise.webapp.model.Resume class
 */
public class Resume implements Serializable{
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullname;
    private Map<Contacts, String> contacts = new EnumMap<>(Contacts.class);
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

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

    public String getContact(Contacts contact) {
        return contacts.get(contact);
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void addContact(Contacts contact, String text) {
        contacts.put(contact, text);
    }

    public void addSectionn(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }
}
