package com.drobot.coursework.javaserver.entity.common;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class NameWithEngAndActiveEntity extends NameWithEngEntity {
    private boolean active = true;
}
