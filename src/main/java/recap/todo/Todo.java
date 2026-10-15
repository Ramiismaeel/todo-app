package recap.todo;

import lombok.Builder;
import lombok.With;

@Builder
@With
public record Todo(String id, String description, TodoStatus status) {
}
