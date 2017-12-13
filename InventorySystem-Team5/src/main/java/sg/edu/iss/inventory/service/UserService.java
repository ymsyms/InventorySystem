package sg.edu.iss.inventory.service;

import java.util.ArrayList;


import sg.edu.iss.inventory.model.User;

public interface UserService {

	ArrayList<User> findAllUser();
	
	User findUser(String userId);

	User createUser(User user);

	User changeUser(User user);


	void removeUser(User user);
	
	

	User authenticate(String uname, String pwd);
	
}