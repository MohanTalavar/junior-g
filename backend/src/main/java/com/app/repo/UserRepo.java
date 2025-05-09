package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByUserName(String userName);

	List<User> findByRole(String role);

}
