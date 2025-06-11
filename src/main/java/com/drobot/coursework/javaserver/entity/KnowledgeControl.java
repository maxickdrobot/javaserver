package com.drobot.coursework.javaserver.entity;

import com.drobot.coursework.javaserver.entity.common.NameWithEngEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class KnowledgeControl extends NameWithEngEntity {
    private boolean graded;
}
