package recap.todo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import recap.todo.Todo;

@Repository
public interface TodoRepo extends MongoRepository<Todo, String> {
}
