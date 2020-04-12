package jp.co.opst.kidazon.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import jp.co.opst.kidazon.entity.OnProductEntity;

@Service
public class CartValidation {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ShareValidations valis;
	
	
	public boolean orderValidation(Map<String, OnProductEntity> inCartMap, ModelAndView mav) {
		
		List<String> orderValiError = new ArrayList<>();
		
		for(OnProductEntity product : inCartMap.values()) {
			if(!"".equals(product.getBuyCnt()) && !valis.stockCheck(product.getBuyCnt(), product.getStockCount())) {
				orderValiError.add("在庫が足りません。購入数を変更してください。");
			}
		}
		
		if(orderValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("orderValiError", orderValiError);
			return false;
		}
	}
	
	public boolean canBuyValidation(List<OnProductEntity> regiProductList, List<OnProductEntity> dbProductList, ModelAndView mav) {
		List<String> orderResultValiError = new ArrayList<>();
		
		
		for(OnProductEntity product : regiProductList) {
			if(!product.getDeleteFlg().equals("0")) {
				orderResultValiError.add("選んだ商品は購入することができません。");
			}
			
			boolean sameProduct = false;
			for(OnProductEntity dbProduct : dbProductList) {
				if(product.getProductCode().equals(dbProduct.getProductCode())) {
					sameProduct = true;
					break;
				} 
			}
			
			if(!sameProduct) {
				orderResultValiError.add("選んだ商品は購入することができません。");
			}
		}
		
		if(orderResultValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("orderValiError", orderResultValiError);
			return false;
		}
	}
	
	public boolean cartProductVali(String buyCnt, ModelAndView mav) {
		List<String> buyCntValiError = new ArrayList<>();
		
		if(!valis.emptyCheck(buyCnt)) {
			buyCntValiError.add("購入数は1~999の数値で入力してください。");
			
		}else if(valis.isNumber(buyCnt)) {
			int intBuyCnt = Integer.parseInt(buyCnt);
			if(!valis.bet1and999Check(intBuyCnt)) {
				buyCntValiError.add("購入数は1~999の数値で入力してください。");
			}
		} else {
			buyCntValiError.add("購入数は1~999の数値で入力してください。");
		}
		
		if(buyCntValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("buyCntValiError", buyCntValiError);
			return false;
		}
	}
}
