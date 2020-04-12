package jp.co.opst.kidazon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.opst.kidazon.entity.OnProductEntity;
import jp.co.opst.kidazon.service.OnOrderService;
import jp.co.opst.kidazon.service.OnProductService;
import jp.co.opst.kidazon.validation.CartValidation;

@Controller
public class CartController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OnOrderService service;
	
	@Autowired
	OnProductService proService;
	
	@Autowired
	CartValidation vali;
	

	@RequestMapping("/cart/cart")
	public String showCart() {
		return "cart/cart";
	}
	
	@RequestMapping(path="/cart/confCart")
	public ModelAndView showConfCart(@RequestParam(name = "productCode", required=false) List<String> codeList, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("cart/confCart");
		Map<String, String> buyCntMap = new LinkedHashMap<>();
		if(session.getAttribute("buyCntMap") != null) {
			buyCntMap = (Map<String, String>) session.getAttribute("buyCntMap");
		}
		
		Map<String, OnProductEntity> inCartMap = new LinkedHashMap<>();
		if(session.getAttribute("inCartMap") != null) {
			inCartMap = (Map<String, OnProductEntity>) session.getAttribute("inCartMap");
		}
		
		if(codeList != null) {
			for(String code : codeList) {
				String buyCnt = request.getParameter(code);
				boolean error = vali.cartProductVali(buyCnt, mav);
				if(!error) {
					mav.setViewName("cart/cart");
					return mav;
				} else {
					buyCntMap.put(code, buyCnt);
					session.setAttribute("buyCntMap", buyCntMap);
				}
			}
		}
		
		int totalPrice = 0;
		int intPrice = 0;
		int intBuyCnt = 0;
		for(String key : buyCntMap.keySet()) {
			OnProductEntity thisProduct = inCartMap.get(key);
			
			thisProduct.setBuyCnt(buyCntMap.get(key));
			inCartMap.put(key, thisProduct);
			intPrice = Integer.parseInt(thisProduct.getUnitPrice());
			intBuyCnt = Integer.parseInt(buyCntMap.get(key));
			totalPrice += intPrice * intBuyCnt;
		}
		
		boolean hasStock = vali.orderValidation(inCartMap, mav);
		if(!hasStock) {
			mav.setViewName("cart/cart");
			return mav;
		}
		
		double tax = 0.08;
		int displayTax = ((int) (totalPrice * tax));
		
		Map<String, Integer> priceMap = new HashMap<>();
		priceMap.put("totalTax", displayTax);
		priceMap.put("totalMoney", totalPrice);
		session.setAttribute("totalMap", priceMap);
		mav.addObject("totalPrice", totalPrice);
		mav.addObject("displayTax", displayTax);
		
		session.setAttribute("inCartMap", inCartMap);
		
		return mav;
	}
	
	
	@RequestMapping("/cart/orderResult")
	public ModelAndView showOrderResult() {
		ModelAndView mav = new ModelAndView();
		String isLogin = (String) session.getAttribute("yourName");
		
		Map<String, OnProductEntity> inCartMap = new LinkedHashMap<>();
		if(session.getAttribute("inCartMap") != null) {
			inCartMap = (Map<String, OnProductEntity>) session.getAttribute("inCartMap");
		}
		
		if(isLogin != null) {
			List<Boolean> hasError = new ArrayList<>();
			for(OnProductEntity product : inCartMap.values()) {
				boolean result = proService.checkDelFlg(product.getProductCode(), mav);
				hasError.add(result);
			}
			
			if(hasError.contains(false)) {
				mav.setViewName("cart/confCart");
				return mav;
			}
			
			try {
				service.insertOrder(inCartMap);
				
			} catch(Exception e) {
				e.printStackTrace();
				mav.setViewName("cart/confCart");
				return mav;
			}
			
			session.removeAttribute("inCartMap");
			session.removeAttribute("totalMap");
			session.removeAttribute("nextPage");
			session.removeAttribute("returnPage");
			session.removeAttribute("buyCntMap");
			
			
			mav.setViewName("cart/orderResult");
			return mav;
		} else {
			mav.setViewName("redirect: ../userLogin.html");
			session.setAttribute("fromWhere", "od");
			return mav;
		}
	}
	
	@RequestMapping("/cart/reset")
	public ModelAndView resetCart(@RequestParam(name = "inCartCheck", defaultValue = "blank") List<String> inCartCheck) {
		ModelAndView mav = new ModelAndView("cart/cart");
		
		if(inCartCheck.contains("blank")) {
			mav.addObject("notSelect", "取り消し対象の商品を選択してください。");
			return mav;
		}
		
		Map<String, OnProductEntity> inCartMap = new LinkedHashMap<>();
		if(session.getAttribute("inCartMap") != null) {
			inCartMap = (Map<String, OnProductEntity>) session.getAttribute("inCartMap");
		}
		
		
		for(String inCartNum : inCartCheck) {
			inCartMap.remove(inCartNum);
		}
		session.setAttribute("inCartMap", inCartMap);
		
		return mav;
		
	}
	
	@RequestMapping("/cart/resetAll")
	public ModelAndView resetAll() {
		ModelAndView mav = new ModelAndView("redirect: ../userMenu.html");
			
		session.removeAttribute("inCartMap");
		session.removeAttribute("totalMap");
		session.removeAttribute("nextPage");
		session.removeAttribute("returnPage");
		session.removeAttribute("buyCntMap");
		
		
		return mav;
	}
}
