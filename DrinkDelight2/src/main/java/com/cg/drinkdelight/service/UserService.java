package com.cg.drinkdelight.service;

import com.cg.drinkdelight.model.User;

public interface UserService {

	public boolean userExists(User u);

	public boolean userRegistration(User user);

	public User userLogin(String id, String pass);

	public void myAccount(User u);

	public boolean logout();

	public User forgotPassword(String id);

	public boolean setPassword(User u, String pass);
}
