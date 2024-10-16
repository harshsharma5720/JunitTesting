package com.nexonsoftware.JunitTesting.Services;

import com.nexonsoftware.JunitTesting.Entities.Student;
import com.nexonsoftware.JunitTesting.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getAllStudent() {
        List<Student> entities = studentRepository.findAll();
        return entities;
    }

    public boolean removeStudent(long id) {
        studentRepository.deleteById(id);
        return true;

    }
    public boolean removeAllStudent(){
        studentRepository.deleteAll();
        return true;
    }
}
