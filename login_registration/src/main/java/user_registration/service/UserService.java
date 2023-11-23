package user_registration.service;

import user_registration.model.User;

public interface UserService {
	
	public String login(String usermail, String password);
	public Boolean registration(User user);
}
