package com.hee462.iolist.exec;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.hee462.iolist.config.DBConnection;
import com.hee462.iolist.models.BuyerDto;
import com.hee462.iolist.persistance.BuyerDao;

public class ExecA {
	public static void main(String[] args) {
		
		SqlSessionFactory sqlSessionFactory = DBConnection.getSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		// mybatis야 BuyerDao interface를 참조하여
		// Query 를 실행할 method를 만들어달라
		BuyerDao buyerDao = sqlSession.getMapper(BuyerDao.class);
		
		List<BuyerDto> buyerList = buyerDao.selectAll();
		for(BuyerDto dto :buyerList) {
			System.out.println(dto.toString());
			
		}
		
	}
}
