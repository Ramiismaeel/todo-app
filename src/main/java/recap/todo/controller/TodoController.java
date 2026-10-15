package recap.todo.controller;

import org.springframework.web.bind.annotation.*;
import recap.todo.Todo;
import recap.todo.dto.CreateTodoDto;
import recap.todo.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    @PostMapping
    public Todo create(@RequestBody CreateTodoDto dto) {
        return todoService.createTodo(dto);

    }
}
