package recap.todo.service;

import org.springframework.stereotype.Service;
import recap.todo.Todo;
import recap.todo.TodoStatus;
import recap.todo.dto.CreateTodoDto;
import recap.todo.repo.TodoRepo;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final IdService idService;

    public TodoService(TodoRepo todoRepo, IdService idService) {
        this.todoRepo = todoRepo;
        this.idService = idService;
    }

    public List<Todo> getAll() {
        return todoRepo.findAll();
    }

    public Todo createTodo(CreateTodoDto dto) {
        Todo newTodo = Todo.builder()
                .id(idService.randomId())
                .description(dto.description())
                .status(TodoStatus.OPEN)
                .build();
        return todoRepo.save(newTodo);
    }

    public Optional<Todo> getById(String id) {
        return todoRepo.findById(id);
    }
}
