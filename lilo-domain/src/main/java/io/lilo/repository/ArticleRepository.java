package io.lilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import io.lilo.domain.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}