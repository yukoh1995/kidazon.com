package jp.co.opst.kidazon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.opst.kidazon.entity.OnCategoryEntity;

@Repository
public class OnlineCategoryDao {
	
	@Autowired
	private JdbcTemplate template;
	
	private class CategoryRowMapper extends BeanPropertyRowMapper<OnCategoryEntity> {
        @Override
        public OnCategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OnCategoryEntity entity = new OnCategoryEntity();
        	
        	entity.setCtgrId(rs.getString("CTGR_ID"));
        	entity.setName(rs.getString("NAME"));
        	entity.setLastUpdTime(rs.getString("LAST_UPD_DATE"));
            return entity;
        }
    }
	
	public List<OnCategoryEntity> findAll() {
		String sql = "SELECT * FROM ONLINE_CATEGORY;";
		List<OnCategoryEntity> categoryList = this.template.query(sql, new CategoryRowMapper());
		return categoryList;
	}
}
