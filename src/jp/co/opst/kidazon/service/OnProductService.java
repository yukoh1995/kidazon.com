package jp.co.opst.kidazon.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import jp.co.opst.kidazon.dao.OnlineProductDao;
import jp.co.opst.kidazon.entity.OnProductEntity;
import jp.co.opst.kidazon.model.SearchProductFormModel;

@Service
public class OnProductService {
	
	@Autowired
	OnlineProductDao dao;
	
	public Map<String, OnProductEntity> findPage(SearchProductFormModel model, int page) {
		Map<String, String> map = new LinkedHashMap<>();
		
		if(model.getCategory() != null && !"".equals(model.getCategory())) {
			map.put("CATEGORY_ID", model.getCategory());
		}
		
		if(model.getName() != null && !"".equals(model.getName())) {
			map.put("PRODUCT_NAME", model.getName());
		}
		
		if(model.getMaker() != null && !"".equals(model.getMaker())) {
			map.put("MAKER", model.getMaker());
		}
		
		if(model.getLowerPrice() != null && !"".equals(model.getLowerPrice())) {
			map.put("MIN_UNIT_PRICE", model.getLowerPrice());
		}
		
		if(model.getHigherPrice() != null && !"".equals(model.getHigherPrice())) {
			map.put("MAX_UNIT_PRICE", model.getHigherPrice());
		}
		
		List<OnProductEntity> productList = dao.searchProduct(map, page);
		Map<String, OnProductEntity> productMap = new LinkedHashMap<String, OnProductEntity>();
		for(OnProductEntity product : productList) {
			String memo = product.getMemo();
			if(memo.length() > 20) {
				memo = memo.substring(0, 20) + "・・・";
				product.setMemo(memo);
			}
			productMap.put(product.getProductCode(), product);
		}
		
		return productMap;
	}
	
	public int foundRows() {
		int allDataNum = dao.foundRows();
		return allDataNum;
	}
	
	public OnProductEntity findById(String code) {
		OnProductEntity product = dao.findById(code);
		return product;
	}
	
	public boolean checkDelFlg(String code, ModelAndView mav) {
		OnProductEntity product = dao.findById(code);
		if(!product.getDeleteFlg().equals("0")) {
			mav.addObject("cantBuy", product.getProductName() + "の取り扱いは終了されたため購入できません。");
			return false;
		}
		return true;
	}
}
