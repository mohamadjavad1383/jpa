package com.example.spring.service;

import com.example.spring.models.*;
import com.example.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final TeacherCourseRepository teacherCourseRepository;

    @Autowired
    public Service(StudentRepository studentRepository, StudentCourseRepository studentCourseRepository, TeacherRepository teacherRepository, CourseRepository courseRepository, TeacherCourseRepository teacherCourseRepository) {
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

    public void addNewTeacher(Long id, String name) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isPresent())
            throw new IllegalStateException("id " + id + " already exist");
        Teacher teacher = new Teacher(id, name);
        teacherRepository.save(teacher);
    }

    public void addNewCourse(Long id, String name) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent())
            throw new IllegalStateException("id " + id + " already exist");
        Course course = new Course(id, name);
        courseRepository.save(course);
    }

    public void acceptTeacher(Long teacherId, Long courseId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() ->
                new IllegalStateException("teacher id " + teacherId + " does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalStateException("course id " + courseId + " does not exist"));
        Optional<TeacherCourse> optionalTeacherCourse = teacherCourseRepository.
                findTeacherCourseByCourseAndTeacher(course, teacher);
        if (optionalTeacherCourse.isPresent())
            throw new IllegalStateException("course with this teacher already exists");
        TeacherCourse teacherCourse = new TeacherCourse(course, teacher);
        teacherCourseRepository.save(teacherCourse);
    }

    public void registerStudent(Long studentId, Long teacherId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("student id " + studentId + " does not exist"));
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() ->
                new IllegalStateException("teacher id " + teacherId + " does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalStateException("course id " + courseId + " does not exist"));
        TeacherCourse teacherCourse = teacherCourseRepository.findTeacherCourseByCourseAndTeacher(course, teacher)
                .orElseThrow(() -> new IllegalStateException("course with this teacher does not exist"));
        if (studentCourseRepository.findStudentCourseByStudentAndTeacherCourse(student, teacherCourse).isPresent())
            throw new IllegalStateException("student already has this course");
        studentCourseRepository.save(new StudentCourse(student, teacherCourse));
    }

    public void deleteCourse(Long studentId, Long courseId, Long teacherId) {
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
        studentCourseRepository.delete(studentCourse);
    }

    public void changeFavourite(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("student id " + studentId + " does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalStateException("course id " + courseId + " does not exist"));
        student.setFavourite(course);
        studentRepository.save(student);
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

    public double viewAverage(Long courseId, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() ->
                new IllegalStateException("teacher id " + teacherId + " does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalStateException("course id " + courseId + " does not exist"));
        TeacherCourse teacherCourse = teacherCourseRepository.findTeacherCourseByCourseAndTeacher(course, teacher)
                .orElseThrow(() -> new IllegalStateException("course with this teacher does not exist"));
        List<StudentCourse> courses = studentCourseRepository.findStudentCoursesByTeacherCourse(teacherCourse)
                .stream().filter(c -> c.getGrade() != null).toList();
        Float avg = (float) 0;
        for (StudentCourse studentCourse : courses) {
            avg += studentCourse.getGrade();
        }
        avg /= courses.size();
        return courses.stream().mapToDouble(StudentCourse::getGrade).average().getAsDouble();
    }

    public List<String> viewGpa(Float grade) {
        return studentRepository.findAllByGradeGreaterThanEqual(grade).stream().map(Student::getName).toList();
    }

    public Map<String, Integer> viewCountForFavourite() {
        List<Student> students = studentRepository.findAll();
        Set<Course> courses = students.stream().map(Student::getFavourite).collect(Collectors.toSet());
        Map<String, Integer> map = new HashMap<>();
        for (Course course : courses) {
            map.put(course.getName(), (int) students.stream().map(Student::getFavourite).filter(c -> c.equals(course)).count());
        }

        return map;
    }
}
