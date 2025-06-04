package com.drobot.coursework.javaserver.repository;
import com.drobot.coursework.javaserver.repository.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "course")
@Getter
@Setter
public class Course extends BaseEntity {
    private BigDecimal credits;
    private Integer hours;
    private Integer hoursPerCredit;
    private Integer semester;

    @ManyToOne( cascade = CascadeType.ALL)
    private CourseName courseName;

    @ManyToOne
    @JoinColumn(name = "kc_id", referencedColumnName = "id")
    private KnowledgeControl knowledgeControl;

    @OneToMany(mappedBy = "course")
    private List<Grade> grades;

    private ZonedDateTime createdAt = ZonedDateTime.now();
    private ZonedDateTime updatedAt = ZonedDateTime.now();

}
