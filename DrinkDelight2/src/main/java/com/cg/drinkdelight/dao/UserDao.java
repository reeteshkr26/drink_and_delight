package com.cg.drinkdelight.dao;

import java.util.List;

import com.cg.drinkdelight.model.User;

public interface UserDao {
	public List<User> getUserList();

	public void addUserList(User u);

	public void updateUserList(User u);


}
