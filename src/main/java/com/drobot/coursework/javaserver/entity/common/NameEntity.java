package com.drobot.coursework.javaserver.entity.common;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class NameEntity extends BaseEntity {

    private String name;
    public String toString() {
        return this.name;
    }
}
