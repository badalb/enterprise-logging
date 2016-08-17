package com.talentica.logging.service;

import com.talentica.logging.domain.User;
import com.talentica.logging.exception.UserNotFoundException;

public interface UserService {

	public User findByUserName(String userName) throws UserNotFoundException;

	public User findUserById(String userId) throws UserNotFoundException;

}
