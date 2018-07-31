package com.urise.webapp.model.section;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conteiner {
    private String homePage;
    private List<Period> periods;
    private String title;
    private String text;

    public Conteiner(String homePage, List<Period> periods, String title, String text) {
        this.homePage = homePage;
        this.periods = periods;
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Conteiner{" +
                "homePage='" + homePage + '\'' +
                ", periods=" + periods +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conteiner)) return false;

        Conteiner conteiner = (Conteiner) o;

        if (homePage != null ? !homePage.equals(conteiner.homePage) : conteiner.homePage != null) return false;
        if (periods != null ? !periods.equals(conteiner.periods) : conteiner.periods != null) return false;
        if (title != null ? !title.equals(conteiner.title) : conteiner.title != null) return false;
        return text != null ? text.equals(conteiner.text) : conteiner.text == null;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public static class Period{
        private LocalDate startDate, endDate;

        public Period(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}
