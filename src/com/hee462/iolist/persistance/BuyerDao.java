package com.hee462.iolist.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hee462.iolist.config.DBContract;
import com.hee462.iolist.models.BuyerDto;
import com.hee462.iolist.persistance.sql.BuyerSQL;

/*
 * DBMS 와 Query 를 보내고, 데이터를 받을 method 선언
 * 인터페이스에 Query method를 선언만 해 두면 
 * Mybatis 의 SessionFactory 가 실제 사용될 코드를 자동으로 작성한다
 */
public interface BuyerDao {
	@Select(" SELECT * FROM "+ DBContract.TABLE.BUYER)
	public List<BuyerDto> selectAll();
	//PK 를 기준으로 조회
	@Select(" SELECT * FROM "+ DBContract.TABLE.BUYER 
			 					+" WHERE buId =#{id} ")
	public BuyerDto findById(String id);
	@Insert(BuyerSQL.INSERT)
	public int insert(BuyerDto dto);
	@Update(BuyerSQL.UPDATE)
	public int update(BuyerDto dto);
	
	@Delete(" DELETE FROM " +DBContract.TABLE.BUYER +
								"WHERE buid = #{id}")
	public int delete(String id);
	/*
	 * 고객정보를 관리하기 위해서 추가할 기능
	 * 1.고객 이름으로 조회하기
	 * 2.고객 전화번호로 조회하기
	 * 
	 */
	@Select("SELECT * FROM " + DBContract.TABLE.BUYER +" WHERE buName LIKE '%' || #{name} || '%' ")

	public List<BuyerDto> findByName(String name);
	@Select("SELECT * FROM " + DBContract.TABLE.BUYER +" WHERE butel LIKE  '%' || #{tel} || '%' ")
	public List<BuyerDto> findByTel(String tel);
	@Select("SELECT max(buid) FROM " + DBContract.TABLE.BUYER)
	public String getMaxID();
	
	
	/*
	 * select(조회)method를 만들때 주의사항
	 * 조회대상(where 절에서 사용) PK 일대는
	 * 		조회되는 데이터가 없거나(null),1개뿐
	 * 		이때 method의 return type은 Dto로 설정
	 * 		SELECT * FROM tbl_buyer WHERE buid ='0001'
	 * 있으면 나오고 아니라면 절대 안나옴
	 * 
	 * 조회대상이 PK가 아닌 경우
	 * 		조회되는 데이터가 없거나(empty),1개 이상(중복 가능)
	 * 		이때 method의 return type List<Dto>로 설정
	 * 		SELECT * FROM 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
}
