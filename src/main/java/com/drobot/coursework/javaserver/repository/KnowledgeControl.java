package com.drobot.coursework.javaserver.repository;

import com.drobot.coursework.javaserver.repository.common.NameWithEngEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class KnowledgeControl extends NameWithEngEntity {
    private boolean graded;
}
