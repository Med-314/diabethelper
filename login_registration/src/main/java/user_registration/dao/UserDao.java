package user_registration.dao;

import user_registration.model.User;

public interface UserDao {
	
	public String login(String usermail, String password);
	public Boolean register(User user);

}
