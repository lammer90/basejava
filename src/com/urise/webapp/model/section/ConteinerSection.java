package com.urise.webapp.model.section;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ConteinerSection extends Section<List<Conteiner>>{
    private static final long serialVersionUID = 1L;

    @JsonDeserialize(as=ArrayList.class, contentAs = Conteiner.class)
    List<Conteiner> conteiners = new ArrayList<>();

    public ConteinerSection(){
    }

    public ConteinerSection(Conteiner... conteiners) {
        this.conteiners = Arrays.asList(conteiners);
    }

    @Override
    public String toString() {
        return conteiners.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConteinerSection)) return false;

        ConteinerSection that = (ConteinerSection) o;

        return conteiners != null ? conteiners.equals(that.conteiners) : that.conteiners == null;
    }

    @Override
    public int hashCode() {
        return conteiners != null ? conteiners.hashCode() : 0;
    }

    public List<Conteiner> getInformation() {
        return conteiners;
    }

    @Override
    public void setNewInformation(List<Conteiner> inf) {
        this.conteiners = inf;
    }
}
