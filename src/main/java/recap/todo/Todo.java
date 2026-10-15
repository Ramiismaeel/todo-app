package recap.todo;

import lombok.Builder;

@Builder
public record Todo(String id, String description, TodoStatus status) {
}
