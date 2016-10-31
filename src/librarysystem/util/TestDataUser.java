package librarysystem.util;

import librarysystem.controller.UserController;
import librarysystem.enums.Role;
import librarysystem.models.User;

public class TestDataUser {

	private static void saveUser(User user) {
		try {
			new UserController().addUser(user);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			
			saveUser(new User("lib", "123", Role.LIBRARIAN));
			saveUser(new User("admin", "123", Role.ADMIN));
			saveUser(new User("both", "123", Role.BOTH));
			System.out.println("user added successfully");

		} catch (Exception e) {

		}
	}
}
