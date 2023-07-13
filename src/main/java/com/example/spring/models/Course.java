package com.example.spring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "course")
public class Course {
    @Id
    @NonNull
    private Long id;
    @NonNull
    private String name;

    @OneToMany(mappedBy = "course")
    Set<TeacherCourse> teachers = new HashSet<>();

    @OneToMany(mappedBy = "favourite")
    private Set<Student> students = new HashSet<>();
}
