package com.cellbiotech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import com.cellbiotech.mapper.sfa.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SfaApplicationTests {

//	@Autowired
//	private DataSource dataSource;
//
//	@Autowired
//	private DataSource barDataSource;
//
//	@Autowired
//	private SqlSessionFactory sqlSessionFactory;


//	@Autowired
//	private UserMapper userMapper;



	@Test
	public void contextLoads() {
	}

//	@Test
//	public void testConnection() throws Exception {
//		System.out.println("dataSource :: " + dataSource);
//
//		Connection con = dataSource.getConnection();
//
//		System.out.println("con :: " + con);
//
//		con.close();
//	}
//
//	@Test
//	public void testConnection2() throws Exception {
//		System.out.println("barDataSource :: " + barDataSource);
//
//		Connection con = barDataSource.getConnection();
//
//		System.out.println("con :: " + con);
//
//		con.close();
//	}
//
//	@Test
//	public void testSqlSession() throws Exception {
//		System.out.println("sqlSessionFactory :: " + sqlSessionFactory);
//	}

	@Test
	public void testMapper() throws Exception {
//		System.out.println(userMapper.selectUserList());

	}

}
