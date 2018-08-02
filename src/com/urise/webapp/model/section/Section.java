package com.urise.webapp.model.section;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ArraySection.class, name="arraysection"),
        @JsonSubTypes.Type(value=StringSection.class, name="stringsection"),
        @JsonSubTypes.Type(value=ConteinerSection.class, name="conteinersection")})
public abstract class Section<T> implements Serializable{
    public Section() {
    }
    public abstract String toString();

    public abstract T getInformation();
    public abstract void setNewInformation(T inf);
}
