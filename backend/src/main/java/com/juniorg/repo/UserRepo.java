package com.juniorg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorg.pojos.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String userName);

	List<User> findByRole(String role);

}
