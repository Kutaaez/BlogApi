package kz.seppaku.postmanBlog.repositories;

import kz.seppaku.postmanBlog.entities.Like;
import kz.seppaku.postmanBlog.entities.Thread;
import kz.seppaku.postmanBlog.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndThread(User user, Thread thread);

}