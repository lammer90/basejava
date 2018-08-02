package com.urise.webapp.model.section;

import java.io.Serializable;

public abstract class Section<T> implements Serializable{
    public Section() {
    }
    public abstract String toString();

    public abstract T getInformation();
    public abstract void setNewInformation(T inf);
}
