package com.social.media.app.service.posts;

import com.social.media.app.entity.dto.posts.PostDTO;
import com.social.media.app.entity.dto.posts.PostTransformer;
import com.social.media.app.entity.model.Post;
import com.social.media.app.entity.model.UserProfile;
import com.social.media.app.repository.PostRepository;
import com.social.media.app.repository.UserProfileRepository;
import com.social.media.app.utils.ImageUtils;
import com.social.media.app.utils.exceptions.ExceptionCodes;
import com.social.media.app.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public PostDTO createPost(final PostDTO createPostDTO, final String userProfileId, final MultipartFile file) throws IOException {

        Optional<UserProfile> profile = getUserProfile(userProfileId);

        createPostDTO.setImageData(ImageUtils.compressImage(file.getBytes()));

        Post post = PostTransformer.toCreatePost(createPostDTO, profile.get());

        return PostTransformer.toPostResponseDTO(postRepository.save(post));
    }
    @Override
    public void updatePost(final String profileId, final String postId, final PostDTO updatePostDTO, final MultipartFile file) throws IOException {

        getPostById(postId);

        if (file != null){

            updatePostDTO.setImageData(ImageUtils.compressImage(file.getBytes()));
        }

        final Post existingPost = getPostByUserProfile(profileId, postId);

        postRepository.save(PostTransformer.toUpdatePost(existingPost, updatePostDTO));
    }
    @Override
    public void deletePost(final String profileId, final String postId) {

        getPostById(postId);

        final Post existingPost = getPostByUserProfile(profileId, postId);

        postRepository.delete(existingPost);
    }

    @Override
    public List<PostDTO> getPostsByUserProfile(final String profileId) {

        List<Post> postList = postRepository.findAllByProfileId(profileId);

        return PostTransformer.toPostResponseDTOList(postList);
    }
    @Override
    public PostDTO findPostById(final String postId) {

        Post post = getPostById(postId);

        return PostTransformer.toPostResponseDTO(post);
    }

    @Override
    public void likePost(final String profileId, final String postId) {

        Optional<UserProfile> profile = getUserProfile(profileId);

        final Post existingPost = getPostById(postId);

        final Set<UserProfile> likedUserProfiles = existingPost.getLikedUserProfiles();
        likedUserProfiles.add(profile.get());
        existingPost.setLikedUserProfiles(likedUserProfiles);

        postRepository.save(existingPost);

    }

    @Override
    public void unlikePost(final String profileId, final String postId) {

        Optional<UserProfile> profile = getUserProfile(profileId);

        final Post existingPost = getPostById(postId);

        final Set<UserProfile> likedUserProfiles = existingPost.getLikedUserProfiles();
        likedUserProfiles.remove(profile.get());
        existingPost.setLikedUserProfiles(likedUserProfiles);

        postRepository.save(existingPost);

    }

    private Post getPostById(final String postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new NotFoundException(ExceptionCodes.POST_NOT_FOUND.name(),
                ExceptionCodes.POST_NOT_FOUND.getErrorDescription());
        }
        return post.get();
    }

    private Post getPostByUserProfile(final String profileId, final String postId) {

        final Post existingPost = postRepository.findByIdAndProfileId(postId, profileId);

        if (existingPost == null) {
            throw new NotFoundException(ExceptionCodes.POST_PERMISSION_DENIED.name(),
                ExceptionCodes.POST_PERMISSION_DENIED.getErrorDescription());
        }
        return existingPost;
    }

    private Optional<UserProfile> getUserProfile(final String userProfileId) {

        Optional<UserProfile> profile = userProfileRepository.findById(userProfileId);

        if (profile.isEmpty()) {
            throw new NotFoundException(ExceptionCodes.USER_PROFILE_NOT_FOUND.name(),
                ExceptionCodes.USER_PROFILE_NOT_FOUND.getErrorDescription());
        }
        return profile;
    }
}
