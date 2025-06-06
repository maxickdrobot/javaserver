package com.drobot.coursework.javaserver.entity.common;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class NameWithEngEntity extends NameEntity {
    private String nameEng;

    public NameWithEngEntity() {
    }

    public NameWithEngEntity(String name, String nameEng) {
        this.setName(name);
        this.nameEng = nameEng;
    }
}
