package kz.seppaku.postmanBlog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "threads")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    private List<Review> commentaries;
    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    private List<Like> likes;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "thread_categories",
            joinColumns = @JoinColumn(name = "thread_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
