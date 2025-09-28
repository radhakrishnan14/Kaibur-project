package com.example.kaiburr;

import com.example.kaiburr.repo.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired 
    MockMvc mvc;

    @Autowired 
    TaskRepository repo;

    @Test
    void createAndGetTask() throws Exception {
        repo.deleteAll();
        String json = "{\"id\":\"t1\",\"name\":\"Test\",\"owner\":\"me\",\"command\":\"echo hi\"}";

        mvc.perform(put("/tasks")
                .contentType("application/json")
                .content(json))
           .andExpect(status().isOk());

        mvc.perform(get("/tasks/t1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("Test"));
    }
}
