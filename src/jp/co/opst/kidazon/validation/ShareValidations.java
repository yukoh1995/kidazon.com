package jp.co.opst.kidazon.validation;

import org.springframework.stereotype.Service;

@Service
public class ShareValidations {

	public boolean upSideDown(String lowerPrice, String higherPrice) {
		int low = Integer.parseInt(lowerPrice);
		int high = Integer.parseInt(higherPrice);
		if( low > high) {
			return false;
		}
		return true;
	}
	
	public boolean integerCheck(String word) {
		if(word.matches("^[0-9]+$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean plusCheck(String word) {
		if(word.matches("^[1-9]\\d*$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isNumber(String age) {
		try {
			Integer.parseInt(age);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}
	
	public boolean zipFormatCheck(String zip) {
		if(zip.matches("^[0-9]{3}-[0-9]{4}$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean phoneFormatCheck(String phone) {
		if(phone.matches("^[0-9]+-[0-9]+-*[0-9]*$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean twentyLiteralCheck(String name) {
		if(name.length() <= 20) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean emptyCheck(String word) {
		if(word.equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean stockCheck(String buyCnt, String stockCount) {
		int intBuyCnt = Integer.parseInt(buyCnt);
		int intStock = Integer.parseInt(stockCount);
		if(intStock < intBuyCnt) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean sameCheck(String StrA, String StrB) {
		if(StrA.equals(StrB)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean eightLiteralCheck(String word) {
		if(word.length() <= 8) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean passFormatCheck(String word) {
		if(word.matches("^[0-9a-zA-Z]+$")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean max50Check(String word) {
		if(word.length() >= 50) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean bet1and999Check(int num) {
		if(1 <= num && num <= 999) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean nullCheck(String word) {
		if(word == null) {
			return false;
		} else {
			return true;
		}
	}
}
