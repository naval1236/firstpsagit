package com.project.post.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String CommentId;
    private String name;
    private String email;
    private String body;
    private String postId;
}
