package user_registration.dao;

import org.hibernate.Session;
import user_registration.hashing.Hashage_mdp;
import user_registration.model.User;
import user_registration.util.Hibernate;

public class UserDaoImpl implements UserDao{
	
	
	public String login(String usermail, String password) {
		
		
		Session session = Hibernate.getSession();
		String uname = null;
		if (session != null) {

			   try {
			    User user = (User) session.get(User.class, usermail);
			    String mdp_hash= Hashage_mdp.get_SHA_512_SecurePassword(password,"@=u9a98EFx##");

			    if (mdp_hash.equals(user.getPassword())) {
					//System.out.println(mdp_hash+" equals to "+user.getPassword());
					uname = user.getUsername();
			     //return true;
			    }
			   } catch (Exception exception) {
			    System.out.println("Exception occured while reading user data: "
			      + exception.getMessage());
			    return null;
			}
		}else {
		   System.out.println("DB server down.....");
		}
		//return false;
		return uname;
	}


	public Boolean register(User user) {

		Boolean registatus = false;
		String msg = "Registration unsuccessful, try again.....";

		Session session = Hibernate.getSession();
		if (session != null) {
			try {
				if (user != null) {
					User usertestindb = (User) session.get(User.class, user.getEmailId());
					if (usertestindb==null){
						user.setPassword(Hashage_mdp.get_SHA_512_SecurePassword(user.getPassword(), "@=u9a98EFx##"));
						session.save(user);
						session.beginTransaction().commit();
						msg = "User " + user.getUsername()
								+ " created successfully, please login...";
						registatus = true;

						// logging statements
						System.out.println("User object saved to database: " + user);
					}
					else {
						msg = "This email " + user.getEmailId()
								+ " is already registered";
					}

				}
			} catch (Exception exception) {
				System.out.println("Exception occurred while saving user data: " + exception.getMessage());
			}
		} else {
			System.out.println("DB server down.....");
		}

		System.out.println(msg);
		return registatus;
	}
}
