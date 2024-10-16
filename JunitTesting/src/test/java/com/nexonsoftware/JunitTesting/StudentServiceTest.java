package com.nexonsoftware.JunitTesting;

import com.nexonsoftware.JunitTesting.Entities.Student;
import com.nexonsoftware.JunitTesting.Repositories.StudentRepository;
import com.nexonsoftware.JunitTesting.Services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    private Student mockStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockStudent = new Student(1L, "John Doe", "1234567890", "example@email.com");
    }

    @Test
    public void addStudent_Success(){
        when(studentRepository.save(mockStudent)).thenReturn(mockStudent);
        Student result = studentService.addStudent(mockStudent);
        assertNotNull(result);
        assertEquals(result.getStudentId(), mockStudent.getStudentId());
        verify(studentRepository, Mockito.times(1)).save(mockStudent);
    }

    @Test
    public void getStudentById_Success(){
        when(studentRepository.findById(mockStudent.getStudentId())).thenReturn(java.util.Optional.of(mockStudent));
        Student result = studentService.getStudentById(mockStudent.getStudentId());
        assertNotNull(result);
        assertEquals(result.getStudentId(), mockStudent.getStudentId());
        verify(studentRepository, Mockito.times(1)).findById(mockStudent.getStudentId());
    }

    @Test
    public void getAllStudent_Success(){
        when(studentRepository.findAll()).thenReturn(java.util.Arrays.asList(mockStudent));
        List<Student> result = studentService.getAllStudent();
        assertNotNull(result);
        assertEquals(result.size(), 1);
        verify(studentRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void removeStudent_Success(){
 //       when(studentRepository.deleteById(mockStudent.getStudentId())).then;
        boolean result = studentService.removeStudent(mockStudent.getStudentId());
        assertTrue(result);
        verify(studentRepository, Mockito.times(1)).deleteById(mockStudent.getStudentId());
    }

    @Test
    public void deleteAll_Success(){
 //       when(studentRepository.deleteAll()).thenReturn(true);
        boolean result = studentService.removeAllStudent();
        assertTrue(result);
        verify(studentRepository, Mockito.times(1)).deleteAll();
    }
}
