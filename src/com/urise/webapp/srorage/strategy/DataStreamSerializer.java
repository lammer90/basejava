package com.urise.webapp.srorage.strategy;

import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StrategySerialization {

    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getFullname());
            dos.writeUTF(r.getUuid());
            Map<Contacts, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<Contacts, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            for (SectionType type : SectionType.values()){
                Section section = r.getSection(type);
                dos.writeInt(section == null ? 0 : 1);
                if (section instanceof StringSection){
                    dos.writeUTF(((StringSection)section).getInformation());
                }
                else if (section instanceof ArraySection){
                    List<String> strings = ((ArraySection)section).getInformation();
                    dos.writeInt(strings.size());
                    for (String s : strings){
                        dos.writeUTF(s);
                    }
                }
                else if (section instanceof ConteinerSection){
                    List<Conteiner> conteiners = ((ConteinerSection)section).getInformation();
                    dos.writeInt(conteiners.size());
                    for (Conteiner conteiner : conteiners){
                        dos.writeUTF(conteiner.getHomePage());
                        dos.writeUTF(conteiner.getName());
                        List<Conteiner.Period> periods = conteiner.getPeriods();
                        dos.writeInt(periods.size());
                        for (Conteiner.Period period : periods){
                            dos.writeUTF(period.getStartDate().toString());
                            dos.writeUTF(period.getEndDate().toString());
                            dos.writeUTF(period.getTitle());
                            dos.writeUTF(period.getText());
                        }
                    }
                }
            }

        }
    }

    @Override
    public Resume getResumeFromFile(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(),  dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(Contacts.valueOf(dis.readUTF()), dis.readUTF());
            }
            for (SectionType type : SectionType.values()){
                if (dis.readInt() == 1) {
                    if (type.getaClass() == StringSection.class) {
                        resume.addSectionn(type, new StringSection(dis.readUTF()));
                    }
                    else if (type.getaClass() == ArraySection.class) {
                        ArraySection arraySection = new ArraySection();
                        resume.addSectionn(type, arraySection);
                        int arSize = dis.readInt();
                        List<String> newStrings = new ArrayList<>();
                        for (int i = 0; i < arSize; i++) {
                            newStrings.add(dis.readUTF());
                        }
                        arraySection.setNewInformation(newStrings);
                    }
                    else if (type.getaClass() == ConteinerSection.class) {
                        ConteinerSection conteinerSection = new ConteinerSection();
                        resume.addSectionn(type, conteinerSection);
                        int arSizeCont = dis.readInt();
                        List<Conteiner> conteiners = new ArrayList<>();
                        for (int i = 0; i < arSizeCont; i++) {
                            Conteiner newConteiner = new Conteiner(dis.readUTF(), dis.readUTF());
                            conteiners.add(newConteiner);

                            int arSizePer = dis.readInt();
                            List<Conteiner.Period> periods = new ArrayList<>();
                            for (int j = 0; j < arSizePer; j++) {
                                periods.add(new Conteiner.Period(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            }
                            newConteiner.setPeriods(periods);
                        }
                        conteinerSection.setNewInformation(conteiners);
                    }
                }
            }
            return resume;
        }
    }
}