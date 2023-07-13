package com.example.spring.repository;

import com.example.spring.models.Student;
import com.example.spring.models.StudentCourse;
import com.example.spring.models.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse,Long> {
    Optional<StudentCourse> findStudentCourseByStudentAndTeacherCourse(Student student, TeacherCourse course);
    List<StudentCourse> findStudentCoursesByTeacherCourse(TeacherCourse course);
}
