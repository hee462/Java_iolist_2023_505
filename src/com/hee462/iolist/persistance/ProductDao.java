package com.hee462.iolist.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hee462.iolist.config.DBContract;
import com.hee462.iolist.models.ProductDto;
import com.hee462.iolist.persistance.sql.ProductSQL;

public interface ProductDao {
	@Select("SELECT * FROM " + DBContract.TABLE.PRODUCT
			 					+" ORDER BY pCode")
	public List<ProductDto> selectAll();
	
	@Select("SELECT * FROM " + DBContract.TABLE.PRODUCT
				+" WHERE pCode =#{id}")
	public ProductDto findById(String ioPCode);
	
	@Insert(ProductSQL.INSERT)
	public int insert(ProductDto productDto);
	
	@Update(ProductSQL.UPDATE)
	public int update(ProductDto productDto);
	
	@Select("SELECT * FROM "
							+ DBContract.TABLE.PRODUCT
							+ " WHERE pName LIKE '%' || #{pName} || '%' "
							+ " ORDER BY pName ")
	
	public List<ProductDto> findByPName(String pName);
}
