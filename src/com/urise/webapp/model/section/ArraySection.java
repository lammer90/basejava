package com.urise.webapp.model.section;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.urise.webapp.model.section.Section;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ArraySection extends Section<List<String>>{
    private static final long serialVersionUID = 1L;

    @JsonDeserialize(as=ArrayList.class, contentAs = String.class)
    private List<String> strings = new ArrayList<>();

    public ArraySection() {
    }

    public ArraySection(String... strings) {
        this.strings = Arrays.asList(strings);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArraySection)) return false;

        ArraySection that = (ArraySection) o;

        return strings != null ? strings.equals(that.strings) : that.strings == null;
    }

    @Override
    public int hashCode() {
        return strings != null ? strings.hashCode() : 0;
    }

    @Override
    public String toString() {
        return strings.toString();
    }

    public List<String> getInformation() {
        return strings;
    }

    @Override
    public void setNewInformation(List<String> inf) {
        this.strings = inf;
    }
}
