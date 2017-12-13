package sg.edu.iss.inventory.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import sg.edu.iss.inventory.model.User;
import sg.edu.iss.inventory.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	UserRepository userRepository;
    /** PasswordEncoder */

	
	/* (non-Javadoc)
	 * @see sg.edu.iss.inventory.service.StudentService#findAllUser()
	 */
//	@Override
//	@Transactional
//	public ArrayList<User> findAllValidUser()
//	{
//		ArrayList<User> userList = (ArrayList<User>)userRepository.findAllValidUser();
//		
//		return userList;
//	}
	
	@Transactional
	public User authenticate(String userId, String password) {
		User u = userRepository.findUserByIdPwd(userId, password);
		return u;
	}
	
	@Override
	@Transactional
	public ArrayList<User> findAllUser()
	{
		ArrayList<User> userList = (ArrayList<User>)userRepository.findAllValidUser();
		
		return userList;
	}
	
	
	@Override
	@Transactional
	public User findUser(String userId) {
		User user= userRepository.findByUserId(userId);
		return user;
	}
	
	@Override
	@Transactional
	public User createUser(User user) {
		user.setUserStatus("Valid");		

		return userRepository.saveAndFlush(user);
	}

	@Override
	@Transactional
	public User changeUser(User user) {
		user.setUserStatus("Valid");
		return userRepository.saveAndFlush(user);
	}

	@Override
	@Transactional
	public void removeUser(User user) {
		user.setUserStatus("InValid");
		userRepository.saveAndFlush(user);
	}


}