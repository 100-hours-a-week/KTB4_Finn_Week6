package kr.ktb.finn_week6.domain.comment.repository;

import jakarta.persistence.EntityManager;
import kr.ktb.finn_week6.domain.comment.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {
    private final EntityManager em;
    public CommentRepository(EntityManager em) {
        this.em = em;
    }

    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    public Optional<Comment> findById(Long id){
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    public List<Comment> findByPostIdWithUser(Long postId) {
        return em.createQuery("SELECT c FROM Comment c join fetch c.user WHERE c.post.id = :postId AND c.isDeleted = false", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }
    public List<Comment> findByPostIdWithPost(Long postId){
        return em.createQuery("SELECT c FROM Comment c join fetch c.post WHERE c.post.id = :postId AND c.isDeleted = false", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }
}
