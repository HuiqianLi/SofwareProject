<%@ page contentType="text/html"%>  
<%@page pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
%>
<html>  
<head>
<title>查询主页</title>
</head>
<body style="background:url(http://img1.imgtn.bdimg.com/it/u=1680093947,1418848796&fm=200&gp=0.jpg) center;background-size:cover"><%--设置背景图片--%>
</br></br></br>
<form action="search_res.jsp" method="POST">
<table align="center">
<h1 style="text-align:center;font-size:50px">用户查询</h1> 
</table>
<table align="center"> 
<tr>
</br>
    <td><input type="text" name="word" size="30" placeholder="请输入用户名中的关键字进行查询"></td>
    <th><input type="submit" value="搜索"></th>
</tr>
</table>
</form>


</table>
<h1 align="center" ><input type="button" name="Submit" onclick="javascript:history.back(-1);" value="返回上一页"> </h1>
</body>
</html> 
