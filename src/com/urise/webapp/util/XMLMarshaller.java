package com.urise.webapp.util;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class XMLMarshaller {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XMLMarshaller(Class...classes){
        try {
            JAXBContext context = JAXBContext.newInstance(classes);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new StorageException("JAXB Error", null, e);
        }
    }

    public Object unmarshal(Reader reader){
        try {
            return unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new StorageException("JAXB read Error", null, e);
        }
    }

    public void marshal(Writer writer, Resume resume){
        try {
            marshaller.marshal(resume, writer);
        } catch (JAXBException e) {
            throw new StorageException("JAXB write Error", null, e);
        }
    }

}
