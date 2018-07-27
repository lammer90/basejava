package com.urise.webapp.model.section;

import com.urise.webapp.model.section.Section;

import java.util.ArrayList;
import java.util.List;

public class ArraySection extends Section<List<String>> {
    private List<String> strings = new ArrayList<>();

    public ArraySection() {
    }

    public ArraySection(List<String> strings) {
        this.strings = strings;
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
