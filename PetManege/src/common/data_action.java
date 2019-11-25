package common;


import dao.ylx_db;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class data_action extends HttpServlet {
	ylx_db query_db = null;
	String refreshCount = "";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String action = request.getParameter("action");
		//System.out.println("[common_data_action]收到的action是：" + action);
		boolean actionOk = false;
		if((action == null) && !action.equals("get_home_menu")){
			try {
				processError(request, response, 1, "传递过来的action是null！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (action.equals("get_main_menu")) {
			actionOk = true;
			try {
				getMainmenu(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (action.equals("get_home_menu")) {
			actionOk = true;
			try {
				getHomemenu(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (action.equals("get_system_status")) {
			actionOk = true;
			try {
				getSystemStatus(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (!actionOk) {
			try {
				processError(request, response, 2, "没有对应的action处理过程，请检查action是否正确！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void processError(HttpServletRequest request, HttpServletResponse response, int errorNo, String errorMsg) throws JSONException {
		String action = request.getParameter("action");
		// 构造返回结果的json
		JSONObject jsonObj = new JSONObject();
		// 共同部分
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("result_msg", "ok");
		jsonObj.put("result_code", 0);
		String json = jsonObj.toString();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	/*
	 * 功能：获取模块单独的菜单，xxx_tree表里的
	 */
	public void getMainmenu(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String module = request.getParameter("module");
		String role=(String)session.getAttribute("user_role");
		List jsonList = new ArrayList();
		String resultMsg="success";
		int resultCode=0;
		if (session.getAttribute("unit_db_name") == null) {
			resultCode=1;
			resultMsg="数据库为空！";
		} else {
			query_db = new ylx_db((String) session.getAttribute("unit_db_name"));
			String id = request.getParameter("id");
			String tableName = request.getParameter("table_name");
			String order = request.getParameter("order");
			if (order == null)
				order = "";
			String where = request.getParameter("where");
			if (where == null)
				where = " where role_id='"+role+"'";
			else
				where = " where role_id='"+role+"' and "+where;
			String sql="";
			if(module.equals("home")){	//如果是主页菜单，对应document_main
				sql = "select value as module_name,reason as category_id,0 as parent_category_id" +
						",reason as file_id,value as file_name,value1 as file_path,value as hreflink" +
						",reason as chain_name,0 as details_tag,picture from " + tableName + where+ " and fieldType_tag=1";
				sql=sql.replace("role_id","role");
			}else{
				sql = "select * from "+module+"_tree a,"+module+"_view b where a.file_id=b.file_id and b.role_id='"+role+"'";
			}
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				Map map = new HashMap();
				// ////////////////////////////////////////独有部分，要修改的是这里
				map.put("module_name", rs.getString("module_name"));
				map.put("category_id", rs.getString("category_id"));
				map.put("parent_item_id", rs.getString("parent_category_id"));
				map.put("item_id", rs.getString("file_id"));
				map.put("item_name", rs.getString("file_name"));
				map.put("file_path", rs.getString("file_path"));
				map.put("href_link", rs.getString("hreflink"));
				map.put("chain_name", rs.getString("chain_name"));
				map.put("details_tag", rs.getString("details_tag"));
				map.put("picture", rs.getString("picture"));
				// ////////////////////////////////////////独有部分修改完毕
				jsonList.add(map);
			}
			rs.close();
			query_db.close();
			//System.out.println(jsonList.toString());
		}
		JSONObject jsonObj = new JSONObject();
		// 共同部分
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("record_list", jsonList);
		jsonObj.put("result_msg", resultMsg);
		jsonObj.put("result_code", resultCode);
		String json = jsonObj.toString();

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(json);
	}
	/*
	 * 功能：document_main菜单，按照角色来查询对应的权限
	 * 查询出来的结果显示在首页菜单的面板上，大图标排列显示
	 */
	public void getHomemenu(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String role=(String)session.getAttribute("user_role");
		List jsonList = new ArrayList();
		String resultMsg="success";
		int resultCode=0;
		if(role!=null){
			if (session.getAttribute("unit_db_name") == null) {
				resultCode=10;
				resultMsg="数据库为空！";
			} else {
				query_db = new ylx_db((String) session.getAttribute("unit_db_name"));
				String id = request.getParameter("id");
				String tableName = request.getParameter("table_name");
				String order = request.getParameter("order");
				if (order == null)
					order = "";
				String where = request.getParameter("where");
				if (where == null)
					where = " where role='"+role+"'";
				else
					where = " where role='"+role+"' and "+where;
				String sql = "select * from " + tableName + where;
				ResultSet rs = query_db.executeQuery(sql);
				while (rs.next()) {
					Map map = new HashMap();
					// ////////////////////////////////////////独有部分，要修改的是这里
					map.put("main_code", rs.getString("main_code"));
					map.put("kind", rs.getString("kind"));
					map.put("reason", rs.getString("reason"));
					map.put("value", rs.getString("value"));
					map.put("picture", rs.getString("picture"));
					// ////////////////////////////////////////独有部分修改完毕
					jsonList.add(map);
				}
				rs.close();
				query_db.close();
				//System.out.println(jsonList.toString());
			}
		}else{
			resultCode=3;
			resultMsg="session超时，请重新登陆！";
		}
		JSONObject jsonObj = new JSONObject();
		// 共同部分
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("record_list", jsonList);
		jsonObj.put("result_msg", resultMsg);
		jsonObj.put("result_code", resultCode);
		String json = jsonObj.toString();

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getSystemStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession();
		String[] id = request.getParameterValues("id");
		String action = request.getParameter("action");
		String resultMsg="ok";
		int resultCode=0;
		JSONObject jsonObj = new JSONObject();
		List jsonList = new ArrayList();
		// 进行计算
		String userName=(String)session.getAttribute("user_name");
		String userId=(String)session.getAttribute("user_id");
		String userAvatar=(String)session.getAttribute("user_avatar");
		if(userId==null){
			resultCode=3;
			resultMsg="session 超时，请重新登陆！";
		}
		/*
		 * 
		 */
		// 构造返回结果的json
		jsonObj.put("user_name",userName);
		jsonObj.put("user_id",userId);
		jsonObj.put("user_avatar",userAvatar);
		// 共同部分
		jsonObj.put("aaData", jsonList);
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("result_msg", resultMsg);
		jsonObj.put("result_code", resultCode);
		String json = jsonObj.toString();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
