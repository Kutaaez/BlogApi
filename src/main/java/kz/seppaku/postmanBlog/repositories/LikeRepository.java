package kz.seppaku.postmanBlog.repositories;
import kz.seppaku.postmanBlog.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
