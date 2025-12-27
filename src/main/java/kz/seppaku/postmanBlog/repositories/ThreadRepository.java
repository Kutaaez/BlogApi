package kz.seppaku.postmanBlog.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
