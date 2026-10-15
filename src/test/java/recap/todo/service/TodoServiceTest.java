package recap.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import recap.todo.Todo;
import recap.todo.TodoStatus;
import recap.todo.dto.CreateTodoDto;
import recap.todo.dto.UpdateTodoDto;
import recap.todo.repo.TodoRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepo mockRepo;

    @Mock
    IdService mockId;

    @InjectMocks
    TodoService todoService;

    @BeforeEach
    void setup() {
        todoService = new TodoService(mockRepo, mockId);
    }


    @Test
    void getAllTodos_shouldReturnEmptyListOfTodos() {

        Mockito.when(todoService.getAll()).thenReturn(Collections.emptyList());
        List<Todo>  todos = todoService.getAll();
        assertEquals(Collections.emptyList(), todos);
        Mockito.verify(mockRepo).findAll();
        Mockito.verifyNoMoreInteractions(mockRepo, mockId);

    }

    @Test
    void getAllTodos_shouldReturnListOfTodos() {
        Todo todo = new Todo("1", "test", TodoStatus.IN_PROGRESS);
        Mockito.when(todoService.getAll()).thenReturn(List.of(todo));
        List<Todo>  todos = todoService.getAll();
        assertEquals(List.of(todo), todos);
        Mockito.verify(mockRepo).findAll();
        Mockito.verifyNoMoreInteractions(mockRepo, mockId);

    }

    @Test
    void getById_shouldReturnOneTodoByGivenId1() {
        Todo todo = new Todo("1", "test", TodoStatus.DONE);

        Mockito.when(mockRepo.findById("1")).thenReturn(Optional.of(todo));

        assertEquals(Optional.of(todo), todoService.getById(todo.id()));

        Mockito.verify(mockRepo).findById(todo.id());
        Mockito.verifyNoMoreInteractions(mockRepo, mockId);
    }

    @Test
    void getById_shouldReturnNullTodoByGivenIdUnknown() {

        Mockito.when(mockRepo.findById("unknown")).thenReturn(null);
        assertNull(todoService.getById("unknown"));
        Mockito.verify(mockRepo).findById("unknown");
        Mockito.verifyNoMoreInteractions(mockRepo, mockId);
    }

    @Test
    void createTodo_shouldReturnThisTodoAndAddItToList_byGivenTodoDto() {

        Mockito.when(mockId.randomId()).thenReturn("1");

        CreateTodoDto dto = new CreateTodoDto("Test");
        Todo newTodo = new Todo("1", dto.description(), TodoStatus.OPEN);

        Mockito.when(mockRepo.save(newTodo)).thenReturn(newTodo);

        assertEquals(newTodo, todoService.createTodo(dto));

        Mockito.verify(mockRepo).save(newTodo);
        Mockito.verify(mockId).randomId();
        Mockito.verifyNoMoreInteractions(mockId, mockRepo);
    }

    @Test
    void updateTodo_shouldThrowException_byNotExistTodo() {

        UpdateTodoDto todo = new UpdateTodoDto( "test", TodoStatus.OPEN);

        Mockito.when(mockRepo.existsById("1")).thenReturn(false);

        assertThrows(RuntimeException.class, ()-> todoService.updateTodo("1", todo));
        Mockito.verify(mockRepo).existsById("1");
        Mockito.verifyNoMoreInteractions(mockId, mockRepo);

    }

    @Test
    void updateTodo_shouldReturnTheNewUpdatedTodo_byGivenDto() throws RuntimeException {
        UpdateTodoDto dto = new UpdateTodoDto( "test", TodoStatus.OPEN);
        Mockito.when(mockRepo.existsById("1")).thenReturn(true);
        Todo todo = Todo.builder()
                        .id("1")
                                .description(dto.description())
                                        .status(dto.status()).build();
        Mockito.when(mockRepo.save(todo)).thenReturn(todo);

        assertEquals(todo, todoService.updateTodo("1", dto) );
        Mockito.verify(mockRepo).existsById("1");
        Mockito.verify(mockRepo).save(todo);
        Mockito.verifyNoMoreInteractions(mockId, mockRepo);

    }

    @Test
    void delete_shouldRemoveTodoFromList_byGivenId1() {

        Mockito.when(mockRepo.existsById("1")).thenReturn(true);
        todoService.deleteTodo("1");
        Mockito.verify(mockRepo).deleteById("1");
    }

    @Test
    void delete_shouldReturnConfirmText_byGivenId1() {

        Mockito.when(mockRepo.existsById("2")).thenReturn(true);
        assertEquals("Deleted successfully", todoService.deleteTodo("2"));
        Mockito.verify(mockRepo).deleteById("2");
    }

    @Test
    void delete_shouldReturnNotfoundText_byGivenId1() {

        Mockito.when(mockRepo.existsById("3")).thenReturn(false);
        assertEquals("This Todo not exist", todoService.deleteTodo("3"));
        Mockito.verify(mockRepo).existsById("3");
        Mockito.verifyNoMoreInteractions(mockId, mockRepo);
    }


}