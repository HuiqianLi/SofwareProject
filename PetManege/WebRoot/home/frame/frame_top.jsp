<%@page contentType="text/html; charset=UTF-8"%>
<meta charset="utf-8" />
<!-- BEGIN HEADER -->
<div class="page-header navbar navbar-fixed-top" id="page_top_div">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN LOGO -->
		<div class="page-logo">
			<a href="../../index.jsp">
			<!-- <img src="../../assets/admin/layout/img/logo.png" alt="logo" class="logo-default"/> -->
			<img src="../../assets/module/img/home/logo.png" alt="logo" class="logo-default"/>
			</a>
			<div class="menu-toggler sidebar-toggler hide">
			</div>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<div class="top-menu">
			<ul id="left_menu_bar" class="nav navbar-nav pull-right">
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
				<li class="dropdown dropdown-user">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<img id="current_user_avatar" alt="" class="img-circle" src="../../assets/module/img/security/user/avatar/avatar.jpg"/>
					<span class="username username-hide-on-mobile" id="current_user"></span>
					<span id="unread_notice" class="badge badge-danger"></span>
					<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-menu-default">
						<li>
							<a href="../../home/main/index.jsp">
							<i class="icon-user"></i>返回主界面</a>
						</li>
						<li class="divider">
						<li>
							<a href="http://www.cdylx.org:9090/moban/metronic_v4.1.0/theme/templates/admin/index.html" target="_blank">
							<i class="icon-rocket"></i>我的参考
							</a>
						</li>
						<li class="divider">
						</li>
						<li>
							<a href="../../home/main/logout.jsp">
							<i class="icon-key"></i> 退出系统</a>
						</li>
					</ul>
				</li>
				<!-- END USER LOGIN DROPDOWN -->
				<!-- BEGIN QUICK SIDEBAR TOGGLER -->
				<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
				<li class="dropdown dropdown-quick-sidebar-toggler">
					<a href="javascript:;" class="dropdown-toggle">
					<i class="icon-logout"></i>
					</a>
				</li>
				<!-- END QUICK SIDEBAR TOGGLER -->
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<div class="clearfix"></div>
