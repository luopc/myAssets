<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
	<title>查询数据</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<!-- jQuery -->
	<script src="${stx}/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
	<!-- bootstrap -->
	<script type="text/javascript" src="${stx}/dlshouwen-grid/dependents/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${stx}/dlshouwen-grid/dependents/bootstrap/css/bootstrap.min.css?v1.0" />
	<!--[if lt IE 9]>
	    <script src="${stx}/dlshouwen-grid/dependents/bootstrap/plugins/ie/html5shiv.js"></script>
	    <script src="${stx}/dlshouwen-grid/dependents/bootstrap/plugins/ie/respond.js"></script>
	    <![endif]-->
	<!--[if lt IE 8]>
	    <script src="${stx}/dlshouwen-grid/dependents/bootstrap/plugins/ie/json2.js"></script>
	    <![endif]-->
	<!-- font-awesome -->
	<link rel="stylesheet" type="text/css" href="${stx}/dlshouwen-grid/dependents/fontAwesome/css/font-awesome.min.css" media="all" />
	<!-- DLShouWen Grid -->
	<script type="text/javascript"
	    src="${stx}/dlshouwen-grid/dlshouwen.grid.js?v3.0"></script>
	<script type="text/javascript" src="${stx}/dlshouwen-grid/i18n/zh-cn.js"></script>
	<script type="text/javascript" src="${stx}/dlshouwen-grid/i18n/en.js"></script>
	<link rel="stylesheet" type="text/css" href="${stx}/dlshouwen-grid/dlshouwen.grid.min.css" />
	<!-- datePicker -->
	<script type="text/javascript" src="${stx}/dlshouwen-grid/dependents/datePicker/WdatePicker.js" defer="defer"></script>
	<link rel="stylesheet" type="text/css" href="${stx}/dlshouwen-grid/dependents/datePicker/skin/WdatePicker.css" />
	<link rel="stylesheet" type="text/css" href="${stx}/dlshouwen-grid/dependents/datePicker/skin/default/datepicker.css" />
	<script type="text/javascript">
        var webApp = "${ctx}";
    </script>
</head>
<body>
    <div style="padding: 10px; width: 98%; height: 98%" class="content">
       <div id="gridContainer" class="dlshouwen-grid-container"></div>
       <div id="gridToolBarContainer" class="dlshouwen-grid-toolbar-container"></div>
    </div>
</body>
<script src="${stx}/admin/layui/layui.js"></script>
<script>
var gridColumns = [
    {id:'id', title:'用户ID', type:'number', columnClass:'text-center'},
    {id:'username', title:'用户名称', type:'string', columnClass:'text-center'},
    {id:'loginname', title:'登陆名称', type:'string', columnClass:'text-center'},
    {id:'telephone', title:'电话号码', type:'string', columnClass:'text-center'}
];
$(function() {
	var dataUrl = "${ctx}/user/list.json";
    var gridOption = {
        lang: 'zh-cn',
        ajaxLoad: true,
        loadURL: dataUrl,
        exportFileName: '数据下载',
        columns: gridColumns,
        gridContainer: 'gridContainer',
        toolbarContainer: 'gridToolBarContainer',
        pageSize: 15,
        pageSizeLimit: [10, 15, 20, 30],
        tools: '',
        onGridComplete: function (grid) {
            
        }
    };
	var gridTable = $.fn.dlshouwen.grid.init(gridOption);
	gridTable.load();
});
</script>
</html>