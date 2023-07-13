package com.example.spring.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"student", "teacherCourse"})
@Table(name = "student_course")
public class StudentCourse {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_sequence")
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "course_teacher_id")
    private TeacherCourse teacherCourse;

    private Float grade;
}
