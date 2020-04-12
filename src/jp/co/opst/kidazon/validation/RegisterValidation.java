package jp.co.opst.kidazon.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class RegisterValidation {
	
	@Autowired
	ShareValidations valis;
	
	public boolean nameValidation(String name, ModelAndView mav) {
		List<String> nameValiError = new ArrayList<>();
		
		if(!valis.emptyCheck(name)) {
			nameValiError.add("氏名は必ず入力してください。");
		}
		
		if(!valis.twentyLiteralCheck(name)) {
			nameValiError.add("氏名は20文字以下で入力してください。");
		}
		
		if(nameValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("nameValiError", nameValiError);
			return false;
		}
	}
	
	public boolean passValidation(String pass, String passConf, ModelAndView mav) {
		List<String> passError = new ArrayList<>();
		List<String> confError = new ArrayList<>();
		
		if(!valis.emptyCheck(pass)) {
			passError.add("パスワードは必ず入力してください。");
		}
		
		if(!valis.emptyCheck(passConf)) {
			confError.add("パスワード(確認用)は必ず入力してください。");
		}
		
		if(!valis.sameCheck(pass, passConf)) {
			passError.add("パスワードとパスワード(確認用)は同じ値を入力してください。");
		}
		
		if(!valis.eightLiteralCheck(pass)) {
			passError.add("パスワードは8桁以下で入力してください。");
		}
		
		if(!valis.eightLiteralCheck(passConf)) {
			confError.add("パスワード(確認用)は8桁以下で入力してください。");
		}
		
		if(!valis.passFormatCheck(pass)) {
			passError.add("パスワードは半角英数字で入力してください。");
		}
		
		if(!valis.passFormatCheck(passConf)) {
			confError.add("パスワード(確認用)は半角英数字で入力してください。");
		}
		
		
		if(passError.isEmpty() && confError.isEmpty()) {
			return true;
		} else {
			mav.addObject("passValiError", passError);
			mav.addObject("passConfValiError", passError);
			return false;
		}
	}
	
	public boolean ageValidation(String age, ModelAndView mav) {
		List<String> ageValiError = new ArrayList<>();
		
		if(!valis.emptyCheck(age)) {
			ageValiError.add("年齢は必ず入力してください。");
		}
		
		if(!valis.integerCheck(age)) {
			ageValiError.add("年齢は半角数字で入力してください。");
		}
		
		if(!valis.plusCheck(age)) {
			ageValiError.add("年齢は正の数で入力してください。");
		}
		
		if(ageValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("ageValiError", ageValiError);
			return false;
		}
	}
	
	public boolean sexValidation(String sex, ModelAndView mav) {
		List<String> sexValiError = new ArrayList<>();
		
		if(!valis.nullCheck(sex)) {
			sexValiError.add("性別は必ず選択してください。");
		} else if(!valis.emptyCheck(sex)) {
			sexValiError.add("性別は必ず選択してください。");
		}
		
		if(sexValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("sexValiError", sexValiError);
			return false;
		}
	}
	
	public boolean zipValidation(String zip, ModelAndView mav) {
		List<String> zipValiError = new ArrayList<>();
		

		if(!valis.emptyCheck(zip)) {
			zipValiError.add("郵便番号は必ず入力してください。");
		}
		
		if(!valis.zipFormatCheck(zip)) {
			zipValiError.add("郵便番号はXXX-XXXXの形式で入力してください。");
		}
		
		if(zipValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("zipValiError", zipValiError);
			return false;
		}
	}
	
	public boolean addrValidation(String addr, ModelAndView mav) {
		List<String> addrValiError = new ArrayList<>();
		
		if(!valis.emptyCheck(addr)) {
			addrValiError.add("住所は必ず入力してください。");
		}
		
		if(!valis.max50Check(addr)) {
			addrValiError.add("住所は50文字以内で入力してください。");
		}
		
		if(addrValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("addrValiError", addrValiError);
			return false;
		}
	}
	
	public boolean phoneValidation(String phone, ModelAndView mav) {
		List<String> phoneValiError = new ArrayList<>();
		
		if(!valis.emptyCheck(phone)) {
			phoneValiError.add("電話番号は必ず入力してください。");
		}
		
		if(!valis.phoneFormatCheck(phone)) {
			phoneValiError.add("電話番号は半角数字とハイフンで入力してください。");
		}
		
		if(!valis.twentyLiteralCheck(phone)) {
			phoneValiError.add("電話番号は20文字以内で入力してください。");
		}
		
		if(phoneValiError.isEmpty()) {
			return true;
		} else {
			mav.addObject("phoneValiError", phoneValiError);
			return false;
		}
	}
}
