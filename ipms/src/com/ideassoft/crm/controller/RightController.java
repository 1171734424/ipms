package com.ideassoft.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Branch;
import com.ideassoft.bean.House;
import com.ideassoft.bean.RightDefine;
import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.bean.pageConfig.ModelConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.crm.service.RightService;
import com.ideassoft.util.CloneUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class RightController {

	@Autowired
	private PageBuilder pageBuilder;

	@Autowired
	private RightService rightService;

	@RequestMapping("/roleRightEdit.do")
	public ModelAndView roleRightEdit(String roleId, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/roleRightEdit");
		// 循环获得model里的各个子系统里模块总数
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Map<String, Integer> subSystemNum = new HashMap<String, Integer>();
		Map<String, ModelConfig> modelConfigs = pageBuilder.getModelConfigs();
		Iterator<Map.Entry<String, ModelConfig>> iter = modelConfigs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, ModelConfig> entry = (Map.Entry<String, ModelConfig>) iter.next();
			ModelConfig tempModelConfig = entry.getValue();
			String subSyStemValue = tempModelConfig.getSubSystem();
			if (!subSystemNum.containsKey(subSyStemValue)) {
				subSystemNum.put(subSyStemValue, 1);
			} else {
				subSystemNum.put(subSyStemValue, subSystemNum.get(subSyStemValue) + 1);
			}
		}
		;
		Entry<?,?> modelEntry;
		Entry<?, ?> pageEntry;
		//将modelConfigs拷贝一份过来用来操作
		Map<?, ?> deepclonemodelConfigs = (Map<?, ?>) CloneUtil.deepClone(modelConfigs);
		Branch branch = (Branch) rightService.findOneByProperties(Branch.class,
				"branchId", loginUser.getStaff().getBranchId());
		for( Iterator<?> it = deepclonemodelConfigs.entrySet().iterator(); it.hasNext(); ){
			modelEntry = (Entry<?, ?>) it.next();
			ModelConfig modelConfig = (ModelConfig) modelEntry.getValue();
			if("M901".equals(modelConfig.getModelId())){
				//System.out.println(modelConfig.getModelId());
				
			}
			for(Iterator<?> pit = ((ModelConfig)modelEntry.getValue()).getPageConfigs()
					.entrySet().iterator(); pit.hasNext(); ) {
				pageEntry = (Entry<?, ?>) pit.next();
				PageConfig pageConfig = (PageConfig) pageEntry.getValue();
				
				if(branch != null){
					/*List<?> house = rightService.findByProperties(House.class, "staffId", loginUser.getStaff().getStaffId());
					if(house.size() > 0){//判断民宿代码
						if(!"4".equals(pageRole))
							pit.remove();
					} */
					String pageRole = pageConfig.getRole();
					if(!StringUtils.isEmpty(pageRole)){
						if(!"*".equals(pageRole)){
							if(CommonConstants.BranchRank.TOP.equals(branch.getRank())){
								//总店
								
							}
							else{
								//分店
								if(branch.getBranchType().equals("1")){
									if(!"1".equals(pageRole))
										pit.remove();
								}
								else if(branch.getBranchType().equals("2")){
									if(!"2".equals(pageRole))
										pit.remove();
								}
								
								//if(!RegExUtil.match(pageRole, userRole)) {
								//	pit.remove();
								//}
								
							}
						}
					}
				}
			}
			
			if(((ModelConfig)modelEntry.getValue()).getPageConfigs().isEmpty()){
				it.remove();
			}
		}
		Map<String, Object> subSystemNames = ReflectUtil.getVariableMap(CommonConstants.SubSystemNames.class);
		//检测MODEL中是否还有
		if(deepclonemodelConfigs.get("M915") == null){
			subSystemNames.remove("EMS");
			subSystemNum.remove("EMS");
		}
		if(deepclonemodelConfigs.get("M911") == null){
			subSystemNames.remove("CRM");
			subSystemNum.remove("CRM");
		}
		
        //将业务权限进行分类
        HashMap<String, RightDefine> mapdefineright = RightConstants.rightDefineMap;
        Map<String, Object> maprightmodel = ReflectUtil.getVariableMap(RightConstants.RightModel.class);
        Map<String, Object> maprighttype = ReflectUtil.getVariableMap(RightConstants.RightType.class);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        String branchtype = branch.getBranchType();
        for (Map.Entry<String, Object> rightmodel : maprightmodel.entrySet()) {
        	Map<String, Object> mapmodel = new HashMap<String, Object>();
        	
        	if(CommonConstants.BranchRank.TOP.equals(branch.getRank())){
        		if(RightConstants.RightModel.HOTEL_CONTROL.equals(rightmodel.getValue()) || RightConstants.RightModel.APARTMENT_CONTROL.equals(rightmodel.getValue())){
        			//continue;
        		}
			}else{
				//当前是公寓的就过滤掉酒店,民宿
				if("2".equals(branchtype)){
					if(RightConstants.RightModel.HOUSE_CONTROL.equals(rightmodel.getValue()) || RightConstants.RightModel.HOTEL_CONTROL.equals(rightmodel.getValue())){
						continue;
					}
				//当前是酒店的就过滤掉公寓,民宿
				}else if("1".equals(branchtype)){
					if(RightConstants.RightModel.HOUSE_CONTROL.equals(rightmodel.getValue()) || RightConstants.RightModel.APARTMENT_CONTROL.equals(rightmodel.getValue())){
						continue;
					}
				}
			}
        	
        	mapmodel.put("model", rightmodel.getValue());
        	
        	List<Map<String, Object>> listrights = new ArrayList<Map<String, Object>>(); 
        	for (Map.Entry<String, RightDefine> rightdefine : mapdefineright.entrySet()) {
        		for (Map.Entry<String, Object> righttype : maprighttype.entrySet()) {
        			Map<String, Object> el = new HashMap<String, Object>();
                	if(rightmodel.getValue().equals(rightdefine.getValue().getRightModel()) && righttype.getValue().equals(rightdefine.getValue().getRightType())){
                		el.put("righttype", righttype.getKey());
                		el.put("name", righttype.getValue());
                		listrights.add(el);
                	}
                }
            }
        	mapmodel.put("listrights", listrights);
        	result.add(mapmodel);
        }
        JSONArray josnarray = JSONUtil.fromObject(result);
		
		mv.addObject("map", JSONUtil.fromObject(subSystemNum));
		mv.addObject("modelconfigs", deepclonemodelConfigs);
		mv.addObject("systemfunctions", JSONObject.fromObject(ReflectUtil.getVariableMap(CommonConstants.SystemFunctions.class)));
		mv.addObject("subSystemNames", JSONObject.fromObject(subSystemNames));
        //mv.addObject("rightconstants", JSONObject.fromObject(ReflectUtil.getVariableMap(RightConstants.RightType.class)));
		mv.addObject("rightconstants", josnarray);
		Role role = rightService.findRoleById(roleId);
		List<RoleRelation> relations = rightService.findRoleRelationByRoleId(roleId);
		mv.addObject("role", role);
		mv.addObject("loginData", loginUser);
		mv.addObject("relations", JSONArray.fromObject(relations));
		return mv;
	}

	@RequestMapping("/roleRightRelations.do")
	public void roleRightRelations(String role, String rights, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();

		org.json.JSONObject r = new org.json.JSONObject(role);
		// delete RoleRelation
		if (r.has("ROLEID")) {
			rightService.removeRoleRelation(r.get("ROLEID").toString());
		}
		// save Role
		Role ro = (Role) ReflectUtil.setBean(r, "Role", r.has("ROLEID") ? null : "ROLEID", r.has("ROLEID") ? null
				: "SEQ_NEW_ROLE", null,"cloud");
		ro.setRecordUser(staff.getStaffId());
		rightService.getHibernateTemplate().saveOrUpdate(ro);

		// save RoleRelation
		Map<String, String> params = new HashMap<String, String>();
		params.put("ROLEID", ro.getRoleId());
		params.put("RECORDUSER", staff.getStaffId());
		String keyConfig = "DATAID|SEQ_NEW_ROLERELATION";
		List<Object> rrs = ReflectUtil.setBeansFromJsonArray(rights, "RoleRelation", true, keyConfig, params, "cloud");
		rightService.saveOrUpdateAll(rrs);

		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}

	@RequestMapping("/roleRightDelete.do")
	public void roleRightDelete(String roleIds, HttpServletResponse response) throws Exception {
		String[] ids = roleIds.split(",");
		for (String id : ids) {
			rightService.delete(rightService.findById(Role.class, id));
			rightService.removeRoleRelation(id);
		}
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}

}