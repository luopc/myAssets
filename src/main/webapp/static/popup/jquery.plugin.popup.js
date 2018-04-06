(function($) {
    $.popup = {
        ID: null,
        title: "",
        top: 0,
        left: 0,
        width: 0,
        height: 0,
        popType: "",
        repositionOnResize: false,                 okButton: '确 定',                 
        cancelButton: '取 消',             
        isButtonRow: false,                
        isPopup: false,                    
        autoClose: 0,                      
        tip: function(msg, top, left, autoClose) {
            this.ID = 'tip';
            this.popType = 'tip';
            this.title = '';
            this.isPopup = false;
            this.autoClose = autoClose || 0;
            this.width = 240;
            this.height = 30;
            this.top = top || ($(document).height() - this.height) / 2;
            this.left = left || ($(document).width() - this.width) / 2;
            $.popup._show(null, msg, null);
        },
		error: function(elem, title, msg, height) {
            this.ID = 'error';
            this.title = title || this.title;
            this.width = 200;
            this.height = height || 40;
            var top = $(elem).offset().top;
            if (top - 60 - this.height > 0) {
                this.top = $(elem).offset().top - 60 - this.height;
                this.popType = 'error_up';
            }
            else {
                this.top = top + 16;
                this.popType = 'error_down';
            }
            this.left = $(elem).offset().left - 30;
            $.popup._show(elem, msg);
        },
        help: function(elem, title, msg, height) {
        	if(!$(elem).length==0)return;
            this.ID = 'help';
            this.title = title || this.title;
            this.width = 200;
            this.height = height || 40;
            var top = $(elem).offset().top;
            if (top - 60 - this.height > 0) {
                this.top = $(elem).offset().top - 60 - this.height;
                this.popType = 'help_up';
            }
            else {
                this.top = top + 16;
                this.popType = 'help_down';
            }
            this.left = $(elem).offset().left - 30;
            $.popup._show(elem, msg);
        },
        model: function(msg) {        	
            this.ID = 'model';
            this.popType = 'model';
            $.popup._showModel(msg);
        },
        prompt: function(elem, title, msg, isButtonRow, isPopup, callback, top, left, width, height) {
            this.ID = 'prompt';
            this.title = title || this.title;
            this.popType = 'prompt';
            this.isButtonRow = isButtonRow || this.isButtonRow;
            this.isPopup = isPopup || this.isPopup;
            if(!$(elem).length==0)return;
            this.top = top || $(elem).offset().top + 16;
            this.left = left || $(elem).offset().left;
            this.width = width || 300;
            this.height = height || 120;
            $.popup._show(elem, msg, function(result) {
                if (callback) callback(result);
            });
        },
        _show: function(elem, msg, callback) {
        	if(!$(elem).length==0)return;
            if ($("#_Popup_" + this.ID).length < 1) {
                var html =
			    '<div class="popup_' + this.popType + '" id="_Popup_' + this.ID + '" style="width:' + this.width + 'px"><div class="popup_header" id="_Title_"><h1>' + this.title + '</h1><div class="h_r"></div></div><div class="popup_content"><div id="_Container_' + this.ID + (this.height == 0 ? '">' : '" style="height:' + this.height + 'px">') + msg + '</div></div>' +
                    (this.isButtonRow ? '<div class="buttonRow" id="_ButtonRow_' + this.ID + '"></div>' : '') +
                  '<div class="popup_bottom"><div class="b_r"></div></div>';
                $("BODY").append(html);
                $("#_Popup_" + this.ID).css({
                    position: 'absolute',
                    zIndex: 9999,
                    padding: 0,
                    margin: 0
                });
                $("#_Popup_" + this.ID).css({
                    minWidth: $("#_Popup_" + this.ID).outerWidth(),
                    maxWidth: $("#_Popup_" + this.ID).outerWidth()
                });
                $.popup._reposition();
                $.popup._maintainPosition(true);
                $.popup._bindType();
                if (this.isPopup) {
                    $(elem).click(function(e) {
                        e ? e.stopPropagation() : event.cancelBubble = true;
                    });
                    $("#_Popup_" + this.ID).click(function(e) {
                        e ? e.stopPropagation() : event.cancelBubble = true;
                    });
                    $(document).click(function() {
                        $.popup._hide();
                    });
                }
                if (this.autoClose > 0) {
                    $.popup._autoClose();
                }
            }
            else {
                $("#_Container_" + this.ID).html(msg);
                $.popup._bindType(callback);
                $.popup._reposition();
                $.popup._maintainPosition(true);
                $("#_Popup_" + this.ID).show();
                if (this.autoClose > 0) {
                    $.popup._autoClose();
                }
            }
        },
        _showModel: function(msg) {    
        	if(!msg)msg="请稍候...";
            if ($("#_Popup_" + this.ID).length < 1) {
                var html =
			    '<div class="popup_' + this.popType + '" id="_Popup_' + this.ID + '" >' +
                    '<div id="popup_' + this.ID + '_content"> <p id="_popup_' + this.ID + '_p">'+msg+'</p></div></div>';
                $("BODY").append(html); 
            }
            else {
                $("#_Popup_" + this.ID).css({"display":"inline"});
                $("#_popup_" + this.ID+ "_p").attr("outerText",msg);
            }
        },
        _bindType: function(callback) {
            switch (this.popType) {
                case 'help':
                    if (this.isButtonRow) {
                        $("#_ButtonRow_" + this.ID).after('<input type="button" value="' + $.popup.okButton + '" id="_ButtonOK_' + this.ID + '" />');
                        $("#_ButtonOK_" + this.ID).click(function() {
                            $.popup._hide();
                            callback(true);
                        });
                        $("#_ButtonOK_" + this.ID).keypress(function(e) {
                            if (e.keyCode == 13 || e.keyCode == 27) $("#_ButtonOK_" + this.ID).trigger('click');
                        });
                    }
                    break;
                case 'prompt':
                    if (this.isButtonRow) {
                        $("#_ButtonRow_" + this.ID).html('<input type="hidden" id="hid_' + this.ID + '" /><input type="button" value="' + $.popup.okButton + '" id="_ButtonOK_' + this.ID + '"/><input type="button" value="' + $.popup.cancelButton + '" id="_ButtonCancel_' + this.ID + '"/>');
                        $("#_ButtonOK_" + this.ID).click(function() {
                            var val = $("#hid_" + this.ID).val();
                            $.popup._hide();
                            if (callback) callback(val);
                        });
                        $("#_ButtonCancel_" + this.ID).click(function() {
                            $.popup._hide();
                            if (callback) callback(null);
                        });
                        $("#_ButtonOK_" + this.ID + ", #_ButtonCancel_" + this.ID).keypress(function(e) {
                            if (e.keyCode == 13) $("#_ButtonOK_" + this.ID).trigger('click');
                            if (e.keyCode == 27) $("#_ButtonCancel_" + this.ID).trigger('click');
                        });
                    }
                    break;
                case 'tip':
                    break;
                default:
                    break;
            }
        },
        _hide: function() {
            if ($("#_Popup_" + this.ID).length > 0) {
                if (this.popType == "tip") {
                    $("#_Popup_" + this.ID).fadeOut(500);
                }
                else {
                    $("#_Popup_" + this.ID).remove();
                }
                $.popup._maintainPosition(false);
            }
        },
        _autoClose: function() {
            setTimeout("$.popup._hide()", this.autoClose * 1000);
        },

        _reposition: function() {
            var top = this.top || (($(document).height() / 2) - ($("#popup_container").outerHeight() / 2));
            var left = this.left || (($(document).width() / 2) - ($("#popup_container").outerWidth() / 2));
            if (top < 0) top = 0;
            if (left < 0) left = 0;
            $("#_Popup_" + this.ID).css({
                top: top + 'px',
                left: left + 'px'
            });
        },
        _maintainPosition: function(status) {
            if ($.popup.repositionOnResize) {
                switch (status) {
                    case true:
                        $(window).bind('resize', $.popup._reposition);
                        break;
                    case false:
                        $(window).unbind('resize', $.popup._reposition);
                        break;
                }
            }
        }
    };
    showLoading = function(msg, elem) {
        var loadingMsg = msg || '正在加载数据，请稍候...';
        if (elem == null) {
            $.popup.tip('<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>' +
                '<td align="center"><img src="/negp/pages/pc/scripts/jquery/popup/images/loading.gif" /> ' + loadingMsg + '</td></tr></table>', null, null, 0);
        } else {
            var middle = ($(elem).height() - 30) / 2;
            var top = $(elem).offset().top + (middle > 0 ? middle : 0);
            $.popup.tip('<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>' +
                 '<td align="center"><img src="/negp/pages/pc/scripts/jquery/popup/images/loading.gif" /> ' + loadingMsg + '</td></tr></table>', top, null, 0);
        }
    };
    hideTip = function() {
        $("#_Popup_tip").fadeOut(300);
    };
    showModel = function(msg) {    	
        $.popup.model(msg);
    };
    hideModel = function() {
        $('#_Popup_model').remove();
    };
    showTip = function(msg, elem, autoClose) {
        if (elem == null) {
            $.popup.tip('<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>' +
                '<td align="center">' + msg + '</td></tr></table>', null, null, autoClose);
        } else {
            var middle = ($("#" + elem).height() - 50) / 2;
            var top = $("#" + elem).offset().top + (middle > 0 ? middle : 0);
            $.popup.tip('<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>' +
                '<td align="center">' + msg + '</td></tr></table>', top, null, autoClose);
        }
    };
    showHelper = function(elem, title, msg, height) {
        $.popup.help(elem, title, msg, height);
    };
	showError = function(elem, title, msg, height) {
        $.popup.error(elem, title, msg, height);
    };
    showPrompt = function(elem, title, msg, isButtonRow, isPopup, callback, top, left, width, height) {
        $.popup.prompt(elem, title, msg, isButtonRow, isPopup, callback, top, left, width, height);
    };
})(jQuery);