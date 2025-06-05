package com.app.service;

import com.app.dto.LoginResponseDto;
import com.app.dto.UserResponseDto;
import com.app.pojos.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

	// add a method to store user details
	String addNewUserRecord(User newUser);

	LoginResponseDto verifyUser(User user);

	// add a method to get the user details
	User retrieveUserDetails(String userName);

	// add a method to initiate password reset
	String initiatePasswordReset(String userName, String email);

	// add a method to reset the passed
	String resetPassword(String token, String newPassword);

	// add a method to get the list of users
	List<UserResponseDto> fetchUserList();

	String deleteUser(String userName);

	// add a method to update the user details
	UserResponseDto updateUserRecord(String userName, UserResponseDto updatedUser);

	// add a method to get user details by userName
	UserResponseDto fetchUserDetailsByUserName(String userName);
}
