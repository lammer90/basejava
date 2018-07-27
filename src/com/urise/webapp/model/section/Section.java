package com.urise.webapp.model.section;

public abstract class Section<T> {
    public abstract String toString();

    public abstract T getInformation();
    public abstract void setNewInformation(T inf);
}
