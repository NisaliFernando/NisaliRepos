package com.social.media.app.repository;
import com.social.media.app.entity.model.Post;
import com.social.media.app.entity.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query("FROM Post p WHERE p.postedBy.profileId = ?1")
    List<Post> findAllByProfileId(String profileId);

    @Query("FROM Post p WHERE p.id = ?1 AND p.postedBy.profileId = ?2")
    Post findByIdAndProfileId(String postId, String profileId);
}
