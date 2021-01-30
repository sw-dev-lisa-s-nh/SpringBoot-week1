package com.promineotech.socialMediaApi.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.promineotech.socialMediaApi.entity.User;
import com.promineotech.socialMediaApi.service.PostService;
import com.promineotech.socialMediaApi.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private static String UPLOADED_FOLDER = "./pictures/";
	
	@Autowired
	private UserService service;
	
	@Autowired
	private PostService postService;
	
	// Show all users
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Object> getAllUsers() {
		try { 
			return new ResponseEntity<Object>(service.getAllUsers(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}
	
	// Register or Create a user
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody User user) {
		return new ResponseEntity<Object>(service.createUser(user), HttpStatus.CREATED);
	}
	
	// Delete a user
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable Long userId) throws Exception {
		service.deleteUser(userId);
		return new ResponseEntity<Object>("Deleted user with id:" + userId, HttpStatus.OK);

	}	
		
	// Allow a user to login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody User user) {
		try {
			return new ResponseEntity<Object>(service.login(user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}

	// Get all posts for a user
		@RequestMapping(value = "/{id}/posts", method = RequestMethod.GET) 
		public ResponseEntity<Object> getPosts(@PathVariable Long id) {
			try {
				return new ResponseEntity<Object>(postService.getAllUserPosts(id), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}		
		}
	// Get all followed users
	@RequestMapping(value = "/{id}/follows", method = RequestMethod.GET) 
	public ResponseEntity<Object> showFollowedUsers(@PathVariable Long id) {
		try {
			return new ResponseEntity<Object>(service.getFollowedUsers(id), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}
	
	// Allow a userId to follow another user, followId
	@RequestMapping(value = "/{id}/follows/{followId}", method = RequestMethod.POST)
	public ResponseEntity<Object> follow(@PathVariable Long id, @PathVariable Long followId) {
		try {
			return new ResponseEntity<Object>(service.follow(id, followId), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}
	
	// Add a profile picture for a user
	@RequestMapping(value = "/{id}/profilePicture", method = RequestMethod.POST)
	public ResponseEntity<Object> singleFileUpload(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return new ResponseEntity<Object>("Please upload a file.", HttpStatus.BAD_REQUEST);
		}		
		try {
			String url = UPLOADED_FOLDER + file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			Path path = Paths.get(url);
			Files.write(path, bytes);
			return new ResponseEntity<Object>(service.updateProfilePicture(id, url), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
}
