package jp.co.opst.kidazon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.opst.kidazon.entity.OnOrderListEntity;

@Repository
public class OnlineOrderListDao {

	@Autowired
    private NamedParameterJdbcTemplate namedTemplate;
	
	@Autowired
	HttpSession session;
	
	private class OrderListRowMapper extends BeanPropertyRowMapper<OnOrderListEntity> {
        @Override
        public OnOrderListEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OnOrderListEntity entity = new OnOrderListEntity();
        	entity.setListNo(rs.getString("LIST_NO"));
        	entity.setCollectNo(rs.getString("COLLECT_NO"));
        	entity.setProductCode(rs.getString("PRODUCT_CODE"));
        	entity.setOrderCount(rs.getString("ORDER_COUNT"));
        	entity.setOrderPrice(rs.getString("ORDER_PRICE"));
        	
            return entity;
        }
    }
	
	public int insert(OnOrderListEntity orderList) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ONLINE_ORDER_LIST ");
		sb.append("(COLLECT_NO, PRODUCT_CODE, ORDER_COUNT, ORDER_PRICE) ");
		sb.append("VALUES ");
		sb.append("(:collectNo, :productCode, :orderCount, :orderPrice);");
		String sql = sb.toString();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("collectNo", orderList.getCollectNo());
		paramMap.addValue("productCode", orderList.getProductCode());
		paramMap.addValue("orderCount",orderList.getOrderCount() );
		paramMap.addValue("orderPrice", orderList.getOrderPrice());
		
		int resultCount = this.namedTemplate.update(sql, paramMap);
		
		return resultCount;
	}
}
