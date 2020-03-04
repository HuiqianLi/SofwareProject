package user.login;
import dao.YlxId;
import dao.ylx_db;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.DefaultCategoryDataset;
import org.jfree.data.DefaultPieDataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import utility.LogEvent;
import utility.MD5Util;
import utility.excel.ExcelWriter;

public class ServletAction extends HttpServlet {
	//这里是需要改的,module和sub
	public String module = "user";
	public String sub = "login";
	public String preFix = module + "_" + sub;
	public String resultPath = module + "/" + sub;
	public String resultPage = "result.jsp";
	public String resultUrl=resultPath+"/"+resultPage;
	public String redirectPath = module + "/" + sub;
	public String redirectPage = "record_list.jsp";
	public String redirectUrl=redirectPath+"/"+redirectPage;
	public String databaseName="test";
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public LogEvent ylxLog = new LogEvent();
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
				
				if (!actionOk) {
					try {
						processError(request, response,2,"["+module+"/"+sub+"/ServletAction]没有对应的action处理过程，请检查action是否正确！action="+action);
					} catch (JSONException e) {
						e.printStackTrace();
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
	 * 功能：查询记录
	 */
	public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action=request.getParameter("action");
	    String passwd=request.getParameter("passwd");
	    String userName=request.getParameter("user_name");
	    String userRole=request.getParameter("user_role");
	    String userAccount=request.getParameter("user_account");

	    System.out.println("获得的参数是:name="+userAccount);

	    List jsonList=new ArrayList();
	    try{
	        Class.forName("com.mysql.jdbc.Driver");
	    }catch (ClassNotFoundException clssNotFoundException){
	        clssNotFoundException.printStackTrace();
	    }
	    try{
	        String url="jdbc:mysql://localhost/test";	//这里改成自己的
	        Connection con=DriverManager.getConnection(url,"ylx","ylx");	//这里改成自己的
	        Statement stmt=con.createStatement();
	        System.out.println("数据库连接成功!");
	        String sql="select * from member_info where user_account='"+userAccount+"'";

	        System.out.println("构造的sql语句是:"+sql);
	        ResultSet rs=stmt.executeQuery(sql);
	        while(rs.next()){
	            Map map=new HashMap();
	            map.put("user_name",rs.getString("user_name"));
	            map.put("passwd",rs.getString("passwd"));
	            map.put("user_role",rs.getString("user_role"));
	            map.put("user_account",rs.getString("user_account"));
	            map.put("email",rs.getString("email"));
	            jsonList.add(map);
	        }
	        stmt.close();
	        con.close();
	        System.out.println("数据库关闭!");
	    }catch (SQLException sqlexception){
	        sqlexception.printStackTrace();
	    }

	    //回传
	    JSONObject json=new JSONObject();
	    json.put("aaData",jsonList);
	    json.put("result_msg","ok");
	    json.put("result_code",0);
	    json.put("version", "1.0");
	    System.out.println("最后构造的json是"+json.toString());
	    response.setContentType("text/html;charset=UTF-8");
	    try{
	        response.getWriter().print(json);
	        response.getWriter().flush();
	        response.getWriter().close();
	    }catch (IOException e){
	        e.printStackTrace();
	    }
	    System.out.println("返回给调用页面了!");
	}
	public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String userId=request.getParameter("register_user_id");
		String passWd=request.getParameter("register_passwd");
		String fullName=request.getParameter("register_full_name");
		String Email=request.getParameter("email");
		String user_id="zhangsan";
		String userRole="normal";
		showDebug("[addRecord]结果URL："+userId);
		//检查输入参数是否正确先

		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		String tableName="member_info";
		String url="jdbc:mysql://localhost/test";	//这里改成自己的
		Connection con=DriverManager.getConnection(url,"ylx","ylx");	//这里改成自己的
		Statement stmt=con.createStatement();
		System.out.println("数据库连接成功!");
		String sql="insert into "+tableName+"(user_account,passwd,user_name,email,create_time,user_id,user_role) values('"+userId+"','"+passWd+"','"+fullName+
				"','"+Email+"','"+createTime+"','"+user_id+"','"+userRole+"')";
		System.out.println("构造的sql语句是:"+sql);
		boolean rs=stmt.execute(sql);
		System.out.println("数据库交互完成!");
		stmt.close();
		con.close();
		System.out.println("数据库关闭!");


	}
	
	
}