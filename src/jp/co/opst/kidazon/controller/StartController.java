package jp.co.opst.kidazon.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.opst.kidazon.entity.OnCategoryEntity;
import jp.co.opst.kidazon.model.LoginFormModel;
import jp.co.opst.kidazon.service.OnCategoryService;
import jp.co.opst.kidazon.service.OnMemberService;

@Controller
public class StartController {
	
	@Autowired
	ServletContext application;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OnMemberService service;
	
	@Autowired
	OnCategoryService cateService;
	
	@RequestMapping("/userMenu")
	public String showUserMenu() {
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String today = sdf.format(cl.getTime());
		application.setAttribute("today", today);
		
		List<OnCategoryEntity> categoryList = cateService.findAll();
		application.setAttribute("categoryList", categoryList);
		
		return "userMenu";
	}
	
	@RequestMapping("/userLogin")
	public String showUserLogin() {
		
		return "userLogin";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		session.removeAttribute("yourNo");
		session.removeAttribute("yourName");
		session.removeAttribute("userInfo");
		session.removeAttribute("regiProductList");
		
		return "redirect: userMenu.html";
	}
	
	@RequestMapping(path="/doLogin", method= RequestMethod.POST)
	public ModelAndView doLogin(@Valid LoginFormModel model, BindingResult errors) {
		
		ModelAndView mav = new ModelAndView("userMenu");
		if (errors.hasErrors()) {
            mav.setViewName("userLogin");
            return mav;
        }
		
		boolean isLogin = service.doLogin(model);
		if(isLogin) {
			if(session.getAttribute("fromWhere") != null) {
				String flg = (String) session.getAttribute("fromWhere");
				if(flg.equals("md")) {
					mav.setViewName("redirect: modify/memInfo.html");
					session.removeAttribute("fromWhere");
					return mav;
				} else if(flg.equals("od")) {
					mav.setViewName("redirect: cart/confCart.html");
					mav.addObject("nothing", "nothing");
					session.removeAttribute("fromWhere");
				}
			}
			return mav;
		} else {
			mav.addObject("failLogin", "ログインできませんでした。");
			mav.setViewName("userLogin");
            return mav;
		}
	}
	
}
