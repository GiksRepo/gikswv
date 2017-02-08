package com.online.adwork.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.online.adwork.dao.UserAuthDaoImpl;
import com.online.adwork.domainobject.UserRoles;
import com.online.adwork.domainobject.Users;

@Component
public class AuthenticationService implements UserDetailsService
{	
	@Autowired
	private UserAuthDaoImpl userAuthDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		System.out.println("Getting access details from uu dao !!"+username);
		Users user = null;
		List <UserRoles> userRoles = null;
		User springUser = null;
		try {
			user = userAuthDao.getUserCredentials(username);
			userRoles = userAuthDao.getUserRoles(user);
			System.out.println("auths : >>>>>>>"+userRoles.get(0).getRole());
			String userAuthorities = null;
			List<String> roleArr = new ArrayList<String>();
			if(userRoles !=null && !userRoles.isEmpty()){
				
				for(UserRoles userRoleTemp:userRoles)
					roleArr.add(userRoleTemp.getRole());
				userAuthorities = StringUtils.arrayToCommaDelimitedString(roleArr.toArray());
			}
			System.out.println("auths : >>>>>>>"+userAuthorities);
			if (user == null) {
				System.out.println("hello i did");
		        throw new UsernameNotFoundException("Invalid username/password.");
		    }
		    Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roleArr.get(0),roleArr.get(1));
		    		springUser = new User(user.getLoginId(), user.getPassword(), authorities);
			
		    System.out.println("userId"+user.getLoginId());
		    System.out.println("Role:"+user.getPassword());
		    System.out.println("Role:"+user.getPassword());
			
		} catch (Exception e) {
			//debug
			e.printStackTrace();
		}
		finally {
		}
		
		
	    return springUser;
	}

}
