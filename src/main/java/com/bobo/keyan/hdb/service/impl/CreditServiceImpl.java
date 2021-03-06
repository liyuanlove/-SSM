package com.bobo.keyan.hdb.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bobo.keyan.hdb.mapper.CreditMapper;
import com.bobo.keyan.hdb.model.Credit;
import com.bobo.keyan.hdb.model.CreditExample;
import com.bobo.keyan.hdb.model.CreditExample.Criteria;
import com.bobo.keyan.hdb.service.api.CreditService;

@Service
public class CreditServiceImpl implements CreditService{
	
	@Autowired
	CreditMapper creditMapper;
	
	@Override
	public Map<String, Object> insertCreditSelective(Map<String, Object> map) throws Exception {
		Map<String , Object> resultmap=new ConcurrentHashMap<>();
		
		Integer userid=Integer.parseInt((String) map.get("userid"));
		String c_province=(String) map.get("c_province");
		String c_bankadress=(String) map.get("c_bankadress");
		String c_bankname=(String) map.get("c_bankname");
		String c_cardnum=(String) map.get("c_cardnum");
		String c_paypassword=(String) map.get("c_paypassword");
		String c_phone=(String) map.get("c_phone");
		String c_urealname=(String) map.get("c_urealname");
		
		Credit credit=new Credit();
		Credit credit1=new Credit();
		credit.setcBankadress(c_bankadress);
		credit.setcBankname(c_bankname);
		credit.setcCardnum(c_cardnum);
		credit.setcPaypassword(c_paypassword);
		credit.setcPhone(c_phone);
		credit.setcProvince(c_province);
		credit.setcUserid(userid);
		credit.setcUrealname(c_urealname);
		
		credit1.setcCardnum(c_cardnum);
		//判断该人该卡是否已经绑定
		int flag=0;
		try {
			CreditExample example=new CreditExample();
			Criteria criteria=example.createCriteria();
			criteria.andCCardnumEqualTo(c_cardnum);
			List<Credit> creditlist=creditMapper.selectByExample(example);
			if(creditlist.size()>0 && creditlist.get(0).getcUserid().equals(userid)) {
				flag=1;//已绑定
				resultmap.put("result", 0);//已绑定
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//未绑定即可直接插入数据
		if(0==flag) {
			try {
				creditMapper.insertSelective(credit);
				resultmap.put("result", 1);//插入成功
			} catch (Exception e) {
				// TODO: handle exception
				resultmap.put("result", 2);//插入失败
			}
		}
		return resultmap;
	}

	@Override
	public Map<String, Object> selectAllCount_svc(Map<String, Object> map) throws Exception {
		Map<String , Object> resultmap=new ConcurrentHashMap<>();
		Integer userid=Integer.parseInt((String) map.get("userid"));
		CreditExample example=new CreditExample();
		example.setOrderByClause("c_id desc");
		List<Credit> credits=creditMapper.selectByExample(example);
		resultmap.put("result", credits);
		return resultmap;
	}

	@Override
	public Map<String, Object> delectAcount_svc(Map<String, Object> map) throws Exception {
		Map<String , Object> resultmap=new ConcurrentHashMap<>();
		Integer cid=Integer.parseInt((String) map.get("cid"));
		try {
			creditMapper.deleteByPrimaryKey(cid);
			resultmap.put("result", 1);//解绑成功
		} catch (Exception e) {
			// TODO: handle exception
			resultmap.put("result", 0);//解绑失败
		}
		return resultmap;
	}
	
}
