package kz.seppaku.postmanBlog.repositories;
import kz.seppaku.postmanBlog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
}
