package librarysystem.dao.service.impl;

import java.util.HashMap;
import java.util.Map.Entry;

import librarysystem.dao.service.UserDaoService;
import librarysystem.models.User;
import librarysystem.util.FileOperation;
import librarysystem.util.ServiceResponse;
import librarysystem.util.FileOperation.StorageType;

public class UserDaoServiceImpl implements UserDaoService {
	private static HashMap<String, User> users;

	@Override
	public void addUser(User user) throws ServiceResponse {
		HashMap<String, User> mems = readUserMap();
		if(!mems.containsKey(user.getUserName())) {
			mems.put(user.getUserName(), user);
			users = mems;
			FileOperation.saveToStorage(StorageType.USERS, mems);
			users.put(user.getUserName(), user);
		} else {
			System.out.println("ERROR: user already exist");
		}
		

	}

	@Override
	public User checkUser(User user) throws ServiceResponse {
		HashMap<String, User> mems = readUserMap();
		for (Entry<String, User> entry : mems.entrySet()) {
			User temp = entry.getValue();
			if (user.getUserName().equals(temp.getUserName())
					&& user.getUserPassword().equals(temp.getUserPassword()))
				return temp;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() throws ServiceResponse {
		if (users == null) {
			try {
				if (FileOperation.readFromStorage(StorageType.USERS) != null)
					users = (HashMap<String, User>) FileOperation
							.readFromStorage(StorageType.USERS);
				else
					return users = new HashMap<String, User>();

			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceResponse(false, e.getMessage());

			}
		}
		return users;
	}

}
