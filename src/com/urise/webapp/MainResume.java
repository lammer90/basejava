package com.urise.webapp;

import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.Section;
import com.urise.webapp.model.section.SectionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainResume {
    public static void main(String[] args) {
        Resume resume = new Resume("Плотников Дмитрий Александрович");

        Map<Contacts, String> contacts = resume.getContacts();
        Map<SectionType, Section> sections = resume.getSections();

        contacts.put(Contacts.HOMEPAGE, "http//fjgndfjngfrng");
        contacts.put(Contacts.LINKEDIN, "dhfth");
        contacts.put(Contacts.PHONE, "76457--4564564-456456-3456");


        Section section1 = sections.get(SectionType.PERSONAL);

        System.out.println(resume);

    }
}
