package com.urise.webapp.srorage.strategy;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.*;
import com.urise.webapp.util.XMLMarshaller;

import java.io.*;

public class XMLStreamStorage implements StrategySerialization{
    private XMLMarshaller marshaller = new XMLMarshaller(Resume.class,
            Contacts.class,
            SectionType.class,
            StringSection.class,
            ArraySection.class,
            Conteiner.class,
            ConteinerSection.class,
            Conteiner.Period.class);

    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(os)) {
            marshaller.marshal(osw, r);
        }
    }

    @Override
    public Resume getResumeFromFile(InputStream is) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(is)) {
            return (Resume) marshaller.unmarshal(isr);
        }
    }
}
