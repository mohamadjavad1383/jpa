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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teacher_course")
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
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public TeacherCourse(Course course, Teacher teacher) {
        this.course = course;
        this.teacher = teacher;
    }

    @OneToMany(mappedBy = "teacherCourse")
    Set<StudentCourse> students = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherCourse that = (TeacherCourse) o;
        return course.equals(that.course) && teacher.equals(that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, teacher);
    }
}
