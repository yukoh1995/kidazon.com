package jp.co.opst.kidazon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.opst.kidazon.entity.OnMemberEntity;
import jp.co.opst.kidazon.model.modifyMemInfoFormModel;
import jp.co.opst.kidazon.service.OnMemberService;
import jp.co.opst.kidazon.validation.ModifyValidation;

@Controller
public class ModifyController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OnMemberService service;
	
	@Autowired
	ModifyValidation vali;
	
	@RequestMapping("/modify/memInfo")
	public ModelAndView showMemInfo() {
		ModelAndView mav = new ModelAndView();
		String isLogin = (String) session.getAttribute("yourName");
		if(isLogin != null) {
			OnMemberEntity userInfo = (OnMemberEntity) session.getAttribute("userInfo");
			session.setAttribute("pastUserInfo", userInfo);
			mav.addObject("userInfo", userInfo);
			mav.setViewName("modify/memInfo");
			return mav;
		} else {
			mav.setViewName("redirect: ../userLogin.html");
			session.setAttribute("fromWhere", "md");
			return mav;
		}
	}
	
	@RequestMapping("/modify/modifyMemInfo")
	public String showModifyMemInfo() {
		return "modify/modifyMemInfo";
	}
	
	@RequestMapping(path = "/modify/confModify", method= RequestMethod.POST)
	public ModelAndView showConfModify(modifyMemInfoFormModel model) {
		
		ModelAndView mav = new ModelAndView("modify/modifyMemInfo");
		mav.addObject("model", model);
        
		//バリデーション開始
		 List<Boolean> hasError = new ArrayList<>();
		OnMemberEntity pastUserInfo = (OnMemberEntity) session.getAttribute("pastUserInfo");
		
		if(!pastUserInfo.getName().equals(model.getName())) {
			hasError.add(vali.nameValidation(model.getName(), mav));
		}
		
        if(!model.getPass().equals("") || !model.getPassConf().equals("")) {
			hasError.add(vali.passValidation(model.getPass(), model.getPassConf(), mav));
			if(pastUserInfo.getPass().equals(model.getPass())) {
				mav.addObject("samePass", "変更前と同じパスワードには変更できません。");
				hasError.add(false);
			}
			
			mav.addObject("tryPass", model.getPass());
			mav.addObject("tryPassConf", model.getPassConf());
			
		} else {
			OnMemberEntity userInfo = (OnMemberEntity) session.getAttribute("userInfo");
			model.setPass(userInfo.getPass());
		}
        
        if(!pastUserInfo.getAge().equals(model.getAge())) {
        	hasError.add(vali.ageValidation(model.getAge(), mav));
        }
        
        if(!pastUserInfo.getSex().equals(model.getSex())) {
        	hasError.add(vali.sexValidation(model.getSex(), mav));
        }
        
        if(!pastUserInfo.getZip().equals(model.getZip())) {
        	hasError.add(vali.zipValidation(model.getZip(), mav));
        }
        
        if(!pastUserInfo.getAddr().equals(model.getAddr())) {
        	hasError.add(vali.addrValidation(model.getAddr(), mav));
        }
        
        if(!pastUserInfo.getTel().equals(model.getPhone())) {
        	hasError.add(vali.phoneValidation(model.getPhone(), mav));
        }
        
        if(hasError.contains(false)) {
        	mav.setViewName("modify/modifyMemInfo");
        	return mav; 
        }
        
		mav.addObject("modifyMemInfoFormModel", model);
		session.setAttribute("confmodifyMemInfoFormModel", model);
		mav.setViewName("modify/confModify");
		return mav;
	}
	
	@RequestMapping("/modify/modifyResult")
	public ModelAndView showcmodifyResult() {
		ModelAndView mav = new ModelAndView();
		modifyMemInfoFormModel model = (modifyMemInfoFormModel) session.getAttribute("confmodifyMemInfoFormModel");
		try {
			service.updateMemInfo(model);
			mav.setViewName("modify/modifyResult");
			return mav;
		} catch(Exception e) {
			e.printStackTrace();
			mav.setViewName("modify/confModify");
			System.out.print("修正に失敗しました。");
			return mav;
		}
	}
	
	@RequestMapping("/modify/resetMemInfo")
	public ModelAndView resetMemInfo() {
		ModelAndView mav = new ModelAndView("modify/modifyMemInfo");
		OnMemberEntity userInfo = (OnMemberEntity) session.getAttribute("userInfo");
		session.setAttribute("userInfo", userInfo);
		
		return mav;
	}
}
