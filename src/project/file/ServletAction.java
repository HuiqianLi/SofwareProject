package project.file;
/*
 * 增删改查看导印统功能的实现
 * 待完成：用MVC模式分开DB和Action操作
 */

import dao.YlxId;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import project.dao.Apply;
import project.dao.File;
import project.dao.FileDao;
import project.dao.Member;
import project.dao.MemberDao;

import utility.LogEvent;
import utility.MD5Util;
public class ServletAction extends HttpServlet {
	//这里是需要改的,module和sub
	public String module = "project";
	public String sub = "file";
	
	public String preFix = module + "_" + sub;
	public String resultPath = module + "/" + sub;
	public String resultPage = "result.jsp";
	public String resultUrl=resultPath+"/"+resultPage;
	public String redirectPath = module + "/" + sub;
	public String redirectPage = "project_list.jsp";
	public String redirectUrl=redirectPage;
	public String databaseName="ylxdb";
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public LogEvent ylxLog = new LogEvent();

	/*
	 * 处理顺序：先是service，后根据情况doGet或者doPost
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		processAction(request,response);
	}
	public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		try {
			ylxLog.setSession(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String action = request.getParameter("action");
		boolean actionOk = false;
		showDebug("processAction收到的action是："+action);
		if(session.getAttribute("user_role")==null){
			try {
				processError(request, response,3,"session超时，请重新登录系统！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			if (action == null){
				try {
					processError(request, response,1,"传递过来的action是null！");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				//常规增删改查功能
				if (action.equals("get_record")) {
					actionOk=true;
					try {
						getRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_record_by_id")) {
					actionOk=true;
					try {
						getRecordById(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("add_record")) {
					actionOk=true;
					try {
						addRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("delete_record")) {
					actionOk=true;
					try {
						deleteRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("modify_record")) {
					actionOk=true;
					try {
						modifyRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_member_record")) {
					actionOk=true;
					try {
						getMemberRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_member_record_by_id")) {
					actionOk=true;
					try {
						getMemberRecordById(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("add_member_record")) {
					actionOk=true;
					try {
						addMemberRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("delete_member_record")) {
					actionOk=true;
					try {
						deleteMemberRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("apply_join")) {
					actionOk=true;
					try {
						applyJoin(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("apply_check")) {
					actionOk=true;
					try {
						applyCheck(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_apply_record")) {
					actionOk=true;
					try {
						getApplyRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_project")) {
					actionOk=true;
					try {
						getRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_project_by_project_id")) {
					actionOk=true;
					try {
						getRecordByProjectId(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("project_file_join_apply")) {
					actionOk=true;
					try {
						processJoinApplyNotice(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("project_file_join_apply_result")) {
					actionOk=true;
					try {
						processJoinApplyNotice(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (!actionOk) {
					try {
						processError(request, response,2,"没有对应的action处理过程，请检查action是否正确！action="+action);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void processError(HttpServletRequest request, HttpServletResponse response,int errorNo,String errorMsg) throws JSONException, IOException{
		String action = request.getParameter("action");
		//errorNo=0->没有错误
		//errorNo=1->action是空值
		//errorNo=2->没有对应的处理该action的函数
		//errorNo=3->session超时
		showDebug("错误信息："+errorMsg+"，代码："+errorNo);
		JSONObject jsonObj=new JSONObject();
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			jsonObj.put("result_code",errorNo);
			jsonObj.put("result_msg",errorMsg);
			jsonObj.put("action",action);
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			errorMsg = java.net.URLEncoder.encode(errorMsg, "UTF-8");
			String url = resultPath+"/"+resultPage+"?action="+action+"&result_code="+errorNo+"&redirect_path=" + redirectPath + "&redirect_page=" + redirectPage + "&result_msg=" + errorMsg;
			showDebug(url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：进行一个本类测试，不用启动整个项目，测试所写的Java
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("");
	}
	public void showDebug(String msg){
		System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+module+"/"+sub+"/ServletAction]"+msg);
	}
	/*
	 * 功能：范例函数，待整理，针对json和form两种情况进行分别对待
	 */
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String content=request.getParameter("content");

		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		FileDao fileDao=new FileDao();
		JSONObject jsonObj=fileDao.doAction(action,dbName,id,content,creator,createTime);
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("doAction结果URL："+url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：查询记录
	 */
	public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String content=request.getParameter("content");
		String type= request.getParameter("type");
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			FileDao fileDao=new FileDao();
			jsonObj=fileDao.getRecord(action,dbName,type,userId);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：修改记录
	 */
	public void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String content=request.getParameter("content");
		String openLevel=request.getParameter("open_level");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(id!=null && dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			FileDao fileDao=new FileDao();
			jsonObj=fileDao.modifyRecord(action,dbName,id,content,openLevel,creator,createTime);
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 修改了 "+sub+" 记录","修改记录",module);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("modifyRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void getRecordById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String content=request.getParameter("content");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(id!=null && dbName!=null){
			String userId=(String)session.getAttribute("user_id");
			String userName=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			FileDao fileDao=new FileDao();
			jsonObj=fileDao.getRecordById(action,dbName,id,userId);
			jsonObj.put("user_name",userName);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecordById结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void getRecordByProjectId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String projectId=request.getParameter("project_id");
		String content=request.getParameter("content");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(projectId!=null && dbName!=null){
			String userId=(String)session.getAttribute("user_id");
			String userName=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			FileDao fileDao=new FileDao();
			jsonObj=fileDao.getRecordByProjectId(action,dbName,projectId,userId);
			jsonObj.put("user_name",userName);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecordByProjectId结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String creator=(String)session.getAttribute("user_name");
		String userId=(String)session.getAttribute("user_id");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());

		File file=new File();
		file.setProjectId(getProjectId());
		file.setUserId(userId);
		file.setProjectName(request.getParameter("project_name"));
		file.setProjectNick(request.getParameter("project_nick"));
		file.setProjectClass(request.getParameter("project_class"));
		file.setProjectSource(request.getParameter("project_source"));
		file.setProjectDescribe(request.getParameter("project_describe"));
		file.setProjectManagerName(request.getParameter("project_manager_name"));
		file.setProjectManagerId(userId);
		file.setApplyMoney(request.getParameter("cost"));
		file.setApprovalMoney(request.getParameter("approval_money"));
		file.setGroupMember(request.getParameter("group_member"));
		file.setStartTime(request.getParameter("start_time"));
		file.setEndTime(request.getParameter("end_time"));
		file.setSuperiorUnit(request.getParameter("superior_unit"));
		file.setSuperiorManager(request.getParameter("superior_manager"));
		file.setAttachmentName(request.getParameter("attachment_name"));
		file.setOpenLevel(request.getParameter("open_level"));
		file.setCheckTag(request.getParameter("check_tag"));
		file.setChecker(request.getParameter("checker"));
		file.setCheckTime(request.getParameter("check_time"));
		file.setCreator(creator);
		file.setCreateTime(createTime);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String avatar=(String)session.getAttribute("user_avatar");
			FileDao fileDao=new FileDao();
			jsonObj=fileDao.addRecord(action,dbName,file,avatar);
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 添加了 "+sub+" 记录","添加记录",module);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			if(null!=request.getParameter("result_path")){this.resultPath=request.getParameter("result_path");};
			if(null!=request.getParameter("result_page")){this.resultPage=request.getParameter("result_page");};
			if(null!=request.getParameter("result_url")){this.resultUrl=request.getParameter("result_url");};
			if(null!=request.getParameter("redirect_path")){this.redirectPath=request.getParameter("redirect_path");};
			if(null!=request.getParameter("redirect_page")){this.redirectPage=request.getParameter("redirect_page");};
			if(null!=request.getParameter("redirect_url")){this.redirectUrl=request.getParameter("redirect_url");};
			String resultMsg="ok";
			int resultCode=0;
			resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("addRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public String getProjectId(){
		String id="PRJ_"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
		return id;
	}
	public void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String[] ids = request.getParameterValues("id");

		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(ids!=null && dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			/*----------------------------------------数据获取完毕，开始和数据库交互*/
			FileDao fileDao=new FileDao();
			jsonObj=fileDao.deleteRecord(action,dbName,ids,creator,createTime);
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 删除了 "+sub+" 记录","删除记录",module);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("deleteRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：申请加入该项目，变成该项目成员
	 */
	public void applyJoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		showDebug(resultUrl);
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String projectId= request.getParameter("project_id");
		String projectName= request.getParameter("project_name");
		String applyContent= request.getParameter("apply_content");
		String applyType= request.getParameter("apply_type");
		
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String userName=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			FileDao fileDao=new FileDao();
			Apply apply=new Apply();
			apply.setProjectId(projectId);
			apply.setProjectName(projectName);
			apply.setUserId(userId);
			apply.setUserName(userName);
			apply.setSessionId(session.getId());
			apply.setApplyContent(applyContent);
			apply.setApplyType(applyType);
			apply.setCreator(userName);
			apply.setCreateTime(createTime);
			apply.setProjectId(projectId);
			jsonObj=fileDao.applyJoin(action,dbName,apply);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="您的申请已经提交，请耐心等待审核，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("applyJoin结果URL："+url);
			response.sendRedirect(url);
		}
	}

	public void applyCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String checkResult = request.getParameter("result");
		String id = request.getParameter("id");
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String projectId= request.getParameter("project_id");
		String memberId= request.getParameter("member_id");
		
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String userName=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			FileDao fileDao=new FileDao();
			Apply apply=new Apply();
			apply.setId(Integer.parseInt(id));
			apply.setUserId(userId);
			apply.setUserName(userName);
			apply.setSessionId(session.getId());
			apply.setCreator(userName);
			apply.setCreateTime(createTime);
			apply.setResult(checkResult);
			apply.setProjectId(projectId);
			jsonObj=fileDao.applyCheck(action,dbName,apply);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultUrl="project/file/project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("applyCheck结果URL："+url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：查询记录
	 */
	public void getMemberRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String projectId=request.getParameter("project_id");
		String type= request.getParameter("type");
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			MemberDao memberDao=new MemberDao();
			jsonObj=memberDao.getRecord(action,dbName,type,userId,projectId);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void addMemberRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String creator=(String)session.getAttribute("user_name");
		String userId=(String)session.getAttribute("user_id");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());

		Member member=new Member();
		member.setProjectId(request.getParameter("project_id"));
		member.setMemberId(request.getParameter("member_id"));
		member.setMemberName(request.getParameter("member_name"));
		member.setMemberRole(request.getParameter("member_role"));
		member.setAvatar(request.getParameter("avatar"));
		member.setCreator(creator);
		member.setCreateTime(createTime);
		String projectId=request.getParameter("project_id");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			MemberDao memberDao=new MemberDao();
			jsonObj=memberDao.addRecord(action,dbName,member);
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 添加了 "+sub+" 记录","添加记录",module);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			if(null!=request.getParameter("result_path")){this.resultPath=request.getParameter("result_path");};
			if(null!=request.getParameter("result_page")){this.resultPage=request.getParameter("result_page");};
			if(null!=request.getParameter("result_url")){this.resultUrl=request.getParameter("result_url");};
			if(null!=request.getParameter("redirect_path")){this.redirectPath=request.getParameter("redirect_path");};
			if(null!=request.getParameter("redirect_page")){this.redirectPage=request.getParameter("redirect_page");};
			if(null!=request.getParameter("redirect_url")){this.redirectUrl=request.getParameter("redirect_url");};
			String resultMsg="ok";
			int resultCode=0;
			resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			this.resultUrl=this.resultPath+"/"+"member_list.jsp";
			String url = resultUrl+"?project_id="+projectId+"&result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("addRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void deleteMemberRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String[] ids = request.getParameterValues("id");

		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(ids!=null && dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			/*----------------------------------------数据获取完毕，开始和数据库交互*/
			MemberDao memberDao=new MemberDao();
			jsonObj=memberDao.deleteRecord(action,dbName,ids,creator,createTime);
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 删除了 "+sub+" 记录","删除记录",module);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("deleteRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：查询记录
	 */
	public void getMemberRecordById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String type= request.getParameter("type");
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			MemberDao memberDao=new MemberDao();
			jsonObj=memberDao.getRecordById(action,dbName,id);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void getApplyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String content=request.getParameter("content");
		String type= request.getParameter("type");
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			FileDao fileDao=new FileDao();
			jsonObj=fileDao.getApplyRecord(action,dbName,id);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="project_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void processJoinApplyNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String content = request.getParameter("content");

		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		FileDao fileDao=new FileDao();
		fileDao.setNoticeRead(action, dbName, userId, creator, createTime);
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl=resultPath+"/member_apply_check.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = redirectUrl+"?type="+action+"&content="+content;
			response.sendRedirect(url);
		}
	}
}
