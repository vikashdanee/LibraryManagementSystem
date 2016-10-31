package librarysystem.dao.service;

import librarysystem.models.User;
import librarysystem.util.ServiceResponse;

public interface UserDaoService {
	public void addUser(User user) throws ServiceResponse;

	public User checkUser(User user) throws ServiceResponse;
}
