package com.urise.webapp.model;

public enum Contacts {
    PHONE ("Телефон"),
    SKYPE ("Skype"),
    MAIL ("Почта"),
    LINKEDIN ("LinkedIn"),
    STACKOVERFLOW ("Stackoverflow"),
    HOMEPAGE ("Домашняя страница");

    private final String title;

    Contacts(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
