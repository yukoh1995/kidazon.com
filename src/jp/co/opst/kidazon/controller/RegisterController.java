package jp.co.opst.kidazon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.opst.kidazon.model.MemInfoFormModel;
import jp.co.opst.kidazon.service.OnMemberService;
import jp.co.opst.kidazon.validation.RegisterValidation;

@Controller
public class RegisterController {
	
	@Autowired
	OnMemberService onMemService;
	
	@Autowired
	RegisterValidation vali;

	
	@RequestMapping("/register/inputMemInfo")
	public String showInputMemInfo() {
		return "register/inputMemInfo";
	}
	
	@RequestMapping(path="/register/confMemInfo", method= RequestMethod.POST)
	public ModelAndView showConfMemInfo(@Valid MemInfoFormModel model, BindingResult errors, HttpSession session) {
		
		ModelAndView mav = new ModelAndView("register/inputMemInfo");
		
        mav.addObject("model", model);
        List<Boolean> hasError = new ArrayList<>();
        
        hasError.add(vali.nameValidation(model.getName(), mav));
        hasError.add(vali.passValidation(model.getPass(), model.getPassConf(), mav));
        hasError.add(vali.ageValidation(model.getAge(), mav));
        hasError.add(vali.sexValidation(model.getSex(), mav));
        hasError.add(vali.zipValidation(model.getZip(), mav));
        hasError.add(vali.addrValidation(model.getAddr(), mav));
        hasError.add(vali.phoneValidation(model.getPhone(), mav));
        
        //バリデーション終了
        if(hasError.contains(false)) {
        	mav.addObject("memInfoForm", model);
        	return mav; 
        }
		
		session.setAttribute("memInfoForm", model);
		mav.setViewName("/register/confMemInfo");
		
		return mav;
	}
	
	@RequestMapping("/register/outputMemnum")
	public String showOutputMemnum(HttpSession session) {
		
		MemInfoFormModel model = (MemInfoFormModel) session.getAttribute("memInfoForm");
		try {
			onMemService.register(model);
			return "register/outputMemnum";
		} catch (Exception e) {
			e.printStackTrace();
			return "register/confMemInfo";
		}
	}
}
