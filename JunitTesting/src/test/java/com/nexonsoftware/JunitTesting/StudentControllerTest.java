package com.nexonsoftware.JunitTesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.TestUtils;
import com.nexonsoftware.JunitTesting.Controllers.StudentController;
import com.nexonsoftware.JunitTesting.Entities.Student;
import com.nexonsoftware.JunitTesting.Repositories.StudentRepository;
import com.nexonsoftware.JunitTesting.Services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

public class StudentControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private StudentController studentController;
    @Mock
    private StudentService studentService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void getAllStudent_Success() throws Exception {
        List<Student> students = Arrays.asList(new Student(1L, "John Doe","AIML", "johndoe@example.com"),
                new Student(2L, "harsh","AIML", "johndoe@example.com"),
                new Student(3L, "Akshay","AIML", "johndoe@example.com")
        );
        Mockito.when(studentService.getAllStudent()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value(1L))
                .andExpect(jsonPath("$[0].studentName").value("John Doe"))
                .andExpect(jsonPath("$[1].studentId").value(2L))
                .andExpect(jsonPath("$[1].studentName").value("harsh"))
                .andExpect(jsonPath("$[2].studentId").value(3L))
                .andExpect(jsonPath("$[2].studentName").value("Akshay"));
        verify(studentService, times(1)).getAllStudent();

    }

    @Test
    public void addStudent_Success() throws Exception {
        Student student = new Student(1L, "John Doe","AIML", "johndoe@example.com");
        when(studentService.addStudent(student)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/student")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(student)));

    }

    @Test
    public void getStudentById_Success() throws Exception {
        Student student = new Student(1L, "John Doe","AIML", "johndoe@example.com");
        when(studentService.getStudentById(student.getStudentId())).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
               .get("/student/id/{studentId}", student.getStudentId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("studentId").value(1L))
               .andExpect(jsonPath("studentName").value("John Doe"))
               .andExpect(jsonPath("branch").value("AIML"))
               .andExpect(jsonPath("studentPassword").value("johndoe@example.com"));
        verify(studentService, times(1)).getStudentById(student.getStudentId());

    }

    @Test
    public void removeStudent_Success() throws Exception {
        Student student = new Student(1L, "John Doe","AIML", "johndoe@example.com");
        when(studentService.removeStudent(student.getStudentId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/student/id/{studentId}", student.getStudentId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(studentService, times(1)).removeStudent(student.getStudentId());
    }

    @Test
    public void removeAllStudents_Success() throws Exception {
        when(studentService.removeAllStudent()).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
               .delete("/student/deleteAll")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        verify(studentService, times(1)).removeAllStudent();

    }
}
