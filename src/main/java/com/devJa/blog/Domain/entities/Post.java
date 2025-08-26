package com.devJa.blog.Domain.entities;

import com.devJa.blog.Domain.PostStatus;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;// Primary Key

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)//enum - naka save as string
    private PostStatus status;

    @Column(nullable = false)
    private Integer readingTime; // estimated reading time in minutes

    @ManyToOne(fetch = FetchType.LAZY)// Many Posts -> One User
    @JoinColumn(name = "author_id", nullable = false)// foreign Key
    private User author;// relationship with user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    // for consistent comparison ng objects (this is important for collections at Hibernate caching)
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(content, post.content) && status == post.status && Objects.equals(readingTime, post.readingTime) && Objects.equals(createdAt, post.createdAt) && Objects.equals(updatedAt, post.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, status, readingTime, createdAt, updatedAt);
    }

    //bago isave sa DB, auto set
    @PrePersist
    protected void onCreate(){
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}

