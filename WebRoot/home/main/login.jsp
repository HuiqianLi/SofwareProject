<%@page contentType="text/html; charset=UTF-8" import="java.sql.*,java.text.*"%>
<%
	String userId=null;
	String userName=null;
	String userAvatar=null;
	String userRole=null;
	if(session.getAttribute("user_role")!=null){
		userRole=(String)session.getAttribute("user_role");
		userAvatar=(String)session.getAttribute("user_avatar");
		userId=(String)session.getAttribute("user_id");
		userName=(String)session.getAttribute("user_name");
	} 
	if(userName!=null && userId!=null && userAvatar!=null && userRole!=null){
		response.sendRedirect("index.jsp");
	}else{}
	String openId="";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
	<!--<![endif]-->
	<!-- BEGIN HEAD -->
	<head>
		<meta charset="utf-8" />
		<title>登陆</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1.0" name="viewport" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<meta content="" name="description" />
		<meta content="" name="author" />
		<!-- BEGIN GLOBAL MANDATORY STYLES -->
		<link href="../../assets/module/css/fonts.css" rel="stylesheet" type="text/css"/>
		<link href="../../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="../../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
		<link href="../../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
		<!-- END GLOBAL MANDATORY STYLES -->
		<!-- BEGIN PAGE LEVEL STYLES -->
		<!-- END PAGE LEVEL SCRIPTS -->
		<!-- BEGIN THEME STYLES -->
		<link href="../../assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css" />
		<link href="../../assets/global/css/plugins.css" rel="stylesheet" type="text/css" />
		<link href="../../assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css" />
		<link href="../../assets/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css" id="style_color" />
		<link href="../../assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css" />
		<!-- END THEME STYLES -->
		<link rel="shortcut icon" href="favicon.ico" />
	</head>
	<!-- END HEAD -->
	<!-- BEGIN BODY -->
	<body class="login">
		<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
		<div class="menu-toggler sidebar-toggler">
		</div>
		<!-- END SIDEBAR TOGGLER BUTTON -->
		<!-- BEGIN LOGO -->
		<div class="logo">
			<!-- <img src="../../assets/admin/layout/img/logo-big.png" alt=""/> -->
			<img src="../../assets/module/img/home/logo-big.png" alt=""/>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN LOGIN -->
		<div class="content">
			<!-- BEGIN LOGIN FORM -->
			<form class="login-form" action="../../security_user_control_action?action=login" method="post">
				<input type="hidden" id="open_id" name="open_id" value="<%=openId %>"/>
				<h3 class="form-title">
					登陆
				</h3>
				<div class="alert alert-danger display-hide">
					<button class="close" data-close="alert"></button>
					<span> 请输入账号密码登陆</span>
				</div>
				<div class="form-group">
					<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
					<label class="control-label visible-ie8 visible-ie9">
						用户账号：
					</label>
					<input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入账号" id="user_id" name="user_id" />
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						登陆密码：
					</label>
					<input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="请输入密码" id="user_password" name="user_password" />
				</div>
				<div class="form-actions">
					<button type="submit" class="btn btn-success uppercase">
						登陆
					</button>
					<label class="rememberme check">
						<input type="checkbox" id="remember" name="remember" value="1" />
						记住我
					</label>
					<a href="javascript:;" id="forget-password" class="forget-password">忘记密码了？</a>
				</div>
				<div class="login-options">
					<h4 style="color:red;">
						本系统仅支持Chrome浏览器，请用Chrome浏览器访问平台。
					</h4>
				</div>
				<div class="create-account-left">
					<p>
						<a href="javascript:;" id="register-btn" class="uppercase">注册新账户</a>
					</p>
				</div>
				<div class="create-account-right">
					<p>
						<a href="javascript:;" id="scan-qrcode-btn" class="uppercase">扫码登录</a>
					</p>
				</div>
			</form>
			<!-- END LOGIN FORM -->
			<!-- BEGIN QRCODE FORM -->
			<form class="qrcode-form" action="index.html" method="post" id="qrcode-form">
				<h3>
					二维码扫描登录
				</h3>
				<p>
					请用微信或者微课百家APP扫描二维码登录，如果在之前在平台注册了账号并且捆绑了微信，会直接跳到登录页面。
				</p>
				<p>
					如果无法登陆，请确定您是否已经在平台注册了账号。
				</p>
				<div class="form-group">
					<div id="qrcode_canvas" style="margin:auto;width:260px;height:260px;border:0px solid red;">
						<img id="qrcode_image" style="width:100%;height:100%;"/>
					</div>
				</div>
				<div class="form-actions">
					<button type="button" id="qrcode-back-btn" class="btn btn-default">
						返回
					</button>
				</div>
			</form>
			<!-- END QRCODE FORM -->
			<!-- BEGIN FORGOT PASSWORD FORM -->
			<form class="forget-form" action="index.html" method="post">
				<h3>
					忘记密码了？
				</h3>
				<p>
					请输入您的用户ID
				</p>
				<div class="form-group">
					<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" id="forget_user_id" name="forget_user_id" />
				</div>
				<p>
					请输入您的邮箱
				</p>
				<div class="form-group">
					<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" id="forget_email" name="forget_email" />
				</div>
				<div class="form-actions">
					<button type="button" id="back-btn" class="btn btn-default">
						返回
					</button>
					<button type="button" id="emailinput-btn" class="btn btn-success uppercase pull-right">
						提交
					</button>
				</div>
			</form>
			<!-- END FORGOT PASSWORD FORM -->
			<!-- BEGIN EMAIL MODIFY PASSWORD FORM -->
			<form class="emailcheck-form" action="index.html" method="post">
				<h3>
					密码修改
				</h3>
				<p>
					系统已经往您的邮箱<span id="receive_email"></span>发了一个链接，请前往邮箱查看。
				</p>
				<div class="form-actions">
					<button type="button" id="emailcheck-btn" class="btn btn-default">
						查看邮箱
					</button>
				</div>
			</form>
			<!-- END EMAIL MODIFY PASSWORD FORM -->
			<!-- BEGIN REGISTRATION FORM -->
			<form class="register-form" action="../../security_user_control_action?action=sign_up" method="post">
				<h3>
					注册
				</h3>
				<p class="hint">
					请输入您的账户信息（必填）
				</p>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						用户名
					</label>
					<input class="form-control placeholder-no-fix" type="text" placeholder="用户账号，建议字母或者数字" id="register_user_id" name="register_user_id" />
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						密码
					</label>
					<input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="密码" name="register_password" />
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						重新输入密码
					</label>
					<input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="重复输入密码" id="register_password_again" name="register_password_again" />
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						姓名
					</label>
					<input class="form-control placeholder-no-fix" type="text" placeholder="姓名，请输入汉字的真实名字" id="register_full_name" name="register_full_name" />
				</div>
				<div class="form-group">
					<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
					<label class="control-label visible-ie8 visible-ie9">
						邮箱
					</label>
					<input class="form-control placeholder-no-fix" type="text" placeholder="请输入你的邮箱，忘记密码时系统可凭邮箱恢复密码" name="email" />
				</div>
				<div style="display:none;">
				<p class="hint">
					请输入您的其他个人详细信息（选填）：
				</p>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						地址
					</label>
					<input class="form-control placeholder-no-fix" type="text" placeholder="地址" name="address" />
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						所在城市
					</label>
					<input class="form-control placeholder-no-fix" type="text" placeholder="城市" name="city" />
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						国籍
					</label>
					<%@include file="country.jsp"%>
				</div>
				</div>
				<div class="form-group margin-top-20 margin-bottom-20">
					<label class="check">
						<input type="checkbox" name="tnc" />
						我同意
						<a href="javascript:Page.serviceRule();"> 服务条款</a> 和
						<a href="javascript:Page.privateRule();"> 隐私条款</a>
					</label>
					<div id="register_tnc_error">
					</div>
				</div>
				<div class="form-actions">
					<button type="button" id="register-back-btn" class="btn btn-default">
						返回
					</button>
					<button type="submit" id="register-submit-btn" class="btn btn-success uppercase pull-right">
						提交
					</button>
				</div>
			</form>
			<!-- END REGISTRATION FORM -->
		</div>
		<div class="copyright">
			2015-2025 © YLX. Technology Co.Ltd.
		</div>
		<!-- END JAVASCRIPTS -->
	</body>
	<!-- END BODY -->
</html>
<!-- END LOGIN -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="../../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="../../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../../assets/module/plugins/jquery-qrcode-master/jquery.qrcode.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="../../assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<link href="../../assets/module/css/home/login.css" rel="stylesheet" type="text/css" />
<script src="../../assets/module/scripts/home/login.js" type="text/javascript"></script>
