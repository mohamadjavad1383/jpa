package com.example.spring.controller;

import com.example.spring.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class Controller {
    private final Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping("/new~student")
    public void addNewStudent(@RequestParam Long id,
                              @RequestParam String name,
                              @RequestParam Long courseId,
                              @RequestParam Float grade) {
        service.addNewStudent(id, name, courseId, grade);
    }

    @PostMapping("/new~teacher")
    public void addNewTeacher(@RequestParam Long id,
                              @RequestParam String name) {
        service.addNewTeacher(id, name);
    }

    @PostMapping("/new~course")
    public void addNewCourse(@RequestParam Long id,
                             @RequestParam String name) {
        service.addNewCourse(id, name);
    }

    @PostMapping("/accept")
    public void registerStudent(@RequestParam Long teacherId, @RequestParam Long courseId) {
        service.acceptTeacher(teacherId, courseId);
    }

    @PostMapping("/register")
    public void registerStudent(@RequestParam Long studentId, @RequestParam Long teacherId,
                                @RequestParam Long courseId) {
        service.registerStudent(studentId, teacherId, courseId);
    }

    @DeleteMapping("/delete~course")
    public void deleteCourse(@RequestParam Long studentId, @RequestParam Long courseId, @RequestParam Long teacherId) {
        service.deleteCourse(studentId, courseId, teacherId);
    }

    @PutMapping("/change~favourite")
    public void changeFavourite(@RequestParam Long studentId, @RequestParam Long courseId) {
        service.changeFavourite(studentId, courseId);
    }

    @PutMapping("/score")
    public void score(@RequestParam Float score, @RequestParam Long studentId,
                      @RequestParam Long courseId, @RequestParam Long teacherId) {
        service.score(score, studentId, teacherId, courseId);
    }

    @GetMapping("/view~average")
    public Float viewAverage(@RequestParam Long courseId, @RequestParam Long teacherId) {
        return service.viewAverage(courseId, teacherId);
    }

    @GetMapping("/view~gpa")
    public List<String> viewGpa(@RequestParam Float grade) {
        return service.viewGpa(grade);
    }

    @GetMapping("/view~count~for~favourite")
    public Map<String, Integer> viewCountForFavourite() {
        return service.viewCountForFavourite();
    }
}
