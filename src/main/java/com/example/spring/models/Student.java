package com.example.spring.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "student")
@NoArgsConstructor
public class Student {

    @Id
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "favourite")
    private Course favourite;
    private Float grade;

    @OneToMany(mappedBy = "student")
    Set<StudentCourse> courses = new HashSet<>();

    public Student(Long id, String name, Course favourite, Float grade) {
        this.id = id;
        this.name = name;
        this.favourite = favourite;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
