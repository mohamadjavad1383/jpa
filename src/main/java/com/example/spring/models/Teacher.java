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
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "teacher")
public class Teacher {
    @Id
    @NonNull
    private Long id;
    @NonNull
    private String name;

    @OneToMany(mappedBy = "teacher")
    Set<TeacherCourse> courses = new HashSet<>();
}
