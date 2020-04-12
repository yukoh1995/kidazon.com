package jp.co.opst.kidazon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.opst.kidazon.entity.OnMemberEntity;

@Repository
public class OnlineMemberDao {
	
	@Autowired
    private NamedParameterJdbcTemplate namedTemplate;
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	HttpSession session;

	private class UserInfoRowMapper extends BeanPropertyRowMapper<OnMemberEntity> {
        @Override
        public OnMemberEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OnMemberEntity entity = new OnMemberEntity();
        	entity.setMemberNo(rs.getString("MEMBER_NO"));
        	entity.setPass(rs.getString("PASSWORD"));
            entity.setName(rs.getString("NAME"));
            entity.setAge(rs.getString("AGE"));
            entity.setSex(rs.getString("SEX"));
            entity.setZip(rs.getString("ZIP"));
            entity.setAddr(rs.getString("ADDRESS"));
            entity.setTel(rs.getString("TEL"));
            entity.setRegisterDate(rs.getDate("REGISTER_DATE"));
            entity.setDeleteFlg(rs.getString("DELETE_FLG"));
            entity.setLastUpdDate(rs.getString("LAST_UPD_DATE"));
            return entity;
        }
    }
	
	public String maxMemberNo() {
		String sql = "SELECT COALESCE(MAX(MEMBER_NO), 0)+1 FROM ONLINE_MEMBER;;";
		int nextMemNo = this.template.queryForObject(sql, Integer.class);
		String retMemNo = String .valueOf(nextMemNo);
		return retMemNo;
	}
	
	public int register(OnMemberEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ONLINE_MEMBER ");
		sb.append("(MEMBER_NO, PASSWORD, NAME, AGE, SEX, ZIP, ADDRESS, TEL, ");
		sb.append("REGISTER_DATE, LAST_UPD_DATE) ");
		sb.append("VALUES ");
		sb.append("(:memberNo, :password, :name, :age, :sex, :zip, :address, :tel, DATE(NOW()), NOW());");
		
		String sql = sb.toString();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		int yourNo = Integer.parseInt(maxMemberNo());
		paramMap.addValue("memberNo", yourNo);
		paramMap.addValue("password", entity.getPass());
		paramMap.addValue("name", entity.getName());
		paramMap.addValue("age", Integer.parseInt(entity.getAge()));
		paramMap.addValue("sex", entity.getSex());
		paramMap.addValue("zip", entity.getZip());
		paramMap.addValue("address", entity.getAddr());
		paramMap.addValue("tel", entity.getTel());
		
		int resultCount = this.namedTemplate.update(sql, paramMap);
		entity.setMemberNo(String.valueOf(yourNo));
		
		return resultCount;
	}
	
	public List<OnMemberEntity> login(OnMemberEntity entity) {
        String sql = "SELECT * FROM ONLINE_MEMBER WHERE MEMBER_NO = :memberNo AND PASSWORD = :password";
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("memberNo", Integer.parseInt(entity.getMemberNo()));
        paramMap.addValue("password", entity.getPass());
        List<OnMemberEntity> userInfoList = this.namedTemplate.query(sql, paramMap, new UserInfoRowMapper());
        
        return userInfoList;
    }
	
	public int update(OnMemberEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ONLINE_MEMBER SET ");
		sb.append("PASSWORD = :password, NAME = :name, ");
		sb.append("AGE = :age, SEX = :sex, ZIP = :zip, ADDRESS = :address, ");
		sb.append("TEL = :tel, LAST_UPD_DATE = NOW() ");
		sb.append("WHERE MEMBER_NO = :memberNum;");
		String sql = sb.toString();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("password", entity.getPass());
		paramMap.addValue("name", entity.getName());
		paramMap.addValue("age", Integer.parseInt(entity.getAge()));
		paramMap.addValue("sex", entity.getSex().toUpperCase());
		paramMap.addValue("zip", entity.getZip());
		paramMap.addValue("address", entity.getAddr());
		paramMap.addValue("tel", entity.getTel());
		paramMap.addValue("memberNum", Integer.parseInt(entity.getMemberNo()));
		
		int resultCount = this.namedTemplate.update(sql, paramMap);
		return resultCount;
	}
	
	public int delete(OnMemberEntity entity) {
		String sql = "UPDATE ONLINE_MEMBER SET DELETE_FLG = '1', LAST_UPD_DATE = NOW() WHERE MEMBER_NO = :memberNo;";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("memberNo", Integer.parseInt(entity.getMemberNo()));
		
		int resultCount = this.namedTemplate.update(sql, paramMap);
		return resultCount;
	}
	
	public Date findRegisterDate(String id) {
		String sql = "SELECT REGISTER_DATE FROM ONLINE_MEMBER WHERE MEMBER_NO = :memberNo;";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("memberNo", Integer.parseInt(id));
		Date registerDate = this.namedTemplate.queryForObject(sql, paramMap, Date.class);
		return registerDate;
	}
}
