package com.example.spring.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "student")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Student {

    @Id
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "favourite")
    @NonNull
    private Course favourite;
    @NonNull
    private Float grade;

    @OneToMany(mappedBy = "student")
    Set<StudentCourse> courses = new HashSet<>();
}
