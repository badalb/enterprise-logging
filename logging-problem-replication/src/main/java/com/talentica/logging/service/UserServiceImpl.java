package com.talentica.logging.service;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentica.logging.domain.User;
import com.talentica.logging.exception.UserNotFoundException;
import com.talentica.logging.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager
			.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUserName(String userName) throws UserNotFoundException {
		User user = userRepository.findByUserName(userName);

		if (user == null) {
			throw new UserNotFoundException(
					"No user exits in the system with userName : " + userName);
		}

		return user;
	}

	@Override
	public User findUserById(String userId) throws UserNotFoundException {
		logger.info("Finding user details for user id {}", userId);
		User user = userRepository.findByUserId(userId);

		if (user == null) {
			throw new UserNotFoundException(
					"No user exits in the system with ID : " + userId);
		}

		return user;
	}

}
