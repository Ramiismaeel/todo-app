package recap.todo;

import lombok.With;

@With
public record Todo(String id, String title, String description, TodoStatus status) {
}
