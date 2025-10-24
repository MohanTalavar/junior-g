package com.juniorg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorg.pojos.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByUserName(String userName);

	List<User> findByRole(String role);

}
