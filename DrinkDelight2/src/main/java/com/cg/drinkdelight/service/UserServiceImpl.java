package com.cg.drinkdelight.service;

import java.util.List;

import com.cg.drinkdelight.dao.UserDaoImpl;
import com.cg.drinkdelight.dao.UtilityClass;
import com.cg.drinkdelight.model.User;

public class UserServiceImpl implements UserService {

	UserDaoImpl userDaoImpl;

	public UserServiceImpl() {

		this.userDaoImpl = new UserDaoImpl();
	}

	public boolean userExists(User u) {
		List<User> userList = userDaoImpl.getUserList();
		boolean flag = false;
		if (!(userList.isEmpty())) {
			for (User user : userList) {
				if (user.getUserId().equals(u.getUserId())) {
					flag = true;
					break;
				}
			}
		}
		return flag;

	}

	public boolean userRegistration(User user) {

		userDaoImpl.addUserList(user);
		return true;
	}

	public User userLogin(String id, String pass) {
		List<User> userList = userDaoImpl.getUserList();

		User currentUser = null;
		if(!userList.isEmpty()) {
			for (User u : userList) {
				if (u.getUserId().equals(id) && u.getPassword().equals(pass)) {
					currentUser = u;
					break;
				}
			}
		}
		
		if (currentUser != null) {
			return currentUser;
		} else {
			return null;
		}
	
	}

	public void myAccount(User u) {

		new UtilityClass().showMessage("User Name:"+u.getUserName(), 2);
		
	}

	public boolean logout() {

		return true;
	}

	public User forgotPassword(String id) {

		List<User> userList = userDaoImpl.getUserList();
		User user = null;
		if (!(userList.isEmpty())) {
			for (User u : userList) {
				if (u.getUserId().equals(id)) {
					user = u;
					break;
				}
			}
		}
		return user;
	}

	public boolean setPassword(User u, String pass) {
		u = new User(u.getUserId(), u.getUserName(), u.getEmail(), pass, u.getPhoneNo(), u.getGender(), u.getDob());
		userDaoImpl.updateUserList(u);
		return true;
	}

}
