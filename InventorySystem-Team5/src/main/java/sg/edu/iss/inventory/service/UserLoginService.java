package sg.edu.iss.inventory.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sg.edu.iss.inventory.repository.UserRepository;

@Service("UserLoginService")
public class UserLoginService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(final String userId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		sg.edu.iss.inventory.model.User user = userRepository.findByUserId(userId);
				
				List<GrantedAuthority> authorities =
                buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);

	}

	private User buildUserForAuthentication(sg.edu.iss.inventory.model.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUserId(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(String userRole) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		// Build user's authorities
		setAuths.add(new SimpleGrantedAuthority(userRole));
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
	
	
	public sg.edu.iss.inventory.model.User  getUserDetails()
	{
		sg.edu.iss.inventory.model.User user = new sg.edu.iss.inventory.model.User();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			user = userRepository.findByUserId(userDetail.getUsername());
		}
		return user;
	}
	

}
