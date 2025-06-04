package com.drobot.coursework.javaserver.repository.common;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
@MappedSuperclass
@Getter
@Setter
public class Person extends BaseEntity {
    private String surname;
    private String name;
    private String patronimic;
    @Enumerated(value = EnumType.STRING)
    private Sex sex = Sex.MALE;

    public String getFullNameUkr() {
        return getSurname() + " " + getName() + " " + getPatronimic();
    }

    public String getInitialsUkr() {
        String result = getSurname() + " " + getName().substring(0, 1) + ".";
        return result; // + (getPatronimic().length()>0 ? getPatronimic().substring(0, 1) + "." : "");
    }
}
