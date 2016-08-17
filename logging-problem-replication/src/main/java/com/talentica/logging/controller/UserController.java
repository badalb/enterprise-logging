package com.talentica.logging.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentica.logging.domain.User;
import com.talentica.logging.exception.UserNotFoundException;
import com.talentica.logging.service.UserService;

@RestController
public class UserController {

	private static final Logger logger = LogManager
			.getLogger(UserController.class.getName());

	@Autowired
	private UserService userService;

	@RequestMapping("/user_id/{userId}")
	public User findUserById(@PathVariable("userId") String userId,
			HttpServletRequest request) {
		logger.info("Finding user details for user id {}", userId);
		logger.info("User agent for incoming request {}",
				request.getHeader("user-agent"));
		try {
			return userService.findUserById(userId);
		} catch (UserNotFoundException ex) {
			logger.debug(
					"Erro finding user details for user id {} with message {}",
					userId, ex.getMessage());
			return null;
		}

	}

	@RequestMapping("/user_name/{userName}")
	public User findByUserName(@PathVariable("userName") String userName,
			HttpServletRequest request) {
		logger.info("Finding user details for user id {}", userName);
		logger.info("User agent for incoming request {}",
				request.getHeader("user-agent"));
		try {
			return userService.findUserById(userName);
		} catch (UserNotFoundException ex) {
			logger.debug(
					"Erro finding user details for user name {} with message {}",
					userName, ex.getMessage());
			return null;
		}

	}

}
