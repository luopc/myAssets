<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%
	String webApp=request.getContextPath();
 %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台登录</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="shortcut icon" href="${stx}/X-admin/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${stx}/X-admin/css/font.css">
    <link rel="stylesheet" href="${stx}/X-admin/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="${stx}/X-admin/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${stx}/X-admin/js/xadmin.js"></script>

</head>
<body class="login-bg">    
    <div class="login">
        <div class="message">管理登录</div>
        <div id="darkbannerwrap"></div>        
        <form class="layui-form" name="loginForm" method="post" action="${ctx}/login/loginCheck.do" >
            <input type="text" name="loginName" id="loginName"  placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="password"  id="loginPass" type="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
            <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
            <hr class="hr20" >
        </form>
    </div> 
</body>
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