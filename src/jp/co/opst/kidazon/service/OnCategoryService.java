package jp.co.opst.kidazon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.opst.kidazon.dao.OnlineCategoryDao;
import jp.co.opst.kidazon.entity.OnCategoryEntity;

@Service
public class OnCategoryService {
	
	@Autowired
	OnlineCategoryDao dao;
	
	public List<OnCategoryEntity> findAll() {
		List<OnCategoryEntity> categoryList = dao.findAll();
		return categoryList;
	}
}
