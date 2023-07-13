package com.example.spring.service;

import com.example.spring.models.*;
import com.example.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final TeacherCourseRepository teacherCourseRepository;

    @Autowired
    public TeacherService(StudentRepository studentRepository, StudentCourseRepository studentCourseRepository, TeacherRepository teacherRepository, CourseRepository courseRepository, TeacherCourseRepository teacherCourseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.teacherCourseRepository = teacherCourseRepository;
    }

    public void addNewTeacher(Long id, String name) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isPresent())
            throw new IllegalStateException("id " + id + " already exist");
        Teacher teacher = new Teacher(id, name);
        teacherRepository.save(teacher);
    }

    public void score(Float score, Long studentId, Long teacherId, Long courseId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() ->
                new IllegalStateException("teacher id " + teacherId + " does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalStateException("course id " + courseId + " does not exist"));
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("student id " + studentId + " does not exist"));
        TeacherCourse teacherCourse = teacherCourseRepository.findTeacherCourseByCourseAndTeacher(course, teacher)
                .orElseThrow(() -> new IllegalStateException("course with this teacher does not exist"));
        StudentCourse studentCourse = studentCourseRepository.findStudentCourseByStudentAndTeacherCourse(student,
                teacherCourse).orElseThrow(() -> new IllegalStateException("student does ont have this course with this teacher"));
        studentCourse.setGrade(score);
        studentCourseRepository.save(studentCourse);
    }
}
