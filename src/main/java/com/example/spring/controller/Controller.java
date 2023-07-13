package com.example.spring.controller;

import com.example.spring.service.CourseService;
import com.example.spring.service.StudentService;
import com.example.spring.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class Controller {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public Controller(StudentService studentService, TeacherService teacherService, CourseService courseService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @PostMapping("new-student")
    public void addNewStudent(@RequestParam Long id,
                              @RequestParam String name,
                              @RequestParam Long courseId,
                              @RequestParam Float grade) {
        studentService.addNewStudent(id, name, courseId, grade);
    }

    @PostMapping("new-teacher")
    public void addNewTeacher(@RequestParam Long id,
                              @RequestParam String name) {
        teacherService.addNewTeacher(id, name);
    }

    @PostMapping("new-course")
    public void addNewCourse(@RequestParam Long id,
                             @RequestParam String name) {
        courseService.addNewCourse(id, name);
    }

    @PostMapping("accept")
    public void acceptTeacher(@RequestParam Long teacherId, @RequestParam Long courseId) {
        courseService.acceptTeacher(teacherId, courseId);
    }

    @PostMapping("register")
    public void registerStudent(@RequestParam Long studentId, @RequestParam Long teacherId,
                                @RequestParam Long courseId) {
        courseService.registerStudent(studentId, teacherId, courseId);
    }

    @DeleteMapping("delete-course")
    public void deleteCourse(@RequestParam Long studentId, @RequestParam Long courseId, @RequestParam Long teacherId) {
        courseService.deleteCourse(studentId, courseId, teacherId);
    }

    @PutMapping("change-favourite")
    public void changeFavourite(@RequestParam Long studentId, @RequestParam Long courseId) {
        studentService.changeFavourite(studentId, courseId);
    }

    @PutMapping("score")
    public void score(@RequestParam Float score, @RequestParam Long studentId,
                      @RequestParam Long courseId, @RequestParam Long teacherId) {
        teacherService.score(score, studentId, teacherId, courseId);
    }

    @GetMapping("view-average")
    public double viewAverage(@RequestParam Long courseId, @RequestParam Long teacherId) {
        return courseService.viewAverage(courseId, teacherId);
    }

    @GetMapping("view-gpa")
    public List<String> viewGpa(@RequestParam Float grade) {
        return studentService.viewGpa(grade);
    }

    @GetMapping("view-count-for-favourite")
    public Map<String, Integer> viewCountForFavourite() {
        return courseService.viewCountForFavourite();
    }
}
