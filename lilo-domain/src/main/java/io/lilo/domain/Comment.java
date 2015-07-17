package io.lilo.domain;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private String content;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    //TODO implement Listener Type
    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;



    @PrePersist
    protected void onCreate() {
        this.createdTime = new Date();
    }

    public Comment() {}

    public Comment(Long articleId, String content) {
        this.articleId = articleId;
        this.content = content;
    }

    public boolean canRegistable() {
        if (StringUtils.isEmpty(content))
            return false;

        if (this.author == null)
            return false;

        return true;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", content='" + content + '\'' +
                ", articleId=" + articleId +
                ", createdTime=" + createdTime +
                '}';
    }
}