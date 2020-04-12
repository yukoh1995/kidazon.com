package jp.co.opst.kidazon.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.opst.kidazon.entity.OnProductEntity;
import jp.co.opst.kidazon.model.SearchProductFormModel;
import jp.co.opst.kidazon.service.OnProductService;
import jp.co.opst.kidazon.validation.SearchValidation;

@Controller
public class SearchController {
	
	@Autowired
	OnProductService service;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	SearchValidation vali;

	@RequestMapping("/search/searchProduct")
	public String showSearchProduct() {
		return "search/searchProduct";
	}
	
	private int thisPage = 0;
	private int lastPageNum = 0;
	
	@RequestMapping(path="/search/searchingProduct", method= RequestMethod.POST)
	public ModelAndView searchingProduct(SearchProductFormModel model) {
		ModelAndView mav = new ModelAndView("search/searchProduct");
		boolean noError = true;
		
		if(!model.getLowerPrice().equals("") && !model.getHigherPrice().equals("")) {
			noError = vali.doublePriceVali(model.getLowerPrice(), model.getHigherPrice(), mav);
		} 
		
		if(!model.getLowerPrice().equals("")) {
			noError = vali.lowPriceValidation(model.getLowerPrice(), mav);
		}
		
		if(!model.getHigherPrice().equals("")) {
			noError = vali.highPriceValidation(model.getHigherPrice(), mav);
		}
		
		
		
		//ここまで来たらすべてのバリデーションは実施済み
		session.setAttribute("prodSearchCondition", model);
		mav.addObject("Inputed", model);
		
		if(noError) {
			Map<String, OnProductEntity> productMap = service.findPage(model, 0);
			if(productMap.isEmpty()) {
				mav.addObject("data0", "条件に該当する商品は0件です。");
			}
			
			int allPageNum = service.foundRows();
			 
			BigDecimal allData = new BigDecimal(allPageNum);
			BigDecimal ten = new BigDecimal(10);
			allData = allData.divide(ten);
			allData = allData.setScale(0, BigDecimal.ROUND_UP);
			lastPageNum = allData.intValue();
			
			if(lastPageNum == 0) {
				lastPageNum = 1;
			}
			thisPage = 1;
			mav.addObject("thisPage", thisPage);
			mav.addObject("lastPageNum", lastPageNum);
			mav.addObject("productMap", productMap);
			session.setAttribute("nextPage", 10);
			session.setAttribute("returnPage", 0);
//	        session.setAttribute("seProductList", productList);
		}
		
		return mav;
	}
	
	@RequestMapping("/search/next")
	public ModelAndView showNextPage(@RequestParam(name = "nextPage") int page) {
		SearchProductFormModel model = (SearchProductFormModel) session.getAttribute("prodSearchCondition");
		Map<String, OnProductEntity> productMap = service.findPage(model, page);
		ModelAndView mav = new ModelAndView("search/searchProduct");
		mav.addObject("lastPageNum", lastPageNum);
		
		
		if(page >= lastPageNum * 10) {
			productMap = service.findPage(model, page - 10);
			mav.addObject("productMap", productMap);
			mav.addObject("thisPage", thisPage);
			session.setAttribute("nextPage", page);
			session.setAttribute("returnPage", page - 20);
		} else {
			mav.addObject("productMap", productMap);
			thisPage += 1;
			mav.addObject("thisPage", thisPage);
			session.setAttribute("nextPage", page + 10);
			session.setAttribute("returnPage", page - 10);
//			session.setAttribute("seProductList", productList);
		}
		
        return mav;
	}
	
	@RequestMapping("/search/return")
	public ModelAndView showReturnPage(@RequestParam(name = "returnPage") int page) {
		SearchProductFormModel model = (SearchProductFormModel) session.getAttribute("prodSearchCondition");
		Map<String, OnProductEntity> productMap = new LinkedHashMap<>();
		if(page > 0) {
			productMap = service.findPage(model, page);
		} else {
			productMap = service.findPage(model, 0);
			page = 0;
		}
		ModelAndView mav = new ModelAndView("search/searchProduct", "productMap", productMap);
		mav.addObject("lastPageNum", lastPageNum);
		if (page != 0) {
			thisPage -= 1;
			mav.addObject("thisPage", thisPage);
			session.setAttribute("nextPage", page + 10);
            session.setAttribute("returnPage", page - 10);
        } else {
        	thisPage = 1;
        	mav.addObject("thisPage", thisPage);
        	session.setAttribute("nextPage", 10);
        	session.setAttribute("returnPage", 0);
        }
//		session.setAttribute("seProductList", productList);
        return mav;
	}
	
	@RequestMapping("/search/top")
	public ModelAndView showTopPage() {
		SearchProductFormModel model = (SearchProductFormModel) session.getAttribute("prodSearchCondition");
		Map<String, OnProductEntity> productMap = service.findPage(model, 0);
		ModelAndView mav = new ModelAndView("search/searchProduct", "productMap", productMap);
		mav.addObject("lastPageNum", lastPageNum);
		thisPage = 1;
		mav.addObject("thisPage", thisPage);
		session.setAttribute("nextPage", 10);
		session.setAttribute("returnPage", 0);
//        session.setAttribute("seProductList", productList);
        return mav;
	}
	
	@RequestMapping("/search/last")
	public ModelAndView showLastPage() {
		SearchProductFormModel model = (SearchProductFormModel) session.getAttribute("prodSearchCondition");
		Map<String, OnProductEntity> productMap = service.findPage(model, lastPageNum * 10 - 10);
		ModelAndView mav = new ModelAndView("search/searchProduct", "productMap", productMap);
		mav.addObject("lastPageNum", lastPageNum);
		thisPage = lastPageNum;
		mav.addObject("thisPage", thisPage);
		session.setAttribute("nextPage", lastPageNum * 10);
		session.setAttribute("returnPage", lastPageNum * 10 - 20);
//        session.setAttribute("seProductList", productList);
        return mav;
	}
	
	@RequestMapping(path="/search/registerProductResult")
	public ModelAndView showRegisterProductResult(@RequestParam(name = "buyItem", required=false) List<String> itemCodes, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("search/searchProduct");
		SearchProductFormModel model = (SearchProductFormModel) session.getAttribute("prodSearchCondition");
		Map<String, OnProductEntity> productMap = service.findPage(model, thisPage * 10 -10);
		mav.addObject("thisPage", thisPage);
		mav.addObject("lastPageNum", lastPageNum);
//		List<OnProductEntity> seProductList = (List<OnProductEntity>) session.getAttribute("seProductList");
		Map<String, String> codeMap = new LinkedHashMap<>();
		boolean fromDetail = Boolean.valueOf(request.getParameter("detail"));
		
		if(itemCodes != null) {
			for(String code : itemCodes) {
				String buyCnt = request.getParameter(code);
				boolean error = vali.inCartVali(buyCnt, mav);
				if(!error && !fromDetail) {
					productMap = service.findPage(model, thisPage * 10 -10);
					mav.addObject("productMap", productMap);
					return mav;
				} else if(!error && fromDetail){
					mav.setViewName("search/productDetail");
					return mav;
				} else {
					codeMap.put(code, buyCnt);
				}
				
			}
		} else {
			mav.addObject("checkboxIsBlank", "商品を選択してください。");
			
			mav.addObject("productMap", productMap);
			mav.addObject("thisPage", thisPage);
			mav.addObject("lastPageNum", lastPageNum);
			
			return mav;
		}
		
		List<String> stockErrorList = new ArrayList<>();
		
		Map<String, OnProductEntity> inCartMap = new LinkedHashMap<>();
		if(session.getAttribute("inCartMap") != null) {
			inCartMap = (Map<String, OnProductEntity>) session.getAttribute("inCartMap");
		}
		
		
		for(String key : codeMap.keySet()) {
			int buyCnt = Integer.parseInt(codeMap.get(key));
			int inCartBuyCnt = 0;
			int stock = 0;
			OnProductEntity thisProduct = productMap.get(key);
			
			stock = Integer.parseInt(thisProduct.getStockCount());
			
			
			if(inCartMap.get(key) != null && inCartMap.get(key).getBuyCnt() != null) {
				inCartBuyCnt = Integer.parseInt(inCartMap.get(key).getBuyCnt());
			}
			
			if(buyCnt + inCartBuyCnt > stock) {
				stockErrorList.add(productMap.get(key).getProductName() + "の在庫が足りません。購入数を変更してください。");
			} else {
				if(inCartMap.isEmpty() || inCartMap.get(key) == null) {
					thisProduct.setBuyCnt(String.valueOf(buyCnt + inCartBuyCnt));
					inCartMap.put(key, thisProduct);
				} else {
					inCartMap.get(key).setBuyCnt(String.valueOf(buyCnt + inCartBuyCnt));
				}
			}	
		}
		
		if(stockErrorList.isEmpty()) {
			//商品登録結果に表示する用
			mav.setViewName("search/registerProductResult");
			session.setAttribute("inCartMap", inCartMap);
			return mav;
		} else if(fromDetail){
			mav.setViewName("search/productDetail");
			mav.addObject("stockErrorList", stockErrorList);
			return mav;
		} else {
			mav.addObject("thisPage", thisPage);
			mav.addObject("lastPageNum", lastPageNum);
			mav.addObject("productMap", productMap);
//			mav.addObject("productList", seProductList);
			mav.addObject("stockErrorList", stockErrorList);
			return mav;
		}
	}
	
	@RequestMapping("/search/productDetail")
	public ModelAndView showProductDetail(@RequestParam String code) {
		ModelAndView mav = new ModelAndView("search/productDetail");
		
		OnProductEntity product = service.findById(code);
		
		session.setAttribute("thisProduct", product);
		return mav;
	}
	
	@RequestMapping("/search/userMenu")
	public ModelAndView backToUserMenu() {
		if(session.getAttribute("nextPage") != null && session.getAttribute("returnPage") != null) {
			session.removeAttribute("nextPage");
			session.removeAttribute("returnPage");
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect: ../userMenu.html");
		return mav;
	}
}
