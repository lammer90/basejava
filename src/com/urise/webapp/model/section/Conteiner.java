package com.urise.webapp.model.section;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.urise.webapp.util.LocalDateMarshaller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Conteiner implements Serializable{
    private static final long serialVersionUID = 1L;

    private String homePage, name;
    @JsonDeserialize(as=ArrayList.class, contentAs = Period.class)
    @JsonProperty("periods")
    private List<Period> periods;

    public Conteiner() {
    }

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

    @XmlAccessorType(XmlAccessType.FIELD)
    @JsonAutoDetect
    public static class Period implements Serializable{
        @XmlJavaTypeAdapter(LocalDateMarshaller.class)
        @JsonProperty("startDate")
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateMarshaller.class)
        @JsonProperty("endDate")
        private LocalDate endDate;
        @JsonProperty("title")
        private String title;
        @JsonProperty("text")
        private String text;

        public Period() {
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Period)) return false;

            Period period = (Period) o;

            if (startDate != null ? !startDate.equals(period.startDate) : period.startDate != null) return false;
            if (endDate != null ? !endDate.equals(period.endDate) : period.endDate != null) return false;
            if (title != null ? !title.equals(period.title) : period.title != null) return false;
            return text != null ? text.equals(period.text) : period.text == null;
        }

        @Override
        public int hashCode() {
            int result = startDate != null ? startDate.hashCode() : 0;
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            result = 31 * result + (title != null ? title.hashCode() : 0);
            result = 31 * result + (text != null ? text.hashCode() : 0);
            return result;
        }
    }
}
