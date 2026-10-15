package recap.todo.controller;

import org.springframework.web.bind.annotation.*;
import recap.todo.Todo;
import recap.todo.dto.CreateTodoDto;
import recap.todo.dto.UpdateTodoDto;
import recap.todo.service.TodoService;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public Optional<Todo> getById(@PathVariable String id) {
        return todoService.getById(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody UpdateTodoDto dto) {
        return todoService.updateTodo(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable String id) {
        return todoService.deleteTodo(id);
    }
}
