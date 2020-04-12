package jp.co.opst.kidazon.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.opst.kidazon.dao.OnlineMemberDao;
import jp.co.opst.kidazon.entity.OnMemberEntity;
import jp.co.opst.kidazon.model.LoginFormModel;
import jp.co.opst.kidazon.model.MemInfoFormModel;
import jp.co.opst.kidazon.model.modifyMemInfoFormModel;

@Service
public class OnMemberService {

	@Autowired
	OnlineMemberDao onMemDao;
	
	@Autowired
	HttpSession session;
	
	@Transactional(rollbackForClassName = "Exception")
	public boolean register(MemInfoFormModel model) throws Exception {
		OnMemberEntity entity = new OnMemberEntity();
		entity.setPass(model.getPass());
		entity.setName(model.getName());
		entity.setAge(model.getAge());
		entity.setSex((model.getSex().toUpperCase()));
		entity.setZip(model.getZip());
		entity.setAddr(model.getAddr());
		entity.setTel(model.getPhone());
		
		int resultCnt = onMemDao.register(entity);
		if(resultCnt == 1) {
			session.setAttribute("yourNo", entity.getMemberNo());
			session.setAttribute("yourName", entity.getName());
			
			Date registerDate = onMemDao.findRegisterDate(entity.getMemberNo());
			entity.setRegisterDate(registerDate);
			session.setAttribute("userInfo", entity);
			return true;
		} else {
			throw new Exception();
		}
	}
	
	public boolean doLogin(LoginFormModel model) {
		OnMemberEntity entity = new OnMemberEntity();
		entity.setMemberNo(model.getMemNo());
		entity.setPass(model.getPass());
		
		List<OnMemberEntity> result = onMemDao.login(entity);
		for(OnMemberEntity val : result) {
			if(val.getMemberNo().equals(model.getMemNo()) && val.getPass().equals(model.getPass()) && val.getDeleteFlg().equals("0")) {
				session.setAttribute("yourNo", val.getMemberNo());
		        session.setAttribute("yourName", val.getName());
		        session.setAttribute("userInfo", val);
		        session.setAttribute("registerDate", val.getRegisterDate());
				return true;
			}
		}
		return false;
	}
	
	@Transactional(rollbackForClassName = "Exception")
	public boolean updateMemInfo(modifyMemInfoFormModel model) throws Exception {
		String yourNo = (String) session.getAttribute("yourNo");
		
		OnMemberEntity entity = new OnMemberEntity();
		entity.setMemberNo(yourNo);
		entity.setPass(model.getPass());
		entity.setName(model.getName());
		entity.setAge(model.getAge());
		entity.setSex((model.getSex().toUpperCase()));
		entity.setZip(model.getZip());
		entity.setAddr(model.getAddr());
		entity.setTel(model.getPhone());
		
		int result = onMemDao.update(entity);
		
		if (result != 0) {
			session.setAttribute("yourName", model.getName());
			session.setAttribute("userInfo", entity);
			
			Date registerDate = onMemDao.findRegisterDate(entity.getMemberNo());
			entity.setRegisterDate(registerDate);
			session.setAttribute("userInfo", entity);
            return true;
        } else {
            throw new Exception();
		}
	}
	
	@Transactional(rollbackForClassName = "Exception")
	public boolean deleteMemInfo(String yourNo) throws Exception {
		OnMemberEntity entity = new OnMemberEntity();
		entity.setMemberNo(yourNo);
		int result = onMemDao.delete(entity);
		
		if (result != 0) {
            return true;
        } else {
            throw new Exception();
		}
	}
}
