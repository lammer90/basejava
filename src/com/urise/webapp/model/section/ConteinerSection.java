package com.urise.webapp.model.section;

import java.util.ArrayList;
import java.util.List;

public class ConteinerSection extends Section<List<Conteiner>>{
    List<Conteiner> conteiners = new ArrayList<>();

    public ConteinerSection(){
    }

    public ConteinerSection(List<Conteiner> conteiners) {
        this.conteiners = conteiners;
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
