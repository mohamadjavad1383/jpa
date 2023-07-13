package com.example.spring.models;

import jakarta.persistence.*;
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
@Table(name = "teacher_course")
@EqualsAndHashCode(of = {"teacher", "course"})
public class TeacherCourse {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @NonNull
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @NonNull
    private Teacher teacher;

    @OneToMany(mappedBy = "teacherCourse")
    Set<StudentCourse> students = new HashSet<>();
}
