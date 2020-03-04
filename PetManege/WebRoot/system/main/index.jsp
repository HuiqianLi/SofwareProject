<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<!-- BEGIN PAGE SCRIPTS -->
<script type="text/javascript" src="system.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<%
    request.setCharacterEncoding("utf-8");
%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()) + "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	String id = request.getParameter("id");
	String existResultset = request.getParameter("exist_resultset");
%>
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
				<%@include file="popup.jsp"%>
				<div class="row">
					<div class="col-md-12">
						<div class="portlet box blue ">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-gift"></i><span id="title_div">记录显示</span>
								</div>
								<div class="tools">
									<a id="tools_menu_reload" href="" class="reload"> </a>
									<a id="tools_menu_remove" href="" class="remove"> </a>
								</div>
							</div>
							<div class="portlet-body form">
								<form class="form-horizontal" role="form" method="post" id="page_form" name="page_form" action="#">
									<input type="hidden" id="action" name="action" value="get_record" />
									<input type="hidden" id="id" name="id" value="<%=id%>" />
									<input type="hidden" id="exist_resultset" name="exist_resultset" value="<%=existResultset%>" />
									<div class="form-body">
										<div class="form-group">
											<div style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;">
									
												<button type="button" id="add_button" class="btn green" title="添加新的记录" onclick="pop()">
													添加记录
												</button>
												<button type="button" id="delete_button" class="btn green" title="删除 条件查询对应的记录" onclick="pop()">
													删除记录
												</button>
												<button type="button" id="search_button" class="btn green" title="设置 条件查询对应的记录" onclick="pop()">
													查询记录
												</button>
												<button type="button" id="modify_button" class="btn green" title="修改已有用户的记录" onclick="pop()">
													修改记录
												</button>
											</div>
											<div style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;">
												<button type="button" id="return_button" class="btn green" onclick="javascript:history.back(-1);" title="返回到前一个页面">
													返回
												</button>
											</div>
										</div>
									</div>
									<div class="form-body">
										<div class="form-group">
											<label id="record_list_tip" class="col-md-12">
												记录列表
											</label>
											<%@include file="list.jsp"%>
										</div>
										<div class="form-group">
											<div class="col-md-12">
												<div id="record_list_div"></div>
											</div>
										</div>
									</div>
								</form>
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

