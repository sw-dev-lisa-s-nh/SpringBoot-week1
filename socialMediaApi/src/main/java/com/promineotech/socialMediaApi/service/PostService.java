package com.promineotech.socialMediaApi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.socialMediaApi.entity.Post;
import com.promineotech.socialMediaApi.entity.User;
import com.promineotech.socialMediaApi.repository.PostRepository;
import com.promineotech.socialMediaApi.repository.UserRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	// Find ALL posts
	public Iterable<Post> getAllPosts() {
		return repo.findAll();
	}
	
	//Find ONE Post
	public Post getPost(Long id) {
		return repo.findOne(id);
	}
	
	//Find ALL of an Individual's posts
	public Iterable<Post> getAllUserPosts(Long id) {
		return userRepo.findOne(id).getPosts();
	}

	//Find ONE of an Individual's posts
	public Post getUserPost(Long userId, Long postId) {
		Post newPost = repo.findOne(postId);
		if (newPost.getUser().getId().equals(userId)) {
			return newPost;
		} else {
			return null;
		}
	}
	
	// Update a Post
	public Post updatePost(Post post, Long id) throws Exception {
		Post foundPost = repo.findOne(id);
		if (foundPost == null) {
			throw new Exception("Post not found.");
		}
		foundPost.setContent(post.getContent());
		return repo.save(foundPost);
	}
	
	// Create a Post
	public Post createPost(Post post, Long userId) throws Exception {
		User user = userRepo.findOne(userId);
		if (user == null) {
			throw new Exception("User not found.");
		}
		post.setDate(new Date());
		post.setUser(user);
		return repo.save(post);
	}
	
	// Delete a post
	public void deletePost(Long postId) throws Exception {
		Post foundPost = repo.findOne(postId); 
		if (foundPost != null) {
			repo.delete(foundPost);
		} else {
			throw new Exception("Post not deleted!");
		}
	}
		
}
