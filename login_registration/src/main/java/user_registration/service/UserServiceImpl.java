package user_registration.service;

import user_registration.dao.UserDao;
import user_registration.dao.UserDaoImpl;
import user_registration.model.User;

public class UserServiceImpl implements UserService {
	
	private UserDao loginDao = new UserDaoImpl();
	
	public String login(String usermail, String password) {
		return loginDao.login(usermail, password);
	}

	public Boolean registration(User user) {
		return loginDao.register(user);
	}

}
