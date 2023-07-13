package com.example.spring.service;

import com.example.spring.models.Course;
import com.example.spring.models.Student;
import com.example.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final TeacherCourseRepository teacherCourseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentCourseRepository studentCourseRepository, TeacherRepository teacherRepository, CourseRepository courseRepository, TeacherCourseRepository teacherCourseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.teacherCourseRepository = teacherCourseRepository;
    }


    public void addNewStudent(Long id, String name, Long courseId, Float grade) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent())
            throw new IllegalStateException("id " + id + " already exist");
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalStateException("course id " + courseId + " does not exist"));
        Student student = new Student(id, name, course, grade);
        studentRepository.save(student);
    }

    public void changeFavourite(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("student id " + studentId + " does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalStateException("course id " + courseId + " does not exist"));
        student.setFavourite(course);
        studentRepository.save(student);
    }


    public List<String> viewGpa(Float grade) {
        return studentRepository.findAllByGradeGreaterThanEqual(grade).stream().map(Student::getName).toList();
    }
}
