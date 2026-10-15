package recap.todo.service;

import org.springframework.stereotype.Service;
import recap.todo.Todo;
import recap.todo.repo.TodoRepo;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepo todoRepo;

    public TodoService(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    public List<Todo> getAll() {
        return todoRepo.findAll();
    }
}
