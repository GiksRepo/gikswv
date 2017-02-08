package com.online.adwork.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.online.adwork.commandobject.ContactCO;
import com.online.adwork.commandobject.PersonCO;
import com.online.adwork.configurations.ApplicationStartUp;
import com.online.adwork.domainobject.Contact;
import com.online.adwork.domainobject.Person;
import com.online.adwork.domainobject.UserRoles;
import com.online.adwork.domainobject.Users;

interface UserAuthDao
{
	public boolean saveUserProfile(PersonCO personCo);
	public boolean saveUserContact(ContactCO contactCo);
	public Users getUserCredentials(String loginId);
	public List <UserRoles> getUserRoles(Users user);
}

public class UserAuthDaoImpl implements UserAuthDao
{
	@Autowired
	private ApplicationStartUp applicationStartUp;
	
	public UserAuthDaoImpl() {
		// TODO Auto-generated constructor stub
		System.out.println("_____>>> CONST <<<<___");
	}

	@Override
	public boolean saveUserProfile(PersonCO personCo) 
	{
		System.out.println("Dao Layer for person");
		
		Person personDO = new Person();
		personDO.setFirstName(personCo.getFirstName());
		personDO.setMiddleName(personCo.getMiddleName());
		personDO.setLastName(personCo.getLastName());
		personDO.setGender(personCo.getGender());
		//personDO.setAge(personCo.getAge());
		//personDO.setDateOfBirth(personCo.getDateOfBirth());
		System.out.println("First Name :"+personDO.getFirstName()+" MiddleName :"+personDO.getMiddleName()+" LastName : "+personDO.getLastName()+" Gender : "+personDO.getGender());
		return false;
	}

	@Override
	public boolean saveUserContact(ContactCO contactCo) 
	{
		System.out.println("Dao Layer for contact");
		Contact contactDO = new Contact();
		contactDO.setCity(contactCo.getCity());
		contactDO.setCountry(contactCo.getCountry());
		contactDO.setState(contactCo.getState());
		contactDO.setEmailId(contactCo.getEmailId());
		contactDO.setIsEmailValid(contactCo.isEmailValid());
		contactDO.setPhoneNo(contactCo.getPhoneNo());
		contactDO.setIsPhonenoValid(contactCo.isPhonenoValid());
		System.out.println("City :"+contactDO.getCity()+" state :"+contactDO.getState()+" country:"+contactDO.getCountry()+" email:"+contactDO.getEmailId()+" phone no"+contactDO.getPhoneNo());
		return false;
	}

	@Override
	public Users getUserCredentials(String loginId) 
	{
		Users user = null;
		Session session = null;
		if(applicationStartUp == null)
			System.out.println("applicationStartUp is null");
		try{
			SessionFactory sessionFactory = applicationStartUp.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Users.class);
			criteria.add(Restrictions.eq("loginId", loginId));
			user = (Users)criteria.uniqueResult();
		}catch(Exception e){
			//debug code ToDo
			e.printStackTrace();
		}finally {
			if(session != null)
			{
				session.flush();
				session.close();
			}
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List <UserRoles> getUserRoles(Users user) {
		List <UserRoles> userRoles = null;
		Session session = null;
		try{
			SessionFactory sessionFactory = applicationStartUp.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Users.class);
			criteria = session.createCriteria(UserRoles.class);
			criteria.add(Restrictions.eq("user", user));
			userRoles = (List<UserRoles>)criteria.list();
		}catch(Exception e){
			//debug
		}finally{
			if(session != null)
			{
				session.flush();
				session.close();
			}
		}
		
		return userRoles;
	}
	
	

}
