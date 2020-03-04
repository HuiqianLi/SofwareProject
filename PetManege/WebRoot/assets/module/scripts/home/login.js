jQuery(document).ready(function() {
	Metronic.init(); // init metronic core componets
	Layout.init(); // init layout
	Demo.init(); // init demo features
	Login.init();
	Page.init();
	Record.init();
});
var toCheckEmail="";
///////////////////////////////////////////////////////////////////////////////////////////////////////
var Login = function() {
    var handleLogin = function() {

        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },
            messages: {
                username: {
                    required: "请输入用户账号名。"
                },
                password: {
                    required: "请输入用户密码。"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   
                $('.alert-danger', $('.login-form')).show();
            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            /*submitHandler: function(form) {
                form.submit(); // form validation success, call ajax form submit
            }*/
        });
        $('.login-form #remember').click(function() {
        	if(this.checked){
            	if(($("#user_id").val()=="") || ($("#user_password").val()=="")){
            		alert("请您输入账号和密码！");
            	}else{
            		//记录进cookie里
            		var userId=$("#user_id").val();
            		var passwd=$("#user_password").val();
            		setCookie("username",userId,30);
            		setCookie("password",passwd,30);
            		setCookie("remember",true,30);
            	}
        	}else{
        		clearCookie("username");
        		clearCookie("password");
        		clearCookie("remember");
        	}
        });
        $('.login-form #login_in_button').click(function(json) {
        	
        	if(($("#user_id").val()=="") || ($("#user_password").val()=="")){
        		alert("请您输入账号和密码！");
        	}else{
        		//检查用户信息
        		var userId=$("#user_id").val();
        		var passwd=$("#user_password").val();
        		Record.CHECKUSER(userId,passwd);
        		
        	}
        	
        	
        });
        
        var clearCookie=function(name) {
        	setCookie(name, "", -1);
        }
        var getCookie=function(c_name){
        	if (document.cookie.length>0)
        	{
        		c_start=document.cookie.indexOf(c_name + "=")
        		if (c_start!=-1)
        		{
        			c_start=c_start + c_name.length+1 
        			c_end=document.cookie.indexOf(";",c_start)
        			if (c_end==-1) c_end=document.cookie.length
        				return unescape(document.cookie.substring(c_start,c_end))
        		}
        	}
        	return ""
        };
        var setCookie=function(c_name,value,expiredays){
        	var exdate=new Date();
        	exdate.setDate(exdate.getDate()+expiredays);
        	document.cookie=c_name+ "=" +escape(value)+
        		((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
        }
        var checkCookie=function(){
        	var username=getCookie('username');
        	var passwd=getCookie('password');
        	var remember=getCookie('remember');
        	if(remember){
        		$('#uniform-remember span').addClass("checked");
            	if (username!=null && username!="")
        			{$("#user_id").val(username);}else{}
            	if (passwd!=null && passwd!="")
            		{$("#user_password").val(passwd);}else{}
        	}
        };
        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    $('.login-form').submit(); //form validation success, call ajax form submit
                }
                return false;
            }
        });
        checkCookie();
    }
//////////////////////////////////////////////////////////////////////////////////////////
    var handleForgetPassword = function() {
        $('.forget-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                email: {
                    required: true,
                    email: true
                }
            },

            messages: {
                email: {
                    required: "请输入您的电子邮件地址。"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   

            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function(form) {
                form.submit();
            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.forget-form').validate().form()) {
                    $('.forget-form').submit();
                }
                return false;
            }
        });
        $("#emailinput-btn").click(function() {
           	var userId=$("#forget_user_id").val();
    		var Email=$("#forget_email").val();
    		if(checkforgetmsg()==true){
    			Record.querypasswd(userId,Email);
    		}
        	
        	});
        var checkforgetmsg=function(){
        	var tag=true;
        	var Email=$("#email").val();
        	if(($("#forget_user_id").val()=="") 
        		|| ($("#forget_email").val()=="")){
        		alert("仍有信息未填写！");
        		tag=false;
        	}
    		return tag;
        	
        }
        jQuery('#forget-password').click(function() {
            jQuery('.login-form').hide();
            jQuery('.forget-form').show();
        });
        
        jQuery('#back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.forget-form').hide();
        });
        
        jQuery('#emailcheck-btn').click(function() {
        	var addressUrl=gotoEmail(toCheckEmail);
        	if(addressUrl!=""){
        		window.location.href="http://"+addressUrl;
        	}
        });
        //功能：根据用户输入的Email跳转到相应的电子邮箱首页
        function gotoEmail($mail){
           $t=$mail.split('@')[1];
            $t=$t.toLowerCase();
            if($t=='163.com'){
                return 'mail.163.com';
            }else if($t=='vip.163.com'){
                return 'vip.163.com';
            }else if($t=='126.com'){
                return 'mail.126.com';
            }else if($t=='qq.com'||$t=='vip.qq.com'||$t=='foxmail.com'){
                return 'mail.qq.com';
            }else if($t=='gmail.com'){
                return 'mail.google.com';
            }else if($t=='sohu.com'){
                return 'mail.sohu.com';
            }else if($t=='tom.com'){
                return 'mail.tom.com';
            }else if($t=='vip.sina.com'){
                return 'vip.sina.com';
            }else if($t=='sina.com.cn'||$t=='sina.com'){
                return 'mail.sina.com.cn';
            }else if($t=='tom.com'){
                return 'mail.tom.com';
            }else if($t=='yahoo.com.cn'||$t=='yahoo.cn'){
                return 'mail.cn.yahoo.com';
            }else if($t=='tom.com'){
                return 'mail.tom.com';
            }else if($t=='yeah.net'){
                return 'www.yeah.net';
            }else if($t=='21cn.com'){
                return 'mail.21cn.com';
            }else if($t=='hotmail.com'){
                return 'www.hotmail.com';
            }else if($t=='sogou.com'){
                return 'mail.sogou.com';
            }else if($t=='188.com'){
                return 'www.188.com';
            }else if($t=='139.com'){
                return 'mail.10086.cn';
            }else if($t=='189.cn'){
                return 'webmail15.189.cn/webmail';
            }else if($t=='wo.com.cn'){
                return 'mail.wo.com.cn/smsmail';
            }else if($t=='139.com'){
                return 'mail.10086.cn';
            }else {
                return '';
            }
        };
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    var handleRegister = function() {
        function format(state) {
            if (!state.id) return state.text; // optgroup
            return "<img class='flag' src='../../assets/global/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
        }

        if (jQuery().select2) {
	        $("#select2_sample4").select2({
	            placeholder: '<i class="fa fa-map-marker"></i>&nbsp;Select a Country',
	            allowClear: true,
	            formatResult: format,
	            formatSelection: format,
	            escapeMarkup: function(m) {
	                return m;
	            }
	        });


	        $('#select2_sample4').change(function() {
	            $('.register-form').validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
	        });
    	}
        $("#register_in_button").click(function() {
           	var userId=$("#register_user_id").val();
    		var passwd=$("#register_password").val();
    		var userName=$("#register_full_name").val();
    		var Email=$("#email").val();
    		if(checkregisterin()==true){
    			Record.giveRecord(userId,passwd,userName,Email);
            	Page.turntologin();
    		}
        	
        	});
        
        var checkregisterin=function(){
        	var tag=true;
        	var Email=$("#email").val();
        	if(($("#register_user_id").val()=="") 
        		|| ($("#register_password").val()=="")
        			|| ($("#register_password_again").val()=="" )
        				|| ($("#register_full_name").val()=="" )
        					|| ($("#email").val()=="")){
        		alert("仍有信息未填写！");
        		tag=false;
        	}
        	else{
        		if($("#register_password").val()!=$("#register_password_again").val()){
        			alert("两次密码不一致！");
        			tag=false;
        		}
    		}
        	
    		return tag;
        	
        }
    
        var checkInput=function(){
        	var userId=$("#register_user_id").val();
        	var userIdPattern = /^[a-zA-Z0-9_]{1,}$/;
        	if (!register_user_id.value.match(userIdPattern)) {
        		var msg = "用户名只能由字母数字下划线组成\n";
        		alert(msg);
        	}
        };
        $(".register-form").submit(function(e){
        	if(checkInput()){
        		return false;
        	}else{return true;}
        });
        $('.register-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                fullname: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                address: {
                    required: false
                },
                city: {
                	required: false
                },
                country: {
                	required: false
                },

                username: {
                    required: true
                },
                password: {
                    required: true
                },
                rpassword: {
                    equalTo: "#register_password"
                },

                tnc: {
                    required: true
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
                tnc: {
                    required: "请先阅读并同意我们的服务条款。"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   

            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
                    error.insertAfter($('#register_tnc_error'));
                } else if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },

            submitHandler: function(form) {
                form.submit();
            }
        });

        $('.register-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.register-form').validate().form()) {
                    $('.register-form').submit();
                }
                return false;
            }
        });

        jQuery('#register-btn').click(function() {
            jQuery('.login-form').hide();
            jQuery('.register-form').show();
        });

        jQuery('#register-back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.register-form').hide();
        });
       
    	var getTicketStatus=function(){
    		$.post("../../user_login_servlet_action?action=get_ticket_status&module=teach",function(data){
    			var json=eval("("+data+")");
    			if(json.result_code==0){
    				Metronic.startPageLoading({message: '登陆成功，正在跳转主界面中，请稍候...'});	//开始等待动画
    				window.location.href=json.redirect_url;
    			}else{
    				if(json.result_code>=10 && json.result_code<=12){
    					if(confirm("登陆发生错误："+json.result_msg)){
    						window.location.href=json.redirect_url;}else{
    							$('#qrcode-back-btn').click();
    					}
    				}else{
        				getStatusTimer=setTimeout(getTicketStatus,2000);
    				}
    			}
    		});
    	}
    }
    var setGlobalSessionConfig=function(){
    	$.post("../../user_login_servlet_action?action=set_global_session_config",function(data){
    		var json=eval("("+data+")");
    		if(json.result_code==0){
    		}else{
    			alert(json.result_msg);
    		}
    	});
    }
    return {
        //main function to initiate the module
        init: function() {
            handleLogin();
            handleForgetPassword();
            handleRegister();
            setGlobalSessionConfig();
        }

    };

}();
/* ================================================================================ */
//关于页面的控件生成等操作都放在Page里，和Record独立，Record主要是和记录集交互
var Page = function() {
	var html="";
	var initPageStyle = function() {
	};
	var processError=function(json){
	};
	
	var help=function(){
		var strUrl=location.pathname;
		window.open('../../help/online/new_win_help.jsp?page_url='+strUrl, 'big', 'fullscreen=yes');
	};
	var turntoindex=function(userId,userName,role){
		window.location.href="../../home/main/index.jsp?user_role="+role+"&user_id="+userId+"&user_name="+userName;
	};
	var serviceRule=function(){
		window.open("");
	};
	
	var turntologin=function(){
		alert("注册成功！请返回页面登录");
		window.location.href="../../home/main/login.jsp";
	}
	var privateRule=function(){
		
	};
	return {
		init: function() {
			initPageStyle();
		},
		processError:function(json){
			processError(json);
		},
		help:function(){
			help();
		},
		turntoindex:function(userId,userName,role){
			turntoindex(userId,userName,role);
		},
		turntologin:function(){
			turntologin();
		},
		serviceRule:function(){
			
		},
		
		privateRule:function(){
			
		}
	}
}();//Page
/*================================================================================*/
var Record = function() {
	var userId=undefined;
	var userName=undefined;
	var userRole=undefined;
	var userAvatar=undefined;
	var initRecordStyle = function() {
	};
	var initRecordList = function(){
		getRecord();
	}
	var getRecord = function(){
		Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
		var id=$("#id").val();
		var existResultset=$("#exist_resultset").val();
		var url="../../user_login_servlet_action?action=get_record&id="+id+"&exist_resultset="+existResultset;
		$.post(url,function(json){
			if(json.result_code==0){
				Record.userId=json.user_id;
				Record.userName=json.user_name;
				Record.userRole=json.user_role;
				Record.userAvatar=json.user_avatar;
				//Page.showResult(json);
			}else{
				if(Page!=null){
					Page.processError(json);
				}
			}
			Metronic.stopPageLoading();	//停止等待动画
		}).error(function(xhr,errorText,errorType){
	        alert('错误信息：'+errorText+",错误类型："+errorType);
	    });
	};
	var giveRecord=function(userId,passwd,userName,Email){
		//待完成
		var url="../../user_login_servlet_action?action=add_record" +
				"&register_user_id="+userId+"&register_passwd="+passwd+
				"&register_full_name="+userName+"&email="+Email;
		//alert(url);
		$.post(url,function(data){
			//alert("注册成功！");
		});
		
	};
	
	var CHECKUSER=function(userId,passWd){
		var url="../../user_login_servlet_action?action=get_record&user_account="+userId;
		//alert(url);
		$.post(url,function(data){
			//alert(data);
			var json=eval("("+data+")");
	        var list=json.aaData;
			//alert(list[0].user_name);
	        if((list[0].user_account==""))
				alert("账号不存在！");
			else
			{
				if(passWd==list[0].passwd){
					var role="";
					var userId=list[0].user_account;
					var userName=list[0].user_name;
					
					if(list[0].user_role=="normal")
						role="normal";
					else
						role="manager";
					alert("登陆成功！");
					
					Page.turntoindex(userId,userName,role);
					}
				else
					alert("密码错误！");
			}
		});
	}
	var querypasswd=function(userId,Email){
		var url="../../user_login_servlet_action?action=get_record&user_account="+userId;
		//alert(url);
		$.post(url,function(data){
			//alert(data);
			var json=eval("("+data+")");
	        var list=json.aaData;
			//alert(list[0].user_name);
			if((list[0].user_account==""))
				alert("账号不存在！");
			else
			{
				if(Email==list[0].email)
					alert("验证成功！您的密码是："+list[0].passwd);
			}
		});
	}
	
	var search=function(){
		page_form.submit();
	}
	return {
		init: function() {
			initRecordList();
			initRecordStyle();
		},
		
		CHECKUSER:function(userId,passWd){
			console.log("Recoed的CHECKUSER入口："+userId+",检查依据："+passWd);
			CHECKUSER(userId,passWd);
		},
		check:function(){
			check();
		},
		giveRecord:function(userId,passwd,userName,Email){
			giveRecord(userId,passwd,userName,Email);
		},
		querypasswd:function(userId,Email){
			querypasswd(userId,Email);
		},
		search:function(){
			search();
		}
	};
}();//Record;
/* ================================================================================ */
//关于页面的控件生成等操作都放在Page里，和Record独立，Record主要是和记录集交互
