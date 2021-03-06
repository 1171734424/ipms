package com.ideassoft.basic.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import com.ideassoft.basic.service.ProbrandBasicService;
import com.ideassoft.bean.City;
import com.ideassoft.bean.Comment;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.House;
import com.ideassoft.bean.ProBrand;
import com.ideassoft.bean.ProBrandType;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.notifier.emay.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;



@Transactional
@Controller
public class ProbrandBasicController {
	@Autowired
	private ProbrandBasicService probrandBasicService;
	
	/**
	 * 用于跳转编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return 返回品宣编辑页面
	 */
	@RequestMapping("/editProbrand.do")
	public ModelAndView editProbrand(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		// 定义JavaBean对象
		ProBrand proBrand = null;
		ProBrandType proBrandType = null;
		City city = null;
		Goods goods = null;
		House house = null;
		String[] pictureNames = null;
		int picNo = 0;
		// 获取品宣Id，通过品宣Id查询品宣所有数据
		String contentId = request.getParameter("contentId");
		if (!"".equals(contentId) && contentId != null) {
			proBrand = (ProBrand)probrandBasicService.findById(ProBrand.class, contentId);
		}
		// 获取品宣参数
		String types = proBrand.getTypes();
		String adminiCode = proBrand.getAdminiCode();
		String goodsId = proBrand.getGoodsId();
		String houseId = proBrand.getHouseId();
		// 如果品宣类别不为空 则根据品宣ID查询类别名称
		if (!"".equals(types) && types != null) {
			proBrandType = (ProBrandType)probrandBasicService.findById(ProBrandType.class, types);
		}
		// 如果城市/风景故事不为空 则根据其ID查询城市数据
		if (!"".equals(adminiCode) && adminiCode != null) {
			city = (City)probrandBasicService.findById(City.class, adminiCode);
		}
		// 如果商品故事不为空 则根据其ID查询商品数据
		if (!"".equals(goodsId) && goodsId != null) {
			goods = (Goods)probrandBasicService.findById(Goods.class, goodsId);
		}
		// 如果民宿故事不为空 则根据其ID查询民宿数据
		if (!"".equals(houseId) && houseId != null) {
			house = (House)probrandBasicService.findById(House.class, houseId);
		}
		// 查询所有品宣信息
		List<?> typeInfoList = probrandBasicService.findBySQL("queryProBrandType", true);
		// 获取品宣名称后截取想要的字段
		String TypeName = proBrandType.getTypeName();
		String newBrandType = TypeName.substring(0,TypeName.indexOf("故事"));
		// 当图片不为空时将图片名称截出来
		String pictureId = proBrand.getPictureId();
		if (pictureId != null && !"".equals(pictureId)) {
			pictureNames = proBrand.getPictureId().split(",");
			picNo = pictureNames.length;
		}
		
		// 返回数据
		mv.addObject("city", city);
		mv.addObject("goods", goods);
		mv.addObject("house", house);
		mv.addObject("proBrand", proBrand);
		mv.addObject("picNo", picNo);
		mv.addObject("pictureNames", pictureNames);
		mv.addObject("typeInfoList", typeInfoList);
		mv.addObject("newBrandType", newBrandType);
		mv.setViewName("/page/basic/probrand/editProbrand");
		return mv;
	}
	
	
	
	/**
	 * 用于跳转页面
	 * 
	 * @param request
	 * @param response
	 * @return 返回品宣页面
	 */
	@RequestMapping("/addProbrand.do")
	public ModelAndView addProbrand(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		List<?> typeInfoList = probrandBasicService.findBySQL("queryProBrandType", true);
		mv.addObject("typeInfoList", typeInfoList);
		mv.setViewName("/page/basic/probrand/addProbrand");
		return mv;
	}
	
	
	/**
	 * 用于品宣添加信息功能
	 * 
	 * @param requeset
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveProbrandInfo.do")
	@ResponseBody
	public void saveProbrandInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 查询登陆人信息
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginuser.getStaff().getStaffId();
		// 创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求(即检查form中是否有enctype="multipart/form-data")
        if(multipartResolver.isMultipart(request)){
        	// 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String types = multiRequest.getParameter("types");
            String brandtypeId = multiRequest.getParameter("brandtype_CUSTOM_VALUE");
            String subTypes = types.substring(0, types.indexOf(","));
            StringBuilder sb = WriteData(request, multiRequest);
            List<ProBrand> dataList = new ArrayList<ProBrand>();
            if ("城市故事".equals(subTypes) || "景点故事".equals(subTypes)) {
            	dataList = probrandBasicService.findByProperties(ProBrand.class, "adminiCode", brandtypeId,"status","2");
			} else if ("商品故事".equals(subTypes)) {
				dataList = probrandBasicService.findByProperties(ProBrand.class, "goodsId", brandtypeId,"status","2");
			} else if ("民宿故事".equals(subTypes)) {
				dataList = probrandBasicService.findByProperties(ProBrand.class, "houseId", brandtypeId,"status","2");
			}
            
            if (dataList.size() > 0) {
            	JSONUtil.responseJSON(response, new AjaxResult(2,"您选择的具体故事已存在！"));
			} else {
				 // 将数据存入probrand表中
	            ProBrand probrand = new ProBrand();
	            // 当图片名称不为空的时候将最后一个逗号删除，并将图片名称存入数据库中
		        if (sb.length() > 2) {
		        	sb.delete(sb.length()-1, sb.length());
		        	probrand.setPictureId(sb.toString());
				}
		        // 根据故事类型存入不同的字段中
		        if ("城市故事".equals(subTypes) || "景点故事".equals(subTypes)) {
		        	probrand.setAdminiCode(brandtypeId);
				} else if ("商品故事".equals(subTypes)) {
					probrand.setGoodsId(brandtypeId);
				} else if ("民宿故事".equals(subTypes)) {
					probrand.setHouseId(brandtypeId);
				}
				probrand.setContentId(DateUtil.toString(new Date(), "yyMMdd") + probrandBasicService.getSequence("SEQ_PROBRAND_ID"));
				probrand.setTitle(multiRequest.getParameter("title"));
				probrand.setContent(multiRequest.getParameter("formatCode"));
				probrand.setSubTitle(multiRequest.getParameter("sub_title"));
				probrand.setContentDesc(multiRequest.getParameter("third_title"));
				probrand.setTypes(types.substring(types.indexOf(",")+1, types.length()));
				probrand.setCommitType(CommonConstants.CommitType.SYSTEMCOMMIT);
				probrand.setStatus(CommonConstants.ProbrandStatus.ALREADYAUDITED);
				probrand.setAuthor(multiRequest.getParameter("author"));
				probrand.setRecordTime(new Date());
				probrand.setRecordUser(recordUser);
				probrandBasicService.save(probrand);
				JSONUtil.responseJSON(response, new AjaxResult(1,"添加成功！"));
			}
        }
	}
	
	
	
	/**
	 * 弹出选择故事类别页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectBrandTypes.do")
	public ModelAndView selectBrandTypes(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		// 获取types类别名称
		String types = request.getParameter("types");
		mv.setViewName("page/dialog/customDialog");
		mv = ForwardPage(mv, "民宿故事",types,"houseId,housename","House","status<>'0'");
		mv = ForwardPage(mv, "城市故事",types,"adminiCode,adminiName","City","RANK='1'");
		mv = ForwardPage(mv, "商品故事",types,"goodsId,goodsName","Goods","status='1'");
		mv = ForwardPage(mv, "景点故事",types,"adminiCode,adminiName","City","RANK='6'");
		return mv;
	}
	
	
	/**
	 * 用于品宣添加信息功能
	 * 
	 * @param requeset
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateProbrandInfo.do")
	@ResponseBody
	public void updateProbrandInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 查询登陆人信息
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginuser.getStaff().getStaffId();
		// 创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求(即检查for    m中是否有enctype="multipart/form-data")
        if(multipartResolver.isMultipart(request)){
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String types = multiRequest.getParameter("types");
            String filePic = multiRequest.getParameter("pictureArray");
            String brandtypeId = multiRequest.getParameter("brandtype_CUSTOM_VALUE");
            String subTypes = types.substring(0, types.indexOf(","));
            StringBuilder sb = WriteData(request, multiRequest);
            
            // 获取品宣Id，通过品宣Id查询品宣所有数据
    		String contentId = request.getParameter("contentId");
    		ProBrand probrand = null;
    		// 如果contentId不为空 向数据库查询Probrand对象数据
    		if (!"".equals(contentId) && contentId != null) {
    			probrand = (ProBrand)probrandBasicService.findById(ProBrand.class, contentId);
    		}
    		// 判断filePic是否有图片名称
    		if (filePic.length() > 1) {
    			// 如果有就将P及逗号截取出去
            	filePic = filePic.substring(1, filePic.length()-1);
            	// 截取后的数据存入到StringBuilder中
            	sb.append(filePic);
            	// 完成以上操作后将图片名称存入到图片名称字段中，以便更新原有数据
            	probrand.setPictureId(sb.toString());
            // 判断StringBuilder是否有值，如果有代表有图片名称，将图片名称存入至probrand对象中
			} else if (sb.length() > 2) {
				// 如果没有走上面的if 代表StringBuilder中最后存在逗号，需要去掉逗号
    			sb.delete(sb.length()-1, sb.length());
    			// 完成以上操作后将图片名称存入到图片名称字段中，以便更新原有数据
	        	probrand.setPictureId(sb.toString());
    		}
    		
	        // 根据故事类型存入不同的字段中
	        if ("城市故事".equals(subTypes) || "景点故事".equals(subTypes)) {
	        	probrand.setAdminiCode(brandtypeId);
			} else if ("商品故事".equals(subTypes)) {
				probrand.setGoodsId(brandtypeId);
			} else if ("民宿故事".equals(subTypes)) {
				probrand.setHouseId(brandtypeId);
			}
	        // 更新其他字段数据
			probrand.setTitle(multiRequest.getParameter("title"));
			probrand.setContent(multiRequest.getParameter("formatCode"));
			probrand.setSubTitle(multiRequest.getParameter("sub_title"));
			probrand.setContentDesc(multiRequest.getParameter("third_title"));
			probrand.setTypes(types.substring(types.indexOf(",")+1, types.length()));
			probrand.setCommitType(CommonConstants.CommitType.SYSTEMCOMMIT);
			probrand.setStatus(CommonConstants.ProbrandStatus.ALREADYAUDITED);
			probrand.setAuthor(multiRequest.getParameter("author"));
			probrand.setRecordTime(new Date());
			probrand.setRecordUser(recordUser);
			probrandBasicService.update(probrand);
			JSONUtil.responseJSON(response, new AjaxResult(1,"添加成功！"));
        }
	}
	
	
	/**
	 * 预览图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showProbrandImage.do")
	public ModelAndView showProbrandImage(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/page/basic/probrand/showImage");
		// 获取项目部署位置
		String pictureName = request.getContextPath() + "/upload/probrandPic/" +request.getParameter("pictureId");
		mv.addObject("pictureName", pictureName);
		return mv;
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
	
	
	/*
	 * 
	 * 批量删除订单评论
	 */
	@RequestMapping("delOrderComment.do")
	public void delOrderComment(HttpServletRequest req,HttpServletResponse resp){
		String commentIds = req.getParameter("commitId");
		String commentId[] = commentIds.split(",");
		try {
			for(String id:commentId){
				Comment co = (Comment) probrandBasicService.findOneByProperties(Comment.class, "commentId", id);
				co.setStatus("0");
				probrandBasicService.update(co);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			JSONUtil.responseJSON(resp, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, null));
		}
		JSONUtil.responseJSON(resp, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}
	
}
