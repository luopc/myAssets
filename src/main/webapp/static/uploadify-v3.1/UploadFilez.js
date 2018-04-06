/**
 * 上传组件通用组件 使用该JS必须引入jquery-1.4.2.js，jquery.uploadify-3.1.js
 */
(function($) {
	/** action */
	var attHandler = {	
		onInit : function(){	//初始化列表			var $this = this;
			$.ajax({
			    url:$this.datalisturl,
			    type: 'POST',
			    data: jQuery.param($this.eParam),
			    dataType: 'json',			    
			    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			    error: function(data){
			    },
			    success: function(data){	
			    	$this.addData(data);
			    }
			});
		},
		addData : function(data) {  //动态生成数据列表			var $this = this;
			$.each(data, function(index, dataObj) {				
				var chkTd=""; 
				var aEl = "a_"+$this.elId+"_"+dataObj.uuid;
				if(!$this.readOnly)chkTd="<td><input type='checkbox' name='"+$this.ckId+"' value='"+dataObj.uuid+"' /></td>";
	    		$('#'+$this.listBodyId).append($("<tr>"+chkTd+"<td  width='20'>"+($this.cursor++)+"</td><td class='uploadify-list-title'><a href='javascript:void(0);' id='"+aEl+"'>"+dataObj.name+"</a></td></tr>"));
	    		$('#'+aEl).click(function() {
	    			$this.downloadFile(dataObj.uuid);
	    		}); 
	    		$this.count ++;
			});
		},
		downloadFile : function (uuid){	//下载附件
			var $this = this;
			var url = $this.downloader+"?attUuid="+uuid+"&etc="+new Date().getTime();
			downloadFrame.src=url;
		},
		deleteFile : function(){	//删除附件
			var $this = this;
			var selIdArr = [];
			$("input[name='"+$this.ckId+"']:checked").each(function(i){				
				if($(this).val()!=""){
					selIdArr.push($(this).val());
				}
			}); 
			var selIds = selIdArr.join(",")
			if(selIds=='')alert('请选择要删除的附件。');
			$.ajax({
			    url:$this.remover,
			    type: 'POST',
			    data: 'attUuids='+selIds,
			    dataType: 'json',
			    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			    error: function(data){
			    },
			    success: function(data){	
			    	if(data=="true"){			    		
				    	$("input[name='"+$this.ckId+"']:checked").each(function(i){
				    		$(this).parent().parent().remove();
				    		$this.count --;
						}); 
			    	}else{
			    		alert("删除失败。");
			    	}
			    }
			});
		}
	}	
	/** init */
	var downloadFrame ;
	$.fn.UploadFile = function(settings){
		var $this = this;	
		this.cursor = 1 ;
		this.elId = $(this).attr('id');
		this.ckId = "chk_"+this.elId
		this.listBodyId = "tbody_"+this.elId
		//必要属性
		this.basePath = settings.basePath ;	//项目根路径		this.fileid=settings.fileid;	//源文件ID
		this.swf = (!settings.swf)?this.basePath+"/pages/pc/scripts/jquery/uploadify-v3.1/uploadify.swf":settings.swf; //swf路径
		this.uploader = (!settings.uploader)?this.basePath+"/pages/DocAttach/upload.do":settings.uploader;//上传action
		this.downloader = (!settings.downloader)?this.basePath+"/pages/DocAttach/download.do":settings.downloader;	//下载acton
		this.datalisturl = (!settings.swf)?this.basePath+"/pages/DocAttach/list.do":settings.swf;	//列表acton
		this.remover = (!settings.remover)?this.basePath+"/pages/DocAttach/delete.do":settings.remover; //删除action
		//可选属性		this.multi = settings.multi==false?settings.multi:true;	//是否同时上传多个
		this.fileSizeLimit = (!settings.fileSizeLimit)?'100MB':settings.fileSizeLimit;	//上传文件大小限制
		this.buttonText = settings.buttonText?settings.buttonText:'添加附件';	//按钮文字
		this.auto = settings.auto==false?settings.auto:true;	//是否自动上传
		this.uploadLimit = (!settings.uploadLimit)?999:settings.uploadLimit;	//同时上传的数量		this.showDel=settings.showDel==false?settings.showDel:true;;	//是否显示删除
		this.showDownload=settings.showDownload==true;	
		this.width=settings.width?settings.width:100;		//按钮长度
		this.readOnly = settings.readOnly==true;		//是否只读
		this.fileTypeExts = (!settings.fileTypeExts)?'*.*':settings.fileTypeExts;	//上传的文件类型		this.fileType = (!settings.fileType)?'ATT':settings.fileType ;	//附件类型
		this.countLimit = 999;	//附件数量限额
		if(!isNaN(settings.countLimit) && parseInt(settings.countLimit)>0)this.countLimit=parseInt(settings.countLimit); 
		this.eParam = {'fileid':this.fileid,'filetype':this.fileType};		
		//method
		this.addData = attHandler.addData;
		this.downloadFile = attHandler.downloadFile;
		this.deleteFile = attHandler.deleteFile;
		this.onInit = attHandler.onInit;
		this.count = 0;
		this.multiFile = settings.multiFile==true?true:false;	//多源文件
		if(this.multiFile)this.uploader += "?multiFile="+this.multiFile ;
		this.onSelect = settings.onSelect ;
		this.onUploadSuccess = settings.onUploadSuccess ;
		//add header
		this.checkTh="";
		if(!this.readOnly)this.checkTh="<th width='30'><input type='checkbox' name='"+this.ckId+"_all' onclick='$.upHandler.checkAllAtt(this,\""+this.ckId+"\");'/></th>";
		$(this).after("<div class='upload-list'><table width='100%' border='0' cellpadding='0' cellspacing='0' class='upload-table'>"+
				"" +
				"<tbody id='"+this.listBodyId+"'></tbody></table></div>");
		var selfEl = $(this);
		$(this).uploadify({
		   	'auto' : this.auto,
		   	'buttonText' : this.buttonText,
		   	'multi' : this.multi,
		   	'removeTimeout' : 1,
		   	'formData' : this.eParam,
		    'swf' 	   : this.swf,
		    'uploader' : this.uploader,
		    'fileSizeLimit' : this.fileSizeLimit,  
		    'width' : this.width,
		    'fileTypeExts' : this.fileTypeExts ,
		    'uploadLimit' : this.uploadLimit,
		    'onUploadSuccess' : function(file, data, response) {
		   		if(data && data.indexOf('{')>-1 && data.indexOf('}')>-1){
		   			var dataObj=eval("("+data+")");
		   			$this.addData(dataObj);
		   		}
		   		if(jQuery.isFunction($this.onUploadSuccess))$this.onUploadSuccess(file, data, response);
		   	},
		    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
		    	if(errorCode!='-280')alert('文件： ' + file.name + ' 上传失败: ' + errorCode + errorString);
		    },
		    'onInit' : function(instance) {		
		    	$this.onInit();
		    },
		    'onSelect' : function(file) {
	            if($this.count>=$this.countLimit){
	            	selfEl.uploadify('cancel');
	            	alert("上传失败：超出附件数量限制。\n如果需要继续上传附件，请删除部分附件。");
	            }
	            if(jQuery.isFunction($this.onSelect))$this.onSelect(file);
	        } 
		});    
		if(this.readOnly){				
			$('#'+this.elId).hide();
		}else{
			if(this.showDel){
				$('#'+this.elId).after("<a id='del_"+this.elId+"' class='uploadify-btn' href='javascript:void(0);' >删除</a>");
				$('#del_'+this.elId).click(function() {
	    			$this.deleteFile();
	    		});
			}
			if(!this.auto)$('#'+this.elId).after("<a class='uploadify-btn' href=\"javascript:$('#"+this.elId+"').uploadify('upload','*')\">上传</a>");
		}
		//下载所用到的隐藏iframe
		if(!downloadFrame){
			var IframeObj = document.createElement("iframe");
			IframeObj.id="UPLOAD_COM_DOWNLOAD";
			IframeObj.width =0;
			IframeObj.height =0;
			IframeObj.style.display="none";
			document.body.appendChild(IframeObj);
			downloadFrame=document.getElementById("UPLOAD_COM_DOWNLOAD");
		}
	};	
	$.upHandler ={
		checkAllAtt : function(obj,ckName){
			$("input[name='"+ckName+"']:checkbox").attr('checked', obj.checked);
		}
	}
})($);