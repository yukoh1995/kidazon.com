package jp.co.opst.kidazon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.opst.kidazon.entity.OnOrderEntity;

@Repository
public class OnlineOrderDao {
	
	@Autowired
    private NamedParameterJdbcTemplate namedTemplate;
	
	@Autowired
	HttpSession session;
	
	private class OrderRowMapper extends BeanPropertyRowMapper<OnOrderEntity> {
        @Override
        public OnOrderEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OnOrderEntity entity = new OnOrderEntity();
        	entity.setOrderNo(rs.getString("ORDER_NO"));
        	entity.setMemberNo(rs.getString("MEMBER_NO"));
        	entity.setTotalMoney(rs.getString("TOTAL_MONEY"));
        	entity.setTotalTax(rs.getString("TOTAL_TAX"));
        	entity.setOrderDate(rs.getString("ORDER_DATE"));
        	entity.setCollectNo(rs.getString("COLLECT_NO"));
            entity.setLastUpdDate(rs.getString("LAST_UPD_DATE"));
            return entity;
        }
    }
	
	public int insert(OnOrderEntity order) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ONLINE_ORDER ");
		sb.append("(MEMBER_NO, TOTAL_MONEY, TOTAL_TAX, ");
		sb.append("ORDER_DATE, COLLECT_NO, LAST_UPD_DATE) ");
		sb.append("VALUES ");
		sb.append("(:memberNo, :totalMoney, :totalTax, ");
		sb.append("DATE(NOW()), :collectNo, :lastUpdDate);");
		
		String sql = sb.toString();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd HHmmss");
		String collectNo = sdf.format(timestamp);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("memberNo", Integer.parseInt(order.getMemberNo()));
		paramMap.addValue("totalMoney", Integer.parseInt(order.getTotalMoney()));
		paramMap.addValue("totalTax", Integer.parseInt(order.getTotalTax()));
		paramMap.addValue("collectNo", collectNo);
		paramMap.addValue("lastUpdDate", timestamp);
		
		int resultCount = this.namedTemplate.update(sql, paramMap);
		session.setAttribute("collectNo", collectNo);
		
		return resultCount;
		
	}
}
