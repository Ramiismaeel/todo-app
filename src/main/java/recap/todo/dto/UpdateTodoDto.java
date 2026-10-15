package recap.todo.dto;

import recap.todo.TodoStatus;

public record UpdateTodoDto(String description, TodoStatus status) {
}
