package com.talentica.logging.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.talentica.logging.domain.User;

public interface UserRepository extends CrudRepository<User, String> {

	public User findByUserName(String userName);

	public List<User> findByIsDeleted(String isDeleted);

	public User findByUserId(String userId);

}