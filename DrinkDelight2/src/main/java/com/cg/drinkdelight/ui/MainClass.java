package com.cg.drinkdelight.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.drinkdelight.dao.UtilityClass;
import com.cg.drinkdelight.exception.MyException;
import com.cg.drinkdelight.model.User;
import com.cg.drinkdelight.service.UserServiceImpl;

public class MainClass {

	InputStreamReader isr;
	BufferedReader buff;
	UserServiceImpl userServiceImpl;
	UtilityClass utilityClass;

	public static void main(String[] args) {

		MainClass mObject = new MainClass();
		mObject.isr = new InputStreamReader(System.in);
		mObject.buff = new BufferedReader(mObject.isr);
		mObject.userServiceImpl = new UserServiceImpl();
		mObject.utilityClass = new UtilityClass();
		mObject.performOperation();

	}

	public void performOperation() {

		try {

			utilityClass.showMessage(
					"*************Welcome to Drink and Delight*********** \n 1.Login \n 2.Register \n 3.Forgot Password ",
					1);
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
			utilityClass.showMessage("Exception msg:"+e.getMessage() + "\n", 1);

		}

	}

	public void userRegistrationProcess() {

		utilityClass.showMessage("****************Registration Page***************", 1);

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

						utilityClass.showMessage("Registreation Success...\n", 1);
						performOperation();
					} else {

						utilityClass.showMessage("Registration Failed...\n", 1);
						performOperation();
					}

				} else {

					utilityClass.showMessage("User Already exits...\n", 1);
					performOperation();
				}
			} else {
				performOperation();
			}

		} catch (Exception e) {

			utilityClass.showMessage("Exception Message: " + e.getMessage(), 1);
		}

	}

	public void userLoginProcess() {

		utilityClass.showMessage("****************Login Page***************", 1);

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
				utilityClass.showMessage("Login Sucess...\n", 1);
				homePage(currentUser);
			}

			else {

				utilityClass.showMessage("Invalid UserId & Password\n", 1);

				performOperation();
			}

		} catch (IOException e) {

			utilityClass.showMessage("Exception Message: " + e.getMessage(), 1);
		} catch (MyException e) {

			utilityClass.showMessage(e.getMessage() + "\n", 1);
			userLoginProcess();
		}

	}

	private void performForgotPassword() {

		utilityClass.showMessage("****************Forgot Password Page***************", 1);
		try {
			System.out.print("Enter your userId: ");
			String uid = buff.readLine();

			System.out.println();

			User u = userServiceImpl.forgotPassword(uid);
			if (u != null) {
				System.out.print("Enter your new password:");
				String pass = buff.readLine();
				if (userServiceImpl.setPassword(u, pass)) {
					utilityClass.showMessage("Password reset Succesfully..\n", 1);
					userLoginProcess();
				}
			} else {

				utilityClass.showMessage("UserId does not exist...\n", 1);
				performOperation();
			}
		} catch (IOException e) {

			utilityClass.showMessage("Exception Message: " + e.getMessage(), 1);
		}

	}

	public void homePage(User currentUser) {

		utilityClass.showMessage("***************Home Page***********************", 1);
		try {
			utilityClass.showMessage(
					"\n 1. Show User Details\n 2. Operation on Raw Material\n 3. Operation on Product\n 4. LogOut ", 1);
			System.out.print("Enter your choice:");
			int choice = Integer.parseInt(buff.readLine());
			System.out.println();
			switch (choice) {
			case 1:
				userServiceImpl.myAccount(currentUser);
				break;

			case 2:

				utilityClass.showMessage("********Operation on Raw Material*******", 1);
				break;

			case 3:

				utilityClass.showMessage("********Operation Product*******", 1);
				break;

			case 4:
				if (userServiceImpl.logout()) {
					System.out.println("Logout successfully...");
					System.exit(0);
				}

				break;

			default:
				throw new MyException("Invalid Choice");
			}

		} catch (Exception e) {
			utilityClass.showMessage("Exception message:"+ e.getMessage() + "\n", 1);
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

				throw new MyException("Error Meaasage: Invalid email Id");

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
			Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}"); 
			Matcher m = p.matcher(phone);
			
			if(!(m.find()&&m.group().equals(phone))) {
				throw new MyException("Error Meaasage: Invalid Mobile Number");
			}
			if (gen.isEmpty()) {

				throw new MyException("Error Meaasage: Gender is empty");

			}
			if (!(gen.equalsIgnoreCase("Male") || gen.equalsIgnoreCase("Female"))) {

				throw new MyException("Error Meaasage: Invalid Gender");

			}

		} catch (MyException e) {

			utilityClass.showMessage(e.getMessage() + "\n", 1);
			return false;
		}

		return true;
	}

}
