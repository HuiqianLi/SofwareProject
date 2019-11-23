<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<!--<![endif]-->
	<!-- BEGIN HEAD -->
	<head>
		<meta charset="utf-8" />
		<title>密码修改</title>
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
		<link href="../../assets/admin/pages/css/login.css" rel="stylesheet" type="text/css" />
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
		h<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
		<div class="menu-toggler sidebar-toggler">
		</div>
		<!-- END SIDEBAR TOGGLER BUTTON -->
		<!-- BEGIN LOGO -->
		<div class="logo">
			<a href="index.html"> <img src="../../assets/admin/layout/img/logo-big.png" alt="" /> </a>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN LOGIN -->
		<div class="content">
			<!-- BEGIN EMAIL MODIFY PASSWORD FORM -->
			<form class="modify-password-form" action="../../security_user_control_action?action=modify_password" method="post">
				<p class="hint">
					请输入您的密码信息（重复输入两遍）
				</p>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						用户名
					</label>
					<input type="hidden" id="user_id" name="user_id"/>
					<input type="hidden" id="session_id" name="session_id"/>
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						密码
					</label>
					<input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="user_password" placeholder="密码" name="user_password" />
				</div>
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">
						重新输入密码
					</label>
					<input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="重复输入密码" id="user_password_again" name="user_password_again" />
				</div>
				<div class="form-actions">
					<button type="submit" class="btn btn-success uppercase pull-right" onsubmit="checkInput();">
						提交
					</button>
				</div>
			</form>
			<!-- END EMAIL MODIFY PASSWORD FORM -->
		</div>
		<div class="copyright">
			2014 © Metronic. Admin Dashboard Template.
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
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="../../assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="../../assets/admin/pages/scripts/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<%
	String userId=request.getParameter("user_id");
	String sessionId=request.getParameter("session_id");
%>
<script>
jQuery(document).ready(function() {     
Metronic.init(); // init metronic core components
Layout.init(); // init current layout
Login.init();
Demo.init();
jQuery('#user_id').val("<%=userId%>");
jQuery('#session_id').val("<%=sessionId%>");
});
function checkInput(){
	var firstPassword=$("#user_password").val();
	var secondPassword=$("#user_password_again").val();
	if(firstPassword==secondPassword){
		return true;
	}else{
		alert("您两次输入的密码不一样！请重新核对后输入密码。");
		return false;
	}
}
</script>
