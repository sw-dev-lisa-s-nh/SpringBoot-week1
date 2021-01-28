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
	
	// Get all posts corresponding to a user
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getAllPosts() {
		return new ResponseEntity<Object>(service.getAllPosts(), HttpStatus.OK);
	}
	
	// Get one post by postId corresponding to a user
	@RequestMapping(value="/{postId}", method=RequestMethod.GET)
	public ResponseEntity<Object> getPosts(@PathVariable Long postId) {
		return new ResponseEntity<Object>(service.getPost(postId), HttpStatus.OK);
	}
	
	// Update a post by postId
	@RequestMapping(value="/{postId}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updatePost(@RequestBody Post post, @PathVariable Long postId) {
		try {
			return new ResponseEntity<Object>(service.updatePost(post,postId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	// Create a comment
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createPost(@RequestBody Post post, @PathVariable Long userId) {
		try {
			return new ResponseEntity<Object>(service.createPost(post,userId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}			
	}

}
