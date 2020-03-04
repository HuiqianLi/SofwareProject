<%@page contentType="text/html; charset=UTF-8" %>   
<%@page import="java.sql.*" %> 
<%
    request.setCharacterEncoding("utf-8");
%>
<html>
<head>
<title >结果显示</title>
</head>
<%      
        try {  
            Class.forName("com.mysql.jdbc.Driver");  ////驱动程序名
            String url = "jdbc:mysql://localhost:3306/test"; //数据库名
            String username = "ylx";  //数据库用户名
            String password = "ylx";  //数据库用户密码
            Connection conn = DriverManager.getConnection(url, username, password);  //连接状态
            if(conn != null){  
                out.print("数据库连接成功！");  
                out.print("<br />");  
%>
<table align="center" border="2">
 <tr>
    <td width="250" name="title">ID</td>
    <td width="200" age="title">用户名</td>
    <td width="250" age="title">角色</td>
</tr>
<%
                Statement stmt = null;  
                ResultSet rs = null; 
                String sql = "";
                String sql1 = "";
                String box =request.getParameter("box");
                String word1 =request.getParameter("word1");
                String word =request.getParameter("word");
                String word2 =request.getParameter("word2");
                String word3 =request.getParameter("word3");
               	//out.print(word1+'\n');
               	if(box.equals("增加")){
               		if(word1==""||word2==""||word=="")
               		{
						%>
						<script>
               			alert("失败了失败了，ID，用户名，角色，密码都得填！");
               			</script>
               			<%
               		}
               		else
               		{
               			sql = "INSERT INTO member_info" + "(user_account,user_name,user_role,passwd)VALUES('"+word1+"','"+word+"','"+word2+"','"+word3+"')";
               			//out.print(word1+'\n');
               			stmt = conn.createStatement();  
						stmt.executeUpdate(sql);
               		}
               		
               	}
               	if(box.equals("删除")){
               		sql1 = "SELECT * FROM member_info WHERE user_name like '%"+word+"%' ";    //查询语句
               		stmt = conn.createStatement();  
                	rs = stmt.executeQuery(sql1);             
                	if(rs.next()==false)
                	{
                		%>
						<script>
               			alert("删不了，没有这个人！");
               			</script>
               			<%
                	}
                	else
                	{
                		sql = "delete from member_info where user_name='" + word + "'";
               			stmt = conn.createStatement();  
						stmt.executeUpdate(sql);
                	}
               		
               	}
               	if(box.equals("修改")){
               		sql1 = "SELECT * FROM member_info WHERE user_account= '"+word1+"' ";    //查询语句
               		stmt = conn.createStatement();  
                	rs = stmt.executeQuery(sql1);                 	
                	if(rs.next()==false)
                	{
                		%>
						<script>
               			alert("改不了，没有这个人！");
               			</script>
               			<%
                	}
                	else
                	{
                		sql = "update member_info set user_name='"+word+"',user_role='"+word2+"',passwd='"+word3+"' WHERE user_account= '"+word1+"'";
               			//out.print(word1+'\n');
               			stmt = conn.createStatement();  
						stmt.executeUpdate(sql);
                	}
               		
               	}
               	if(box.equals("查询")){        	
                	sql = "SELECT * FROM member_info WHERE user_name like '%"+word+"%' ";    //查询语句
                	//String sql = "select * from security_users";    //查询语句
                	stmt = conn.createStatement();  
                	rs = stmt.executeQuery(sql); 
                out.print("查询结果：");  
                }
                while (rs.next()) {%>
  <tr>  
    <td width="250"><%=rs.getString("user_account") %></td>  
    <td width="200"><%=rs.getString("user_name") %></td>  
    <td width="250"><%=rs.getString("user_role") %></td>
  </tr>
<%               
            } 
            }else{  
                out.print("连接失败！");  
            }  
        }catch (Exception e) {        
            e.printStackTrace();  
            //out.print("数据库连接异常！");  
        }  
%>

</table>
<%-- 
<h1 align="center" ><input type="button" name="Submit" onclick="javascript:history.back(-1);" value="返回上一页"> </h1>
--%>
</body>
</html>
