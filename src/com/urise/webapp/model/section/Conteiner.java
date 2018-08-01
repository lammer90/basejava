package com.urise.webapp.model.section;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Conteiner implements Serializable{
    private static final long serialVersionUID = 1L;

    private String homePage, name;
    private List<Period> periods;

    public Conteiner(String homePage, String name, Period...periods) {
        this.homePage = homePage;
        this.name = name;
        this.periods = Arrays.asList(periods);
    }

    @Override
    public String toString() {
        return "Conteiner{" +
                "homePage='" + homePage + '\'' +
                ", name='" + name + '\'' +
                ", periods=" + periods +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conteiner)) return false;

        Conteiner conteiner = (Conteiner) o;

        if (homePage != null ? !homePage.equals(conteiner.homePage) : conteiner.homePage != null) return false;
        if (name != null ? !name.equals(conteiner.name) : conteiner.name != null) return false;
        return periods != null ? periods.equals(conteiner.periods) : conteiner.periods == null;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public static class Period implements Serializable{
        private LocalDate startDate, endDate;
        private String title;
        private String text;

        public Period(LocalDate startDate, LocalDate endDate, String title, String text) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.text = text;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }
}
