package com.cg.drinkdelight.testcase;




import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.drinkdelight.model.User;
import com.cg.drinkdelight.service.UserServiceImpl;

public class TestCaseClass {
	
	private static UserServiceImpl userServiceImpl;
	
	@BeforeClass
    public static void initTestCase() {
        userServiceImpl = new UserServiceImpl();
    }
	@Test
	public void testUserRegistration() {
		boolean flag=userServiceImpl.userRegistration(new User("hello@1234","Hello World","hello@gmail.com","hello1234","8765678987","male",LocalDate.now()));
		assertEquals(true,flag);
	}
	@Test
	public void testUserLogin() {
		userServiceImpl.userRegistration(new User("hello@1234","Hello World","hello@gmail.com","hello1234","8765678987","male",LocalDate.now()));
		User u=userServiceImpl.userLogin("hello@1234", "hello1234");
		assertEquals("hello@1234",u.getUserId());
	}
	
}
