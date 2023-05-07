package com.social.media.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.media.app.entity.dto.posts.PostDTO;
import com.social.media.app.service.posts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@RestController
public class PostsController extends BaseController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/users/{profileId}/posts", method = RequestMethod.POST,
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public PostDTO createPost(HttpServletResponse response,
        @PathVariable("profileId") String profileId, @RequestPart String postDTOString,
        @RequestPart("image") MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        PostDTO postDTO = objectMapper.readValue(postDTOString, PostDTO.class);

        final PostDTO responseDTO = postService.createPost(postDTO, profileId, file);

        setStatusHeadersToSuccess(response);

        return responseDTO;
    }

    @RequestMapping(value = "/users/{profileId}/posts/{postId}", method = RequestMethod.PUT)
    @ResponseBody
    public void updatePost(HttpServletResponse response, @PathVariable("profileId") String profileId,
        @PathVariable("postId") String postId, @RequestPart String postDTOString,
        @RequestPart(value = "image", required = false) MultipartFile file) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        PostDTO postDTO = objectMapper.readValue(postDTOString, PostDTO.class);

        postService.updatePost(profileId, postId, postDTO, file);

        setStatusHeadersToSuccess(response);
    }

    @RequestMapping(value = "/users/{profileId}/posts", method = RequestMethod.GET)
    @ResponseBody
    public List<PostDTO> findAllPosts(HttpServletResponse response,
        @PathVariable("profileId") String profileId) {

        final List<PostDTO> responseDTO = postService.getPostsByUserProfile(profileId);

        setStatusHeadersToSuccess(response);

        return responseDTO;
    }

    @RequestMapping(value = "/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public PostDTO findPost(HttpServletResponse response, @PathVariable("postId") String postId) {

        final PostDTO postResponseDTO = postService.findPostById(postId);

        setStatusHeadersToSuccess(response);

        return postResponseDTO;
    }

    @RequestMapping(value = "/users/{profileId}/posts/{postId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePost(HttpServletResponse response, @PathVariable("profileId") String profileId,
        @PathVariable("postId") String postId) {

        postService.deletePost(profileId, postId);

        setStatusHeadersToSuccess(response);
    }

    @RequestMapping(value = "/users/{profileId}/posts/{postId}/like", method = RequestMethod.GET)
    @ResponseBody
    public void likePost(HttpServletResponse response, @PathVariable("profileId") String profileId,
        @PathVariable("postId") String postId) {

        postService.likePost(profileId, postId);

        setStatusHeadersToSuccess(response);
    }

    @RequestMapping(value = "/users/{profileId}/posts/{postId}/unlike", method = RequestMethod.GET)
    @ResponseBody
    public void unLikePost(HttpServletResponse response, @PathVariable("profileId") String profileId,
        @PathVariable("postId") String postId) {

        postService.unlikePost(profileId, postId);

        setStatusHeadersToSuccess(response);
    }
}
