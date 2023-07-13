package com.example.spring.repository;

import com.example.spring.models.Course;
import com.example.spring.models.Teacher;
import com.example.spring.models.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {
    Optional<TeacherCourse> findTeacherCourseByCourseAndTeacher(Course course, Teacher teacher);
}
