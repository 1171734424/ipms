package com.ideassoft.crm.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.City;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.House;
import com.ideassoft.bean.ProBrand;
import com.ideassoft.bean.ProBrandType;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.notifier.emay.util.DateUtil;
import com.ideassoft.crm.service.ProbrandService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Controller
@Transactional
public class ProbrandController {

	@Autowired
	private ProbrandService probrandService;
	
	
	
	
	

	

	

	
	/**
	 * 批量删除品宣功能
	 * @param request
	 * @param response
	 * @param contentId
	 */
	@RequestMapping("/delProbrand.do")
	public void delProbrand(HttpServletRequest request, HttpServletResponse response, String contentId) {
		// 获取操作人信息
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		// 验证回传数据是否为空或为null
		if (!"".equals(contentId) && contentId != null) {
			String[] contentIds = contentId.split(",");
			for (int i = 0; i < contentIds.length; i++) {
				if (contentIds[i] != null) {
					ProBrand proBrand = ((ProBrand) (probrandService.findOneByProperties(ProBrand.class,
							"contentId", contentIds[i])));
					proBrand.setStatus("0");
					proBrand.setRecordUser(recordUser);
					proBrand.setRecordTime(new Date());
					probrandService.update(proBrand);
				}
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		} else {
			JSONUtil.responseJSON(response, new AjaxResult(-1, null));
		}
	}

	
	

	/**
	 * 将上传图片写入至tomcat服务器中upload文件夹中
	 * 
	 * @param request
	 * @param multiRequest
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private StringBuilder WriteData(HttpServletRequest request,
			MultipartHttpServletRequest multiRequest) throws IOException,
			FileNotFoundException {
		// 定义StringBuilder用于存放图片名称
		StringBuilder sb = new StringBuilder();
		// 定义输入输出流
		FileOutputStream fos = null;
		InputStream is = null;
		// 取得request中的所有input的name 
		Iterator<String> iter = multiRequest.getFileNames();
		while(iter.hasNext()){
		    // 取得一个input里面所有的上传文件  
		    List<MultipartFile> list = multiRequest.getFiles(iter.next());
		    for(MultipartFile file : list){
		        if(file != null){
		            // 取得当前上传文件的文件名称 
		            String myFileName = file.getOriginalFilename();
		            // 如果名称不为“”,说明该文件存在，否则说明该文件不存在  
		            if(myFileName.trim() != null && !"".equals(myFileName.trim())){
		            	// 重命名上传后的文件名  
		            	String prictreRename = new Date().getTime() + myFileName.substring(myFileName.lastIndexOf("."));
		            	sb.append(prictreRename).append(",");
		            	// 获取项目部署位置
						String webPath = RequestUtil.getWebPath(request);
						// 在项目部署位置中打开upload子文件路径，如果没有的话就创建upload文件夹
						File srcFile = new File(webPath + File.separator + "upload" + File.separator + "probrandPic");
						if (!srcFile.exists()) {
							srcFile.mkdirs();
						}
						// 在项目部署位置中打开upload子文件中的新建图片的名称，如果图片名称不存在就创建新的文件并且命名为图片名称
						File tarFile = new File(srcFile.getAbsoluteFile() + File.separator + prictreRename);
						if (!tarFile.exists()) {
							tarFile.createNewFile();
						}
						
		                try {
		                	// 建立写入流并把刚刚创建的图片名称文件放进去
		                	fos = new FileOutputStream(tarFile);
		    				// 将图片读出准备写入至上述文件中
		    				is = file.getInputStream();
		    				
		    				// 创建缓冲流
		    				int ln = 0;
		    				byte[] buf = new byte[1024];
		    				// 将读取的图片写入上述创建的改名的文件中去
		    				while ((ln = is.read(buf)) != -1) {
		    					fos.write(buf, 0, ln);
		    				}
		    				fos.flush();
		    				fos.close();
		    				is.close();
		                	
		                } catch (IllegalStateException e) {
		                	fos.flush();
		    				fos.close();
		    				is.close();
		                    e.printStackTrace();
		                }
		            }
		        }
		    }

		}
		return sb;
	}
	
	/**
	 * 用于多个不同页面调用的公共方法
	 * 
	 * @param mv 页面
	 * @param nowTypes 比对的类别
	 * @param types 传入的类别
	 * @param argsIdName 需要查询的ID和名称
	 * @param argsBean Bean名称
	 * @param argsStatus 状态
	 */
	private ModelAndView ForwardPage(ModelAndView mv, String nowTypes,String types,String argsIdName,String argsBean,String argsStatus) {
		if (nowTypes.equals(types)) {
			mv.addObject("dialogColumns", argsIdName);
			mv.addObject("dialogTarget", argsBean);
			mv.addObject("dialogConditions", argsStatus);
			mv.addObject("dialogQuickAdd", null);
			mv.addObject("selectType", "dialog-radio");
			mv.addObject("colName", "brandtype");
		}
		return mv;
	}
}
