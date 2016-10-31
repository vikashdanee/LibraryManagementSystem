package librarysystem.controller;

import librarysystem.dao.factory.DataAccessFactory;
import librarysystem.dao.service.UserDaoService;
import librarysystem.enums.Role;
import librarysystem.models.User;
import librarysystem.util.ServiceResponse;

public class UserController {
	public static Role role = null;
	DataAccessFactory daoFactory = DataAccessFactory.getDAOFactory();
	UserDaoService userDao = daoFactory.getUserDAO();

	public ServiceResponse addUser(User user) throws Exception {
		try {

			userDao.addUser(user);
			return new ServiceResponse(true, "Successfully added");

		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false,
					ServiceResponse.getRuntimeException());
		}

	}

	public ServiceResponse checkUser(User user) throws Exception {
		try {
			User result = userDao.checkUser(user);
			if (result != null) {
				role = result.getRole();
				return new ServiceResponse(true, "User exist");
			} else {
				role = null;
				return new ServiceResponse(false, "User doesn't exist");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false,
					ServiceResponse.getRuntimeException());
		}
	}
}
