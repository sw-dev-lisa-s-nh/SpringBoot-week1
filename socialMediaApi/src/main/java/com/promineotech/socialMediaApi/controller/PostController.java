package com.promineotech.socialMediaApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.socialMediaApi.entity.Post;
import com.promineotech.socialMediaApi.service.PostService;

@RestController
@RequestMapping("/users/{userId}/posts")
public class PostController {

	@Autowired
	private PostService service;

	// Get all posts by userId 
		@RequestMapping(value="/{userId}/posts", method=RequestMethod.GET)
		public ResponseEntity<Object> getPosts(@PathVariable Long userId) {
			return new ResponseEntity<Object>(service.getAllUserPosts(userId), HttpStatus.OK);
		}
		
	// Get one post by postId 
	@RequestMapping(value="/{postId}", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserPost(@PathVariable Long userId, @PathVariable Long postId) {
		return new ResponseEntity<Object>(service.getUserPost(userId, postId), HttpStatus.OK);
	}
	
//	// Get one post by postId 
//	@RequestMapping(value="/{postId}", method=RequestMethod.GET)
//	public ResponseEntity<Object> getPost(@PathVariable Long postId) {
//		return new ResponseEntity<Object>(service.getPost(postId), HttpStatus.OK);
//	}
//	
	
	// Update a post by postId
	@RequestMapping(value="/{postId}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updatePost(@RequestBody Post post, @PathVariable Long postId) {
		try {
			return new ResponseEntity<Object>(service.updatePost(post,postId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	// Create a post
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createPost(@RequestBody Post post, @PathVariable Long userId) {
		try {
			return new ResponseEntity<Object>(service.createPost(post,userId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}			
	}

	
	// Delete a post
	@RequestMapping(value="/{postId}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deletePost(@PathVariable Long postId) throws Exception {
		service.deletePost(postId);
		return new ResponseEntity<Object>("Deleted post with id:" + postId, HttpStatus.OK);
	}

}
