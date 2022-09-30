package de.amthor.gendb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);
}
