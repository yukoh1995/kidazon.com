package jp.co.opst.kidazon.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.opst.kidazon.service.OnMemberService;

@Controller
public class destroyController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OnMemberService service;

	@RequestMapping("/destroy/confDestroy")
	public String showConfDestroy() {
		return "destroy/confDestroy";
	}
	
	@RequestMapping("/destroy/destroyResult")
	public ModelAndView showDestroyResult() {
		ModelAndView mav = new ModelAndView();
		String yourNo = (String) session.getAttribute("yourNo");
		
		try {
			service.deleteMemInfo(yourNo);
			mav.setViewName("destroy/destroyResult");
			return mav;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.print("削除に失敗しました。");
			mav.setViewName("destroy/confDestroy");
			return mav;
		}
	}
}
