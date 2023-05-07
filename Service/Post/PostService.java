package com.social.media.app.service.posts;

import com.social.media.app.entity.dto.posts.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface PostService {

    PostDTO createPost(final PostDTO createPostDTO, final String userProfileId, final MultipartFile file) throws IOException;

    void updatePost(final String profileId, final String postId, final PostDTO updatePostDTO, final MultipartFile file) throws IOException;

    void deletePost(final String profileId, final String postId);

    List<PostDTO> getPostsByUserProfile(final String profileId);

    PostDTO findPostById(final String postId);

    void likePost(final String profileId, final String postId);

    void unlikePost(String profileId, String postId);
}
