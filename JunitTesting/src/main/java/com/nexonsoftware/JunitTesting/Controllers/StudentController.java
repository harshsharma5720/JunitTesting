package com.nexonsoftware.JunitTesting.Controllers;

import com.nexonsoftware.JunitTesting.Entities.Student;
import com.nexonsoftware.JunitTesting.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> students = studentService.getAllStudent();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return new ResponseEntity<>("Student added successfully", HttpStatus.OK);
    }

    @GetMapping("/id/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable long studentId){
        Student student = studentService.getStudentById(studentId);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{studentId}")
    public ResponseEntity<String> removeStudent(@PathVariable long studentId){
        if (studentService.removeStudent(studentId)) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Student removed successfully");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> removeAllStudents(){
        if (studentService.removeAllStudent()) {
            return new ResponseEntity<>("All students removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The list is already empty",HttpStatus.NOT_FOUND);
        }
    }
}
