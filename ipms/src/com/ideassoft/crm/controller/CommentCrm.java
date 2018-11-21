package com.ideassoft.crm.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Comment;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.ProBrand;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.crm.service.CommentService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Controller
public class CommentCrm {
	
	@Autowired
	private CommentService commentService;
	
	/*
	 * 舆情品宣新增跳转
	 */
	@RequestMapping("addCommentCrm.do")
	public ModelAndView toAddComment(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("page/comment/commentCrm");
		List <?>list = commentService.findByProperties(ProBrand.class, "status", "2");
		mv.addObject("proBrandList", list);
		return mv;
	}
	
	/*
	 * 
	 * 舆情品宣编辑
	 */
	@RequestMapping("editCommentCrm.do")
	public ModelAndView toEditCommentCrm(HttpServletRequest req,HttpServletResponse resp){
		String commentId = req.getParameter("commentId");
		List<?> list = commentService.findBySQL("getProComById", new String[]{commentId},true);
		List <?>listProBrand = commentService.findByProperties(ProBrand.class, "status", "2");
		ModelAndView mv = new ModelAndView("page/comment/commentCrmProEdit");
		mv.addObject("listProBrand", listProBrand);
		JSONArray ja = JSONArray.fromObject(list);
		if(ja.size()>0){
			JSONObject jo = ja.getJSONObject(0);
			mv.addObject("comment", jo);
		}
		return mv;
	}
	
	
	
	
	/*
	 * 
	 * 批量删除品宣评论
	 */
	@RequestMapping("delProbrandComment.do")
	public void delProbrandComment(HttpServletRequest req,HttpServletResponse resp){
		String commentIds = req.getParameter("commitId");
		String commentId[] = commentIds.split(",");
		try {
			for(String id:commentId){
				Comment co = (Comment) commentService.findOneByProperties(Comment.class, "commentId", id);
				co.setStatus("0");
				commentService.update(co);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			JSONUtil.responseJSON(resp, new AjaxResult(CommonConstants.AJAXRESULT.FALSE, null));
		}
		JSONUtil.responseJSON(resp, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}
	/*
	 * 
	 * 更新品宣评论
	 */
	@RequestMapping("updateComment.do")
	public void updateProbrandComment(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)req;
		MultipartFile photo = mr.getFile("pictureId");
		String content = req.getParameter("content");
		String commentId = req.getParameter("commentId");
		FileOutputStream fos = null;
		InputStream is = null;
		String originalFilename = null;
		String fileName_timestamp = null;
		File tarFile = null;
		if(photo != null){
			 originalFilename=photo.getOriginalFilename();
			 if(!"".equals(originalFilename)){
				 fileName_timestamp = new Date().getTime() + originalFilename.substring(originalFilename.lastIndexOf("."));

			 }
			 
		}
		
		if((!"".equals(originalFilename)) && (originalFilename !=null)){
			String webPath = RequestUtil.getWebPath(req);
			try{
				File srcFolder = new File(webPath + File.separator+"upload" +File.separator+"commentPhoto");
				if (!srcFolder.exists()) {
					srcFolder.mkdirs();
				}
				tarFile = new File(srcFolder.getAbsolutePath() + File.separator+ fileName_timestamp);
				if(!tarFile.exists()){
					tarFile.createNewFile();
				}
				fos = new FileOutputStream(tarFile);
				is = photo.getInputStream();
				if(is.available() <= 0){
					JSONUtil.responseJSON(resp, new AjaxResult(2,"不可上传空的照片!"));
					return;
				}
				int ln = 0;
				byte[] buf = new byte[1024];
				while ((ln = is.read(buf)) != -1) {
					fos.write(buf, 0, ln);
				}
				fos.flush();
			}catch(IOException e){
				e.printStackTrace();
				 JSONUtil.responseJSON(resp, new AjaxResult(2, "文件传输失败!"));
			}finally{
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
			}
		}
		
		Comment comment = (Comment)commentService.findOneByProperties(Comment.class, "commentId", commentId);
		if(photo != null){
			comment.setPicture(fileName_timestamp);
		}
		comment.setCommentContent(content);
		comment.setRecordTime(new Date());
		commentService.update(comment);
		JSONUtil.responseJSON(resp, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}
	
	/*
	 * 
	 * 新增品宣评论
	 */
	@RequestMapping("addProbrandComment.do")
	public void addProbrandComment(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		LoginUser loginuser = (LoginUser) req.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)req;
		MultipartFile photo = mr.getFile("pictureId");
		String content = req.getParameter("content");
		String proBrandId = req.getParameter("proBrandId");
		FileOutputStream fos = null;
		InputStream is = null;
		String originalFilename = null;
		String fileName_timestamp = null;
		File tarFile = null;
		if(photo != null){
			 originalFilename=photo.getOriginalFilename();
			 if(!"".equals(originalFilename)){
				 fileName_timestamp = new Date().getTime() + originalFilename.substring(originalFilename.lastIndexOf("."));

			 }
			 
		}
		
		if((!"".equals(originalFilename)) && (originalFilename !=null)){
			String webPath = RequestUtil.getWebPath(req);
			try{
				File srcFolder = new File(webPath + File.separator+"upload" +File.separator+"commentPhoto");
				if (!srcFolder.exists()) {
					srcFolder.mkdirs();
				}
				tarFile = new File(srcFolder.getAbsolutePath() + File.separator+ fileName_timestamp);
				if(!tarFile.exists()){
					tarFile.createNewFile();
				}
				fos = new FileOutputStream(tarFile);
				is = photo.getInputStream();
				if(is.available() <= 0){
					JSONUtil.responseJSON(resp, new AjaxResult(2,"不可上传空的照片!"));
					return;
				}
				int ln = 0;
				byte[] buf = new byte[1024];
				while ((ln = is.read(buf)) != -1) {
					fos.write(buf, 0, ln);
				}
				fos.flush();
			}catch(IOException e){
				e.printStackTrace();
				 JSONUtil.responseJSON(resp, new AjaxResult(2, "文件传输失败!"));
			}finally{
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
			}
		}
		Comment comment = new Comment();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		comment.setCommentContent(content);
		comment.setCommentId(sdf.format(new Date())+commentService.getSequence("SEQ_COMMENT_ID"));
		comment.setPicture(fileName_timestamp);
		comment.setContentId(proBrandId);
		comment.setRecordTime(new Date());
		comment.setStatus("1");
		comment.setRecordUser(loginuser.getStaff().getStaffName());
		commentService.save(comment);
		JSONUtil.responseJSON(resp, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}
	
	

	private double StringToDouble(String strParam, double dbParame) {
		if (!StringUtils.isEmpty(strParam)) {
			dbParame = Double.parseDouble(strParam);
		}
		return dbParame;
	}
	
	
	
	
	@RequestMapping("/addReply.do")
	public void addReply(HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String commentId = request.getParameter("commentId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String reply = request.getParameter("reply");
		Comment comment = new Comment();
		comment.setCommentId(sdf.format(new Date())+commentService.getSequence("SEQ_COMMENT_ID"));
		comment.setRecordTime(new Date());
		comment.setStatus("1");
		comment.setRecordUser(loginuser.getStaff().getStaffName());
		comment.setServiceScore(0);
		comment.setServiceComment("1");
		comment.setFacilityScore(0);
		comment.setFacilityComment("1");
		comment.setSecurityScore(0);
		comment.setSecurityComment("1");
		comment.setCommentContent(reply);
		comment.setRelativeComment(commentId);
		commentService.save(comment);
		
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, "1"));
		
	}
	
}
