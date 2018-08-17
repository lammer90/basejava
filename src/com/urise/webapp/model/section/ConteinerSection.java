package com.urise.webapp.model.section;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.urise.webapp.util.JSONConverter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ConteinerSection extends Section<List<Conteiner>>{
    private static final long serialVersionUID = 1L;

    @JsonDeserialize(as=ArrayList.class, contentAs = Conteiner.class)
    @JsonProperty("conteiners")
    List<Conteiner> conteiners = new ArrayList<>();

    public ConteinerSection(){
    }

    public ConteinerSection(Conteiner... conteiners) {
        this.conteiners = Arrays.asList(conteiners);
    }

    @Override
    public String toString() {
        try {
            String result = "";
            for (Conteiner con : conteiners) {
                result = result + JSONConverter.write(con) + System.lineSeparator();
            }
            return result;
        }
        catch (IOException e){
            return "";
        }
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

    @JsonIgnore
    public List<Conteiner> getInformation() {
        return conteiners;
    }

    @Override
    public void setNewInformation(List<Conteiner> inf) {
        this.conteiners = inf;
    }
}
