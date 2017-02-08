package com.online.adwork.configurations;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.online.adwork.domainobject.UserRoles;
import com.online.adwork.domainobject.Users;

@Component
public class ApplicationStartUp 
{
	private EntityManagerFactory entityManagerFactory;
	private SessionFactory sessionFactory;
	
	public void initIt() throws Exception
	{
		System.out.println("Intializing");
		if(entityManagerFactory == null)
		{
			entityManagerFactory = Persistence.createEntityManagerFactory("com.adwork");
			sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
			
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			
			Users user = new Users();
			user.setLoginId("123");
			user.setPassword("123");
			session.persist(user);
			
			UserRoles userRole = new UserRoles();
			userRole.setUser(user);
			userRole.setRole("ROLE_USER");
			session.persist(userRole);
			
			UserRoles userRole2 = new UserRoles();
			userRole2.setUser(user);
			userRole2.setRole("ROLE_ADMIN");
			session.persist(userRole2);
			
			transaction.commit();
			session.close();
		}
	}
	
	
	public SessionFactory getSessionFactory() throws Exception
	{
		System.out.println("Creating sessionFactory");
		
		if(entityManagerFactory == null)
			entityManagerFactory = Persistence.createEntityManagerFactory("com.adwork");
		if(sessionFactory == null)
			sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		
		return sessionFactory;
	}
	
	public void cleanUp() throws Exception
	{
		System.out.println("Cleaning Up");
		if(entityManagerFactory != null)
			entityManagerFactory.close();
		if(sessionFactory != null)
			sessionFactory.close();
	}
}
