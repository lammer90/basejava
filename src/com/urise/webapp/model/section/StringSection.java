package com.urise.webapp.model.section;

import com.urise.webapp.model.section.Section;

public class StringSection extends Section<String> {
    private static final long serialVersionUID = 1L;

    private String text = "";

    public StringSection() {
    }

    public StringSection(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringSection)) return false;

        StringSection that = (StringSection) o;

        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getInformation() {
        return text;
    }

    @Override
    public void setNewInformation(String inf) {
        this.text = inf;
    }
}
