package com.drobot.coursework.javaserver.repository.common;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class NameWithActiveEntity extends NameEntity {
    private boolean active = true;

}
