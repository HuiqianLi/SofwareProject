package project.document;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.dao.Document;
import project.dao.DocumentDao;

import utility.LogEvent;
import utility.FileUpload;
import utility.MD5Util;
public class ServletAction extends HttpServlet {
	//这里是需要改的,module和sub
	public String module = "project";
	public String sub = "document";
	
	public String preFix = module + "_" + sub;
	public String resultPath = module + "/" + sub;
	public String resultPage = "result.jsp";
	public String resultUrl=resultPath+"/"+resultPage;
	public String redirectPath = module + "/" + sub;
	public String redirectPage = "record_list.jsp";
	public String redirectUrl=redirectPath+"/"+redirectPage;
	public String databaseName="ylxdb";
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public LogEvent ylxLog = new LogEvent();

	/*
	 * 处理顺序：先是service，后根据情况doGet或者doPost
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//System.out.println("执行到：service");
		processAction(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("执行到：doPost");
		//doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("执行到：doGet");
		//processAction(request,response);
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
				//getRecord(),getRecordById(),deleteRecord(),modifyRecord(),searchRecord()这几个常规增删改查功能
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
				if (action.equals("get_project_record")) {
					actionOk=true;
					try {
						getProjectRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("download_record")) {
					actionOk=true;
					try {
						downloadRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
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
		DocumentDao documentDao=new DocumentDao();
		JSONObject jsonObj=documentDao.doAction(action,dbName,id,content,creator,createTime);
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
			redirectUrl="record_list.jsp";
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
		JSONObject jsonObj=null;
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求

		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String content=request.getParameter("content");
		String type= request.getParameter("type");
		String projectId= request.getParameter("project_id");
		String projectName= request.getParameter("project_name");
		String newSearch= request.getParameter("new_search");
		if((newSearch==null) ||(newSearch.equals("null") || newSearch.isEmpty())) newSearch="0";
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		//检查输入参数是否正确先
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		if(projectId!=null && (projectId.equals("null") || projectId.isEmpty()))
			projectId=null;
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		showDebug("要查询的项目ID："+projectId+"，项目名称："+projectName);
		if(newSearch.equals("1")){			//如果是新查询
			if(dbName!=null){
				//查询数据库，查完保存到session里，这里要注意是转换成String去保存
				DocumentDao documentDao=new DocumentDao();
				jsonObj=documentDao.getRecord(action,dbName,type,userId,projectId,projectName);
				session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj.toString());
			}
		}else{
			if(isAjax){
				//如果有就取出来，如果没有就重新查询一次，并且保存进session里
				if(session.getAttribute(module+"_"+sub+"_get_record_result")!=null){
					jsonObj=new JSONObject((String)session.getAttribute(module+"_"+sub+"_get_record_result"));
				}else{
					if(dbName!=null){
						DocumentDao documentDao=new DocumentDao();
						jsonObj=documentDao.getRecord(action,dbName,type,userId,projectId,projectName);
						session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj.toString());
					}
				}
			}else{}
		}
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
			redirectUrl="record_list.jsp";
			resultUrl=resultPath+"/document_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?new_search=0&result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
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
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(id!=null && dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			DocumentDao documentDao=new DocumentDao();
			jsonObj=documentDao.modifyRecord(action,dbName,id,content,creator,createTime);
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
			redirectUrl="record_list.jsp";
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
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			DocumentDao documentDao=new DocumentDao();
			jsonObj=documentDao.getRecordById(action,dbName,id);
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
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj.toString());
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecordById结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String title=request.getParameter("title");
		String pageCount=request.getParameter("page_count");
		String projectId=request.getParameter("project_id");
		String documentType=request.getParameter("document_type");
		if(pageCount==null) pageCount="0";
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			/*----------------------------------------数据获取完毕，开始和数据库交互*/
			//文件存哪里，什么子目录，url多少，
			String rootPath="C:\\";
			String filePath = rootPath+"upload\\project\\document\\"+projectId+"\\";	//用户路径
			String urlPath="/upload/project/document/"+projectId+"/";
			FileUpload iu=new FileUpload();
			iu.setSession(session);
			iu.setRootPath(rootPath);
			iu.setFilePath(filePath);
			iu.setUrlPath(urlPath);
			jsonObj=iu.upload(request,response);
			//赋值给show去保存，jsonObj里可能有多个记录
			JSONArray arr=jsonObj.getJSONArray("files");
			int imageCount=0;
			for(int i=0;i<arr.length();i++){
				urlPath=(String)((HashMap)(arr.get(i))).get("url_path");
				filePath=(String)((HashMap)(arr.get(i))).get("file_path");
				String fileName=(String)((HashMap)(arr.get(i))).get("file_name");
				String fileSize=((HashMap)(arr.get(i))).get("file_size")+"";
				if(!fileName.isEmpty()){
					DocumentDao documentDao=new DocumentDao();
					Document document=new Document();
					document.setDocumentId(getDocumentId());
					document.setProjectId(projectId);
					document.setDocumentName(fileName);
					document.setDocumentNick(fileName);
					document.setPageCount(pageCount);
					document.setFileName(fileName);
					document.setFilePath(filePath.replaceAll("\\\\","\\\\\\\\"));
					document.setFileSize(fileSize);
					document.setUrlPath(urlPath);
					document.setDocumentType(documentType);
					document.setCreator(creator);
					document.setCreateTime(createTime);
					jsonObj=documentDao.addRecord(action,dbName,document);
					imageCount=imageCount+1;
				}
			}
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
			String resultMsg="ok";
			int resultCode=0;
			resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			redirectUrl="document_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl+"&redirect_param="+projectId+"&redirect_param_name=project_id";
			showDebug("addRecord结果URL："+url);
			response.sendRedirect(url);
		}
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
			DocumentDao documentDao=new DocumentDao();
			jsonObj=documentDao.deleteRecord(action,dbName,ids,creator,createTime);
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
			redirectUrl="record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("deleteRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void getProjectRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String content=request.getParameter("content");
		String type= request.getParameter("type");
		String projectId= request.getParameter("project_id");
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			DocumentDao documentDao=new DocumentDao();
			jsonObj=documentDao.getProjectRecord(action,dbName,type,userId);
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
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj.toString());
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	private String getDocumentId(){
		String id="DOC_"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
		return id;
	}
	public void downloadRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		DocumentDao documentDao=new DocumentDao();
		JSONObject jsonObj=documentDao.getRecordById(action,dbName,id);
		JSONArray arr=jsonObj.getJSONArray("aaData");
		String filePath="";
		String fileName="";
		for(int i=0;i<arr.length();i++){
			filePath=(String)((List)(arr.get(i))).get(7);
			fileName=(String)((List)(arr.get(i))).get(8);
		}
		FileUpload iu=new FileUpload();
		iu.setSession(session);
		showDebug("文件是："+filePath+","+fileName);
		iu.download(request, response, filePath, fileName);
		//完毕后登记一下下载数量
		documentDao.IncDownloadCount(action,dbName,id);
	}
}
