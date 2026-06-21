package kr.ktb.finn_week6.domain.post.repository;

import jakarta.persistence.EntityManager;
import kr.ktb.finn_week6.domain.post.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    private final EntityManager em;
    public PostRepository(EntityManager em) {
        this.em = em;
    }

    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    public Optional<Post> findById(Long id){
        return Optional.ofNullable(em.find(Post.class, id));
    }
    public List<Post> findByUserId(Long userId){
        return em.createQuery("select p from Post p where p.user.id = :userId order by p.createdAt desc", Post.class)
                .setParameter("userId", userId)
                .setMaxResults(10)
                .getResultList();
    }

    public List<Post> findPostsOrderByCreatedAtDesc(){
        return em.createQuery("select p from Post p join fetch p.user where p.isDeleted = false order by p.createdAt desc", Post.class)
                .setMaxResults(10)
                .getResultList();
    }
}
