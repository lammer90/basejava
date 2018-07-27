package com.urise.webapp.model.section;

public enum SectionType {
    OBJECTIVE ("Позиция", StringSection.class),
    PERSONAL ("Личные качества", StringSection.class),
    ACHIEVEMENT ("Достижения", ArraySection.class),
    QUALIFICATION ("Квалификация",ArraySection.class),
    EXPERIENCE ("Опыт работы", ConteinerSection.class),
    EDUCATION ("Образование", ConteinerSection.class);

    private final String title;
    private final Class aClass;

    SectionType(String title, Class aClass) {
        this.aClass = aClass;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Class getaClass() {
        return aClass;
    }
}
