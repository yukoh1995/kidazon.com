package jp.co.opst.kidazon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.opst.kidazon.dao.OnlineOrderDao;
import jp.co.opst.kidazon.dao.OnlineOrderListDao;
import jp.co.opst.kidazon.dao.OnlineProductDao;
import jp.co.opst.kidazon.entity.OnOrderEntity;
import jp.co.opst.kidazon.entity.OnOrderListEntity;
import jp.co.opst.kidazon.entity.OnProductEntity;

@Service
public class OnOrderService {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OnlineOrderDao orderDao;
	
	@Autowired
	OnlineOrderListDao orderListDao;
	
	@Autowired
	OnlineProductDao proDao;
	
	@Transactional(rollbackForClassName = "Exception")
	public boolean insertOrder(Map<String, OnProductEntity> inCartMap) throws Exception {
		//ONLINE_ORDERの処理
		OnOrderEntity entity = new OnOrderEntity();
		String yourNo = (String) session.getAttribute("yourNo");
		Map<String, Integer> map = (Map<String, Integer>) session.getAttribute("totalMap");
		
		List<Integer> resultList = new ArrayList<>();
		
		entity.setMemberNo(yourNo);
		entity.setTotalMoney(String.valueOf(map.get("totalMoney")));
		entity.setTotalTax(String.valueOf(map.get("totalTax")));
		
		int result = orderDao.insert(entity);
		resultList.add(result);
		
		
		//ONLINE_ORDER_LISTの処理
		
		String collectNo = (String) session.getAttribute("collectNo");
		
		OnOrderListEntity liEntity = new OnOrderListEntity();
		
		for(OnProductEntity product : inCartMap.values()) {
			liEntity.setCollectNo(collectNo);
			liEntity.setProductCode(product.getProductCode());
			liEntity.setOrderCount(product.getBuyCnt());
			liEntity.setOrderPrice(product.getUnitPrice());
			
			result = orderListDao.insert(liEntity);
			resultList.add(result);
		}
		
		//ORDER_PRODUCTから在庫をへらす処理
		OnProductEntity proEntity = new OnProductEntity();
		
		for(OnProductEntity product : inCartMap.values()) {
			proEntity.setStockCount(product.getStockCount());
			proEntity.setProductCode(product.getProductCode());
			proEntity.setBuyCnt(product.getBuyCnt());
			
			result = proDao.updateStock(proEntity);
			resultList.add(result);
		}
		
		for(int resultCheck : resultList) {
			if (resultCheck != 0) {
	            return true;
	        } else {
	            throw new Exception();
			}
		}
		return false;
	}
}
