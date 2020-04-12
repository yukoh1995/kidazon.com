package jp.co.opst.kidazon.validation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class SearchValidation {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ShareValidations valis;
	
	public boolean lowPriceValidation(String lowerPrice, ModelAndView mav) {
		List<String> lowPriceValiError = new ArrayList<>();
		
		
		if(!valis.integerCheck(lowerPrice)) {
			lowPriceValiError.add("金額下限は半角数字で入力してください。");
		}
	
		if(!valis.plusCheck(lowerPrice)) {
			lowPriceValiError.add("金額下限は正の数で入力してください。");
		}
		
		if(lowPriceValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("lowPriceValiError", lowPriceValiError);
			return false;
		}
		
		
		
	}
	
	public boolean highPriceValidation(String higherPrice, ModelAndView mav) {
		List<String> highPriceValiError = new ArrayList<>();
		
		if(!valis.integerCheck(higherPrice)) {
			highPriceValiError.add("金額上限は半角数字で入力してください。");
		}
		
		if(!valis.plusCheck(higherPrice)) {
			highPriceValiError.add("金額上限は正の数で入力してください。");
		}
		
		if(highPriceValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("highPriceValiError", highPriceValiError);
			return false;
		}
	}
	
	public boolean doublePriceVali(String lowerPrice, String higherPrice, ModelAndView mav) {
		List<String> doublePriceValiError = new ArrayList<>();
		
		if(valis.isNumber(lowerPrice) && valis.isNumber(higherPrice)) {
			if(!valis.upSideDown(lowerPrice, higherPrice)) {
				doublePriceValiError.add("上限は下限より大きい値を入力してください。");
			}
		}
		
		if(doublePriceValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("doublePriceValiError", doublePriceValiError);
			return false;
		}
	}
	
	public boolean regiProductVali(String buyCnt) {
		int errorCnt = 0;
		
		if(valis.isNumber(buyCnt)) {
			int intBuyCnt = Integer.parseInt(buyCnt);
			if(!valis.bet1and999Check(intBuyCnt)) {
				errorCnt++;
			}
		} else {
			errorCnt++;
		}
		
		if(!valis.emptyCheck(buyCnt)) {
			errorCnt++;
		}
		
		if(errorCnt == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean inCartVali(String buyCnt, ModelAndView mav) {
		List<String> inCartValiError = new ArrayList<>();
		
		if(valis.isNumber(buyCnt)) {
			int intBuyCnt = Integer.parseInt(buyCnt);
			if(!valis.bet1and999Check(intBuyCnt)) {
				inCartValiError.add("購入数は1~999の数値で入力してください。");
			} else if(!valis.emptyCheck(buyCnt)) {
				inCartValiError.add("購入数は1~999の数値で入力してください。");
			}
		} else {
			inCartValiError.add("購入数は1~999の数値で入力してください。");
		}
		
		if(inCartValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("inCartValiError", inCartValiError);
			return false;
		}
	}
}
