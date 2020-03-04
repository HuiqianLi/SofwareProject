<%@page contentType="text/html; charset=UTF-8" %>   
<%@page import="java.sql.*" %> 
<%
    request.setCharacterEncoding("utf-8");
%>
<html>
<head>
<title >查询</title>
</head>
<%      
        try {  
            Class.forName("com.mysql.jdbc.Driver");  ////驱动程序名
            String url = "jdbc:mysql://localhost:3306/test"; //数据库名
            String username = "ylx";  //数据库用户名
            String password = "ylx";  //数据库用户密码
            Connection conn = DriverManager.getConnection(url, username, password);  //连接状态
            if(conn != null){  
                //out.print("数据库连接成功！");  
                //out.print("<br />");  
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
                String sql = "select * from member_info";    //查询语句
                stmt = conn.createStatement();  
                rs = stmt.executeQuery(sql);  
                //out.print("查询结果：");  
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
            out.print("数据库连接异常！");  
        }  
%>

</table>
</body>
</html>
