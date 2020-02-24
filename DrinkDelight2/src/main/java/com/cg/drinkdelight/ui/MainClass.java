package com.cg.drinkdelight.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.drinkdelight.exception.MyException;
import com.cg.drinkdelight.model.User;
import com.cg.drinkdelight.service.UserServiceImpl;

public class MainClass {

	InputStreamReader isr;
	BufferedReader buff;
	UserServiceImpl userServiceImpl;

	public static void main(String[] args) {

		MainClass mObject = new MainClass();
		mObject.isr = new InputStreamReader(System.in);
		mObject.buff = new BufferedReader(mObject.isr);
		mObject.userServiceImpl = new UserServiceImpl();
		mObject.performOperation();

	}

	public void performOperation() {

		try {

			System.out.println(
					"*************Welcome to Drink and Delight*********** \n 1.Login \n 2.Register \n 3.Forgot Password ");
			System.out.print("Enter your choice:");
			int choice = Integer.parseInt(buff.readLine());
			System.out.println();
			switch (choice) {
			case 1:

				userLoginProcess();
				break;
			case 2:
				userRegistrationProcess();
				break;
			case 3:
				performForgotPassword();
				break;
			default:
				throw new MyException("Invalid Choice");

			}

		} catch (Exception e) {
			System.out.println(e.getMessage() + "\n");

		}

	}

	public void userRegistrationProcess() {
		System.out.println("****************Registration Page***************");

		try {
			System.out.print("Enter your userId : ");
			String userId = buff.readLine();
			System.out.print("Enter your userName : ");
			String userName = buff.readLine();
			System.out.print("Enter your email : ");
			String email = buff.readLine();
			System.out.print("Enter your password : ");
			String password = buff.readLine();
			System.out.print("Enter your phoneNumber : ");
			String phoneNumber = buff.readLine();
			System.out.print("Enter your gender : ");
			String gen = buff.readLine();
			System.out.println();

			if (isValidation(userId, userName, email, password, phoneNumber, gen)) {
				User user = new User(userId, userName, email, password, phoneNumber, gen, LocalDate.now());
				if (!(userServiceImpl.userExists(user))) {
					if (userServiceImpl.userRegistration(user)) {
						System.out.println("Registreation Success...\n");
						performOperation();
					} else {
						System.out.println("Registration Failed...\n");
						performOperation();
					}

				} else {
					System.out.println("User Already exits...\n");
					performOperation();
				}
			} else {
				performOperation();
			}

		} catch (Exception e) {
			System.out.println("Exception Message: " + e.getMessage());
		}

	}

	public void userLoginProcess() {

		System.out.println("****************Login Page***************");

		try {
			System.out.print("Enter your userId : ");
			String id = buff.readLine();
			System.out.print("Enter your password : ");
			String password = buff.readLine();

			System.out.println();

			if (id.isEmpty() || password.isEmpty()) {
				throw new MyException("Error Message: Empty fields");
			}

			User currentUser = userServiceImpl.userLogin(id, password);

			if (currentUser != null) {
				System.out.println("Login Sucess...\n");
				homePage(currentUser);
			}

			else {
				System.out.println("Invalid UserId & Password\n");

				performOperation();
			}

		} catch (IOException e) {

			System.out.println(e.getMessage());
		} catch (MyException e) {

			System.out.println(e.getMessage() + "\n");
			userLoginProcess();
		}

	}

	private void performForgotPassword() {

		System.out.println("****************Forgot Password Page***************");
		try {
			System.out.print("Enter your userId: ");
			String uid = buff.readLine();

			System.out.println();

			User u = userServiceImpl.forgotPassword(uid);
			if (u != null) {
				System.out.print("Enter your new password:");
				String pass = buff.readLine();
				if (userServiceImpl.setPassword(u, pass)) {
					System.out.println("Password reset Succesfully..\n");
					userLoginProcess();
				}
			} else {
				System.out.println("UserId does not exist...\n");
				performOperation();
			}
		} catch (IOException e) {

			System.out.println(e.getMessage());
		}

	}

	public void homePage(User currentUser) {

		System.out.println("***************Home Page***********************");

		try {
			System.out.println(
					"\n 1. Show User Details\n 2. Operation on Raw Material\n 3. Operation on Product\n 4. LogOut ");
			System.out.print("Enter your choice:");
			int choice = Integer.parseInt(buff.readLine());
			System.out.println();
			switch (choice) {
			case 1:
				userServiceImpl.myAccount(currentUser);
				break;

			case 2:
				System.out.println("********Operation on Raw Material*******");

				break;

			case 3:
				System.out.println("********Operation Product*******");
				break;

			case 4:
				if (userServiceImpl.logout())
					System.exit(0);
				break;

			default:
				throw new MyException("Invalid Choice");
			}
			System.out.println("Thank u for visiting my Page");
		} catch (Exception e) {

			System.out.println(e.getMessage() + "\n");
		}
	}

	public boolean isValidation(String userId, String userName, String email, String password, String phone,
			String gen) {

		try {
			if (userId.isEmpty()) {

				throw new MyException("Error Meaasage: UserId is empty");

			}
			if (userId.length() < 6) {

				throw new MyException("Error Meaasage: length of UserId should be at least 6");

			}
			if (userName.isEmpty()) {

				throw new MyException("Error Meaasage: UserName is empty");

			}
			if (email.isEmpty()) {

				throw new MyException("Error Meaasage: Email is empty");

			}
			Pattern pattern = Pattern.compile("^(.+)@(.+)$");
			Matcher matcher = pattern.matcher(email);
			if (!(matcher.matches())) {

				throw new MyException("Error Meaasage: email id is incorrect..plz enter correct email");

			}
			if (password.isEmpty()) {

				throw new MyException("Error Meaasage: password is empty");

			}
			if (password.length() < 6) {

				throw new MyException("Error Meaasage: length of password should be at least 6");

			}
			if (phone.isEmpty()) {

				throw new MyException("Error Meaasage: Phone is empty");

			}
			if (gen.isEmpty()) {

				throw new MyException("Error Meaasage: Gender is empty");

			}
			if (!(gen.equalsIgnoreCase("Male") || gen.equalsIgnoreCase("Female"))) {

				throw new MyException("Error Meaasage: plz enter correct gender...");

			}

		} catch (MyException e) {
			System.out.println(e.getMessage() + "\n");
			return false;
		}

		return true;
	}

}
