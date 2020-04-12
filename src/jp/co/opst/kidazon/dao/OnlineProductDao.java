package jp.co.opst.kidazon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.opst.kidazon.entity.OnProductEntity;

@Repository
public class OnlineProductDao {
	
	@Autowired
    private NamedParameterJdbcTemplate namedTemplate;
	
	@Autowired
	private JdbcTemplate template;
	
	private class OnProductRowMapper extends BeanPropertyRowMapper<OnProductEntity> {
        @Override
        public OnProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OnProductEntity entity = new OnProductEntity();
        	
        	entity.setProductCode(rs.getString("PRODUCT_CODE"));
        	entity.setCategoryId(rs.getString("CATEGORY_ID"));
        	entity.setProductName(rs.getString("PRODUCT_NAME"));
        	entity.setMaker(rs.getString("MAKER"));
        	entity.setStockCount(rs.getString("STOCK_COUNT"));
        	entity.setRegisterDate(rs.getString("REGISTER_DATE"));
        	entity.setUnitPrice(rs.getString("UNIT_PRICE"));
        	entity.setPictureName(rs.getString("PICTURE_NAME"));
        	entity.setMemo(rs.getString("MEMO"));
        	entity.setDeleteFlg(rs.getString("DELETE_FLG"));
        	entity.setLastUpdDate(rs.getString("LAST_UPD_DATE"));
        	
            return entity;
        }   
    }
	
	public List<OnProductEntity> searchProduct(Map<String, String> map, int page) {
    	List<OnProductEntity> productList = new ArrayList<>();
    	
    	String sql = "SELECT SQL_CALC_FOUND_ROWS * FROM ONLINE_PRODUCT";
    	StringBuilder sb = new StringBuilder();
    	sb.append(sql);
    	sb.append(" WHERE DELETE_FLG = 0");
    	
    	if (map.size() > 0) {
			sb.append(" AND ");
		}
    	
    	int count = 1;
    	for(String key : map.keySet()) {
    		if("MAX_UNIT_PRICE".equals(key)) {
    			sb.append("UNIT_PRICE <= :max");
    		} else if("MIN_UNIT_PRICE".equals(key)) {
    			sb.append("UNIT_PRICE >= :min");
    		} else if("CATEGORY_ID".equals(key)){
    			sb.append("CATEGORY_ID = :categoryId");
    		} else if("PRODUCT_NAME".equals(key)){
    			sb.append(key + " LIKE :productName");
    		} else if("MAKER".equals(key)) {
    			sb.append(key + " LIKE :maker");
    		}
    		
    		if(count < map.size()) {
    			sb.append(" AND ");
    		}
    		count++;
    	}
    	
    	
    	sb.append(" LIMIT :page,10");
    	sb.append(";");
    	sql = sb.toString();
    	
    	MapSqlParameterSource paramMap = new MapSqlParameterSource();
    	
    	for (String key : map.keySet()) {
    		if("MAX_UNIT_PRICE".equals(key)) {
    			paramMap.addValue("max", Integer.parseInt(map.get(key)));
    		} else if("MIN_UNIT_PRICE".equals(key)) {
    			paramMap.addValue("min", Integer.parseInt(map.get(key)));
    		} else if("CATEGORY_ID".equals(key)) {
    			paramMap.addValue("categoryId", Integer.parseInt(map.get(key)));
    		} else if("PRODUCT_NAME".equals(key)) {
    			paramMap.addValue("productName","%" + map.get(key) + "%");
    		} else if("MAKER".equals(key)) {
    			paramMap.addValue("maker","%" + map.get(key) + "%");
    		}
    	}
    	paramMap.addValue("page", page);
    	
    	productList = this.namedTemplate.query(sql, paramMap, new OnProductRowMapper());
    	
    	return productList;
    }
	
	public int foundRows() {
		String sql = "SELECT FOUND_ROWS();";
		int result = this.template.queryForObject(sql, Integer.class);
		return result;
	}
	
	public OnProductEntity findById(String code) {
		String sql = "SELECT * FROM ONLINE_PRODUCT WHERE PRODUCT_CODE = :code";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("code", code);
		OnProductEntity product = this.namedTemplate.queryForObject(sql, paramMap, new OnProductRowMapper());
		return product;
	}
	
	public int updateStock(OnProductEntity entity) {
		
		String sql = "UPDATE ONLINE_PRODUCT ";
		StringBuilder sb = new StringBuilder(sql);
		sb.append("SET STOCK_COUNT = STOCK_COUNT - :buyCnt, ");
		sb.append("LAST_UPD_DATE = NOW() ");
		sb.append("WHERE PRODUCT_CODE = :code;");
		sql = sb.toString();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("buyCnt", Integer.parseInt(entity.getBuyCnt()));
		paramMap.addValue("code", entity.getProductCode());
		
		int resultCnt = this.namedTemplate.update(sql, paramMap);
		return resultCnt;
	}

}
