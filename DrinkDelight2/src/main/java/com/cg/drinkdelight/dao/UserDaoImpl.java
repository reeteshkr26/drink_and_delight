package com.cg.drinkdelight.dao;

import java.util.ArrayList;
import java.util.List;
import com.cg.drinkdelight.model.User;

public class UserDaoImpl implements UserDao {
	static List<User> userList = new ArrayList<User>();
	
	public List<User> getUserList() {

		return userList;
	}

	public void addUserList(User u) {
		userList.add(u);
	}

	public void updateUserList(User u) {
		int key = -1;
		for (User user : userList) {
			if (user.getUserId().equals(u.getUserId())) {
				key++;
			}
		}
		if (key >= 0) {
			userList.set(key, u);

		}

	}

}
