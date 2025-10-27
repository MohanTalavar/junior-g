package com.juniorg.service;

import com.juniorg.dto.LoginResponseDto;
import com.juniorg.dto.UserResponseDto;
import com.juniorg.pojos.User;

import java.util.List;

public interface IUserService {

	// add a method to store user details
	String addNewUserRecord(User newUser);

	LoginResponseDto verifyUser(User user);

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
