package redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RedisDaoImplTest {

//	@Autowired
//	RedisDaoImpl<List<String>> redisDaoImpl;
	@Autowired
	RedisDaoImpl<User> redisDaoImpl;
	
	@Test
	public void testGet() {
//		List<String> l = new ArrayList<String>();
//		l.add("123");
//		l.add("we");
//		
//		redisDaoImpl.set("l", l);
//		
//		List<String> ll = redisDaoImpl.get("l");
//		System.out.println(ll.get(0));
		
		// object
//		User u = new User();
//		u.setId(1);
//		u.setName("jhww");
//		u.setDate(new Date());
//		redisDaoImpl.set("u", u);
//		
//		User u2 = redisDaoImpl.get("u");
//		System.out.println(u2.getDate());
		
		User u = new User(1,"建设",new Date());
		User u2 = new User(2,"建设撒旦法",new Date());
		List<User> us = new ArrayList<User>();
		us.add(u);
		us.add(u2);
		redisDaoImpl.set("us", us);
		
		//redisDaoImpl.remove("us");
		
		List<User> usc = redisDaoImpl.getList("us");
		System.out.println("12");
		
		
	}

//	@Test
	public void testSetStringObject() {
	}

//	@Test
	public void testSetStringObjectLong() {
	}

//	@Test
	public void testHasKey() {
	}

//	@Test
	public void testRemoveString() {
	}

//	@Test
	public void testRemoveStringArray() {
	}

//	@Test
	public void testRemovePattern() {
	}

}
