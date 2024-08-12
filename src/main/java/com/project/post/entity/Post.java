package com.project.post.entity;

import jakarta.persistence.*;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String description;
    private String content;
}