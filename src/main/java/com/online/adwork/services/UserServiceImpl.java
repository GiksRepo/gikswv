package com.online.adwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.adwork.commandobject.ContactCO;
import com.online.adwork.commandobject.PersonCO;
import com.online.adwork.dao.UserAuthDaoImpl;
import com.online.adwork.viewobject.UserRegistration;

interface UserService
{
	public boolean saveUserRegistration(UserRegistration userRegistration);
}

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserAuthDaoImpl UserDaoImpl;
	
	@Override
	public boolean saveUserRegistration(UserRegistration userRegistration) 
	{
		System.out.println("Service Layer");
		boolean flag = false;
		
		PersonCO personCO = new PersonCO();
		personCO.setFirstName(userRegistration.getFirstName());
		personCO.setMiddleName(userRegistration.getMiddleName());
		personCO.setLastName(userRegistration.getLastName());
		personCO.setGender(userRegistration.getGender());
		//personCO.setDateOfBirth(java.sql.Date.valueOf(userRegistration.getDateOfBirth()));
		//personCO.setAge(userRegistration.getAge().byteValue());
		try 
		{
			flag = UserDaoImpl.saveUserProfile(personCO);
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		
		ContactCO contactCO = new ContactCO();
		contactCO.setCity(userRegistration.getCity());
		contactCO.setState(userRegistration.getState());
		contactCO.setCountry(userRegistration.getCountry());
		contactCO.setEmailId(userRegistration.getEmailId());
		contactCO.setPhoneNo(userRegistration.getPhoneNo());
		contactCO.setEmailValid(false);
		contactCO.setPhonenoValid(false);
		
		try 
		{
			flag = UserDaoImpl.saveUserContact(contactCO);		
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		
		
		return flag;
	}

}
