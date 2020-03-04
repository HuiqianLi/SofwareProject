<%@page contentType="text/html; charset=UTF-8" %>   
<%@page import="java.sql.*" %> 
<form action="result_home.jsp" method="POST">

<%-- 查询 --%>
<div id="pop" style="border:1px solid #CCC;display:none;background-color: white;text-align: center;"><br>
<div align="center">注意:选择删除、查询功能时,只需输入用户名即可!</div>
</br>
	<div align="center"> 
	 	<span style="margin-left:200px;width:18px;overflow:hidden;">
 		<select style="width:118px;margin-left:-100px" onchange="this.parentNode.nextSibling.value=this.value">
 		<option value="增加">增加</option>
 		<option value="删除">删除</option>
 		<option value="查询">查询</option>
 		<option value="修改">修改</option>
 		</select></span><input name="box" size="30" placeholder="请选择功能选项">
 		<td><input type="text" name="word1" size="30" placeholder="请输入ID"></td>
		<td><input type="text" name="word" size="30" placeholder="请输入用户名"></td>
		<span style="margin-left:95px;width:12px;overflow:hidden;">
 		<select style="width:118px;margin-left:-100px" onchange="this.parentNode.nextSibling.value=this.value">
 		<option value="normal">普通用户</option>
 		<option value="manager">管理员</option>
		</select></span><td><input type="text" name="word2" size="13" placeholder="请输入用户角色"></td>
		<td><input type="password" name="word3" size="30" placeholder="请输入密码"></td>
		</br>
   		<th><input type="submit" class="btn green" value="确定" align="center"></th>
  	</div>
</br>
	<a href="javascript:hidePop();" style="display: block; text-align: center;">hide</a>
</div> 

