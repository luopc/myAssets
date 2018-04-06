<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%
	String webApp=request.getContextPath();
 %>
 <!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>后台登录</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link href="${stx }/H+admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="${stx }/H+admin/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${stx }/H+admin/css/animate.css" rel="stylesheet">
    <link href="${stx }/H+admin/css/style.css" rel="stylesheet">
    <link href="${stx }/H+admin/css/login.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>

</head>

<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
                <div class="signin-info">
                    <div class="logopanel m-b">
                        <h1>[ H+ ]</h1>
                    </div>
                    <div class="m-b"></div>
                    <h4>欢迎使用 <strong>H+ 后台主题UI框架</strong></h4>
                    <ul class="m-b">
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势一</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势二</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势三</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势四</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势五</li>
                    </ul>
                    <strong>还没有账号？ <a href="#">立即注册&raquo;</a></strong>
                </div>
            </div>
            <div class="col-sm-5">
                <form method="post" name="loginForm" method="post" action="${ctx}/login/loginCheck.do" >
                    <h4 class="no-margins">登录：</h4>
                    <p class="m-t-md">登录到H+后台主题UI框架</p>
                    <input type="text" name="loginName" id="loginName"  class="form-control uname" placeholder="用户名" />
                    <input name="password"  id="loginPass" type="password" class="form-control pword m-b" placeholder="密码" />
                    <a href="">忘记密码了？</a>
                    <button class="btn btn-success btn-block" lay-filter="login" >登录</button>
                </form>
            </div>
        </div>
        <div class="signup-footer">
            <div class="pull-left">
                &copy; 2015 All Rights Reserved. H+
            </div>
        </div>
    </div>
</body>
<script src="${stx}/layui/layui.js" charset="utf-8"></script>
<script src="${stx}/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${stx}/jquery/jquery.cookie.js" type="text/javascript" ></script>
<script src="${stx}/CryptoJS/md5.js" type="text/javascript" ></script>
<script>
$(document).ready(function(){
    var loginName = $.cookie("loginName");
    if(username){
        $("#loginName").val(username);
        $("#remembName").attr("checked",true);
    }
});
function enterHandler(event){
    var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
    if (keyCode == 13) {
        login();
    }
}
layui.use(['form', 'layedit', 'laydate'], function() {
    var form = layui.form(),
        layer = layui.layer;
    //自定义验证规则
    form.verify({
        title: function(value) {
            if(value.length < 5) {
                return '用户名必须5个字符啊';
            }
        },
        password: [/(.+){6,12}$/, '密码必须6到12位']   
    });    
    //监听提交
    form.on('submit(login)', function(data) {        
        return true;
    });

});
</script>
</html>