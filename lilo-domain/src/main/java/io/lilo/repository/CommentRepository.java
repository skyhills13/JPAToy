package io.lilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.lilo.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
