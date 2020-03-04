<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%--
	String id = request.getParameter("id");
	String existResultset = request.getParameter("exist_resultset");
--%>
<!DOCTYPE html>
<head>
	<title>管理系统</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1" name="viewport" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../../home/frame/frame_style.jsp"%>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
	<%@include file="../../home/frame/frame_top.jsp"%>
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<%@include file="../../home/frame/frame_left.jsp"%>
		<div class="page-content-wrapper">
			<!-- BEGIN CONTENT -->
			<div class="page-content">
				<%@include file="../../home/frame/frame_portlet.jsp"%>
				<%@include file="../../home/frame/frame_theme_color.jsp"%>
				<%@include file="../../home/frame/frame_page_header.jsp"%>
				<!-- BEGIN PAGE CONTENT-->
				<!-- ----------------------------------------页面开始，替换这里即可---------------------------------------- -->
				<div class="row">
					<div class="col-md-12">
						<div class="portlet box blue ">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-gift"></i><span id="title_div">版本显示</span>
								</div>
							</div>
						</div>
						<div class="portlet-body form" style="float:left;width:50%;">
        					<h1 class="main-title" style="text-align:center">宠物信息管理系统1.1</h1>
        					</br>
        					<div style="font-size:16px">
        					<p style="text-align:center">2019/12/15更新</p>
                    		<p style="text-align:center">实现了管理员的用户管理功能，包括用户表的增删查改</p>
                    		<p style="text-align:center">实现了管理员与用户不同权限下的不同功能</p>
                    		<p style="text-align:center">完成了用户注册、登录模块</p>
                    		<p style="text-align:center">修正了宠物管理中的bug</p>
                    		</br>
                    		<p style="text-align:center">待改进部分：</p>
                    		<p style="text-align:center">前端美化</p>
                    		<p style="text-align:center">增添用户个人中心</p>
                    		<p style="text-align:center">代码优化</p>
                    		</div>
    					</div>
						 <div class="portlet-body form" style="float:left;width:50%;">
        					<h1 class="main-title" style="text-align:center">宠物信息管理系统1.0</h1>
        					</br>
        					<div style="font-size:16px">
        					<p style="text-align:center">2019/11/25更新</p>
                    		<p style="text-align:center">实现了前端与数据库的交互（增删查改）</p>
                    		<p style="text-align:center">完成了用户操作模块</p>
                    		</br>
                    		<p style="text-align:center">待完成部分：</p>
                    		<p style="text-align:center">修改登录功能，目前仍有部分bug</p>
                    		<p style="text-align:center">完成管理员模块，主要对系统普通用户进行管理</p>
                    		</div>
    					</div>
					</div>
				</div>
				<!-- ----------------------------------------页面结束，替换上面即可---------------------------------------- -->
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END CONTENT -->
			<%@include file="../../home/frame/frame_sidebar.jsp"%>
		</div>
		<!-- END CONTENT WRAPPER-->
	</div>
	<!-- END CONTAINER -->
	<%@include file="../../home/frame/frame_bottom.jsp"%>
	<%@include file="../../home/frame/frame_ajax_modal.jsp"%>
</body>
</html>
<%@include file="../../home/frame/frame_page_component.jsp"%>
<%@include file="../../home/frame/frame_javascript.jsp"%>
