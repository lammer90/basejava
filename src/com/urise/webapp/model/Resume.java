package com.urise.webapp.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.urise.webapp.model.section.Section;
import com.urise.webapp.model.section.SectionType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * com.urise.webapp.model.com.urise.webapp.model.Resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect
public class Resume implements Serializable{
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullname;
    @JsonDeserialize(as=EnumMap.class, keyAs = Contacts.class, contentAs = String.class)
    private Map<Contacts, String> contacts = new EnumMap<>(Contacts.class);
    @JsonDeserialize(as=EnumMap.class, keyAs = SectionType.class, contentAs = Section.class)
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName, String uuid) {
        this.fullname = fullName;
        this.uuid = uuid;
    }

    public Resume(String fullName){
        this.fullname = fullName;
        this.uuid = UUID.randomUUID().toString();
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

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        if (fullname != null ? !fullname.equals(resume.fullname) : resume.fullname != null) return false;
        if (contacts != null ? !contacts.equals(resume.contacts) : resume.contacts != null) return false;
        return sections != null ? sections.equals(resume.sections) : resume.sections == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (sections != null ? sections.hashCode() : 0);
        return result;
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

    public Map<Contacts, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }
}
