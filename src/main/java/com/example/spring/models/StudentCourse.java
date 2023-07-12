package com.example.spring.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_teacher_id")
    private TeacherCourse teacherCourse;

    private Float grade;

    public StudentCourse(Student student, TeacherCourse teacherCourse) {
        this.student = student;
        this.teacherCourse = teacherCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourse that = (StudentCourse) o;
        return student.equals(that.student) && teacherCourse.equals(that.teacherCourse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, teacherCourse);
    }
}
