package com.app.service;

import com.app.dto.LoginResponseDto;
import com.app.pojos.User;

public interface IUserService {

	// add a method to store user details
	String addNewUserRecord(User newUser);

	LoginResponseDto verifyUser(User user);

	// add a method to get the user details
	User retrieveUserDetails(String userName);

}
