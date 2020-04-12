package jp.co.opst.kidazon.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginFormModel {
	
	@NotEmpty(message="会員NOは必ず入力してください。")
	@Pattern(regexp = "^[0-9]+$", message = "会員NOは半角数字で入力してください。")
	private String memNo;
	
	@NotEmpty(message="パスワードは必ず入力してください。")
	@Size(max=8, message="パスワードは8桁以下で入力してください。")
	@Pattern(regexp = "^[0-9a-zA-Z]+$", message = "パスワードは半角英数字で入力してください。")
	private String pass;
	
	
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
