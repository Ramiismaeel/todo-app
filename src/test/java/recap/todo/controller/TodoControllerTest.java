package recap.todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import recap.todo.Todo;
import recap.todo.TodoStatus;
import recap.todo.repo.TodoRepo;
import recap.todo.service.IdService;



@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepo todoRepo;

    @Autowired
    IdService idService;
    Todo todo = new Todo("1", "test", TodoStatus.OPEN);

    @BeforeEach
    void setUp() {
        todoRepo.deleteAll();
    }

    @Test
    void getAll_shouldReturnAllTodos() throws Exception{


        todoRepo.save(todo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
        """
                       [
                           {
                               "id": "1",
                               "description": "test",
                               "status": "OPEN"
                           }
                       ]
                 """
                )
        );
    }

    @Test
    void getById_shouldReturnTodo_thatHaveSameIdInPath_byGivenPathWithId1() throws Exception {
        todoRepo.save(todo);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
        """
                       {
                           "id": "1",
                           "description": "test",
                           "status": "OPEN"
                       }
                 """
                )
        );
    }

    @Test
    void createTodo_shouldAddTodoToListAndReturnTodo_byGivenTodoDto() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo").contentType(MediaType.APPLICATION_JSON).content(
                """
                    {"description" : "test"}
                """
        ))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
    """
                    {
                    "description" : "test",
                    "status": "OPEN"
                    }
                """
            ))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());

    }

    @Test
    void updateTodo_shouldUpdateValuesAndReturnUpdatedTodo_byGivenIdAndDtoValues() throws Exception {
        todoRepo.save(todo);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/1").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                "description" : "new text",
                "status": "DONE"
                }
            """
        ))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
    """
                  {
                       "id": "1",
                       "description": "new text",
                       "status": "DONE"
                   }
                """
        ));
    }

    @Test
    void updateTodo_shouldNotUpdateTheTodoAndReturnNotFoundException() throws Exception {
        todoRepo.save(todo);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/2").contentType(MediaType.APPLICATION_JSON).content(
                        """
                            {
                            "description" : "new text",
                            "status": "DONE"
                            }
                        """
                ))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteTodo_shouldDeleteTodoAndReturnConfirmText_byGivenValidPath() throws Exception {
        todoRepo.save(todo);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deleted successfully"));
    }

    @Test
    void deleteTodo_shouldReturnFailerText_byGivenUnknownId() throws Exception {
        todoRepo.save(todo);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("This Todo not exist"));
    }

}