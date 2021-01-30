package com.promineotech.socialMediaApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.socialMediaApi.entity.User;
import com.promineotech.socialMediaApi.repository.UserRepository;
import com.promineotech.socialMediaApi.view.Following;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	// Register or Create user
	public User createUser(User user) {
		return repo.save(user);
	}
	
	// Delete a user
	public void deleteUser(Long userId) throws Exception {
		User foundUser = repo.findOne(userId); 
		if (foundUser != null) {
			repo.delete(foundUser);
		} else {
			throw new Exception("User not deleted!");
		}
	}
	
	// Allow user to log in
	public User login(User user) throws Exception {
		User foundUser = repo.findByUsername(user.getUsername());
		if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
			return foundUser;
		} else {
			throw new Exception("Invalid username or password.");
		}
	}
	// Find all users
	public Iterable<User> getAllUsers() {
		return repo.findAll();
	}
	
	
	// Allow users to follow other users
	public Following follow(Long userId, Long followId) throws Exception {
		User user = repo.findOne(userId);
		User follow = repo.findOne(followId);
		if (user == null || follow == null) {
			throw new Exception("User does not exist.");
		}
		user.getFollowing().add(follow);
		repo.save(user);
		return new Following(user);
	}
	
	// Get a list of followed users for a given user
	public Following getFollowedUsers(Long userId) throws Exception {
		User user = repo.findOne(userId);
		if (user == null) {
			throw new Exception("User does not exist.");
		}
		return new Following(user);
	}
	
	// Update a user's profile picture
	public User updateProfilePicture(Long userId, String url) throws Exception {
		User user = repo.findOne(userId);
		if (user == null) {
			throw new Exception("User does not exist.");
		}
		user.setProfilePictureUrl(url);
		return repo.save(user);
	}
}

