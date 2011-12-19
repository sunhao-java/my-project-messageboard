package com.message.main.admin.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.message.main.admin.pojo.Admin;

public class TestAdminDAO  {
	private static SessionFactory sessionFactory=null;
	
	@BeforeClass
	public static void beforeClass(){
		new SchemaExport(new Configuration().configure()).create(false, true);
		sessionFactory=new Configuration().configure().buildSessionFactory();
	}

	@Test
	public void testGetAdmins(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		AdminDAO adminDAO = (AdminDAO) context.getBean("adminDAO");
		
		List<Admin> admins = adminDAO.getAdmins();
		if(CollectionUtils.isNotEmpty(admins)){
			System.out.println("none");
		} else {
			System.out.println("no none");
		}
	}
	
	@AfterClass
	public static void afterClass(){
		sessionFactory.close();
	}
}
