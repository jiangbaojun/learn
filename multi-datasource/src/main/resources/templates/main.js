var INDEXModel = INDEXModel || {};
//菜单根节点sjqxdm
var menuRootId = "T1";
var navWindow = null;
var initPageData = {
	demoTitle: "上中下布局示例",
	demoHref: "demo1/index",
	menuTitle: "导航",
	menuHref: "basic/menu/index"
}
$(function() {
  INDEXModel = {
	/**
	 * 初始化主页
	 */
	init: function(options) {
		var self = this;
		self.options = options || {};
		self.refreshServerTime();
		self.getDmList();
		self.initListener();
//		self.initMessageServices();
		//用户个性化设置初始化
		userSetFunc.init();
	},
	/**
	 * 全屏切换
	 */
	toggleFullscreen: function(){
		//判断全屏打开模式
		if(VSTOptions.custom.system.fullScreenOpen){
			if($("body").mrkFullScreen()){
				$("body").mrkFullScreen(false);
			} else{
				$("body").mrkFullScreen(true);
			}
		}
	},
	/**
	 * 定义事件监听
	 */
	initListener: function() {
		var self = this;
//		tabs标签页关闭鼠标右键事件绑定
		self.tabClose();
//		监听全局点击事件
		window.addEventListener("globalClick",function(e){
			//原始点击事件
			var originEvent = e.detail.originEvent;
			//点击其他地方，关闭bt的dropmenu
			if($(originEvent.target).closest($("[data-toggle=dropdown]")).length<1){
				$(".dropdown").removeClass("open");
			}
			if($(originEvent.target).closest($(".dropdown-panel")).length<1 && $(originEvent.target).closest($(".dropdown-panel-btn")).length<1){
				$(".dropdown-panel").hide();
			}
		});
//		退出系统
		$('#loginOut').click(function() {
			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
				if (r) {
					location.href = basePath+'basic/login/logout';
				}
			});
		});
//		修改密码
		$('#userChangePassword').click(function() {userSet(1);});
//		修改主题
		$('#userChangeTheme').click(function() {userSet(2);});
//		修改字体
		$('#userChangeFont').click(function() {userSet(3);});
		//保存当前激活的标签（当前所处的模块）
		$('#tabs').tabs({
			'onSelect':function(title,index){
				var iframe = $('#tabs').tabs('getTab',index).find(">iframe");
				if(iframe!=null&&iframe.length>0){
					var url = iframe.attr("src");
					if(url!=null){
						var selectedMenu = {url:url};
						var menu = self._getQxiddByUrl(url);
						if(menu && menu["qxdm"]){
							selectedMenu = menu;
						}
						//保存当前模块信息
						$.ajax({
							url: 'basic/main/saveModuleId',
							data: {"qxId": menu["qxdm"]}
						});
						//触发模块改变通知事件
						var ifs = document.getElementsByTagName("iframe");
						if(ifs.length>0){
							for(var i=0;i<ifs.length;i++){
								var win = ifs[i].contentWindow;
								Common.dispatchEvent(win,"tabChange",selectedMenu);
							}
						}
					}
				}
			}
		});
//		鼠标右键-刷新
		$('#mm-tabupdate').click(function(){
			var currTab = $('#tabs').tabs('getSelected');
			var url = $(currTab.panel('options').content).attr('src');
			var tempParam = $(currTab.panel('options').content).attr('tempParam');
			if(url != undefined) {
				//处理url参数
				if(tempParam && url.indexOf("?")>0){
					url = url.split("?")[0];
				}
				$('#tabs').tabs('update',{
					tab:currTab,
					options:{
						content:self._createFrame(url)
					}
				});
			}
		})
//		鼠标右键-关闭当前
		$('#mm-tabclose').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('#tabs').tabs('close',currtab_title);
		})
//		鼠标右键-全部关闭
		$('#mm-tabcloseall').click(function(){
			$('.tabs-inner span.tabs-closable').each(function(i,n){
				var t = $(n).text();
				$('#tabs').tabs('close',t);
			});
		});
//		鼠标右键-关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function(){
			var curTab = $('#mm').data("currtab");
			$('.tabs-inner span.tabs-closable').each(function(i,n){
				var t = $(n).text();
				if(t!=curTab) {
					$('#tabs').tabs('close',t);
				}
			});
		});
//		鼠标右键-关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function(){
			var curTab = $('#mm').data("currtab");
			var nextall = $('.tabs-selected').nextAll();
			if(nextall.length==0){
				//msgShow('系统提示','后边没有啦~~','error');
				$('#mm').menu('hide');
				return false;
			}
			nextall.each(function(i,n){
				var t=$(n).find("span.tabs-closable").text();
				$('#tabs').tabs('close',t);
			});
			$('#tabs').tabs('select',curTab);
		});
//		鼠标右键-关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function(){
			var curTab = $('#mm').data("currtab");
			var prevall = $('.tabs-selected').prevAll();
			if(prevall.length==0){
				$('#mm').menu('hide');
				return false;
			}
			prevall.each(function(i,n){
				var t=$(n).find("span.tabs-closable").text();
				$('#tabs').tabs('close',t);
			});
			$('#tabs').tabs('select',curTab);
		});
	},
	//初始化消息服务(测试)
	initMessageServices: function(){
		var socket =  io.connect('http://localhost:9092');
		socket.on('connect', function() {
			console.log("connected to the server!");
		});

		socket.on('chatevent', function(data) {
			console.log(data.userName + ':' + data.message);
		});

		socket.on('disconnect', function() {
			console.log("has disconnected!");
		});
		setTimeout(function(){
			var jsonObject = {userName: "VSTA", message: "大家好，我是VSTA"};
			socket.emit('chatevent', jsonObject);
		}, 1000);
	},
	/**
	 * 获得字典表数据
	 */
	getDmList: function(){
		var self = this;
		$.ajax({
			url : 'basic/main/dmList',
			mask:false,
			success : function(data) {
				if(Common.isString(data)){
					try{
						data = JSON.parse(data);
					}catch (e) {}
				}
				INDEXModel.dmList = dmList = data;
				//处理远程服务器时间
				dmList["serverTimeDeviation"] = dmList.serverTime - new Date().getTime();
				dmList["serverTime"]  = new Date(dmList["serverTime"]);
				//初始化菜单搜索功能
				self.initAutoComplete();
				//初始化导航菜单
				self.initNavigateMenu();
				//初始化导航页面
				// self.loadMenuPage();
				//初始化示例页面
				self.loadDemoPage();
			}
		});
	},
	//更新服务器时间显示
	refreshServerTime: function(){
		window.setInterval(function(){
			var date = new Date(dmList["serverTimeDeviation"]+new Date().getTime());
			dmList["serverTime"]  = date;
			$("#serverDate").text(date.format("yyyy-MM-dd"));
			$("#serverTime").text(date.format("HH:mm:ss"));
		},1000);
	},
	//初始化员工列表示例页面
	loadDemoPage: function(){
		var self = this;
		self._addTab(initPageData.demoTitle, initPageData.demoHref);
	},
	//载入menu导航页
	loadMenuPage: function(){
		var self = this;
		self._addTab(initPageData.menuTitle, initPageData.menuHref, {closable: false,nameAttr: "navMenuTab"});
	},
	/**
	 * 初始化导航菜单
	 */
	initNavigateMenu: function() {
		var self = this;
		var treeSelector = "#menuTree";
		var menuData = dmList.menu;
		$(treeSelector).empty();
		$(treeSelector).mrkMenu({
            menuData:menuData,
            menuRootId: menuRootId,
			showIcon: true,
            onlyUnfold: true,
            remberSonFoldState: true,
            onComplete:function(data){
            	//初始化滚动条（只是对滚动条做优化，非必须）
            	$(treeSelector).mCustomScrollbar({
        			theme:"vst-dark",
        			axis:"y",
        			mouseWheel:{ preventDefault:true }
        		});
            },
            onClickContextMenu:function(data){
            	console.log(data);
            	if(data.isModule){
            		var event = data.e;
            		var menuid = $(event.currentTarget).attr("menuid");
            		//判断是否收藏
            		if(NavMenu.getNavWindow().INDEXModel.hasFavorited(menuid)){
            			$('#collectMenu').menu('disableItem',$("#favorite").get(0));
            			$('#collectMenu').menu('enableItem',$("#unFavorite").get(0));
            		}else{
            			$('#collectMenu').menu('enableItem',$("#favorite").get(0));
            			$('#collectMenu').menu('disableItem',$("#unFavorite").get(0));
            		}
            		$('#collectMenu').menu('show', {
            			left: event.pageX,
            			top: event.pageY
            		});
            		$("#collectMenu").data("favorite",menuid);
            	}
            },
            onClickMenu: function(data){
            	if(data.isModule){
                    var li = this;
                    var href = data.data.url || li.find(">a").attr('href');
                    var separator = "?";
                    if(href.search(/\?/)>0){
                        separator = "&";
                    }
                    var params = separator+"qxdm="+data.data.qxdm+"&qxmc="+encodeURIComponent(data.data.qxmc);
                    href = href+params;
                    var title = data.data.qxmc || li.find(">a>span.menu-title").text();
                    INDEXModel._addTab(title, href);
            	}
        	}
        });
	},
	/**
	 * 根据菜单id判断菜单是否已收藏
	 */
	hasFavorited: function(menuid){
		var existFlag = false;
		for(var i=0;i<dmList.favoriteMenu.length;i++){
			if(dmList.favoriteMenu[i].qxdm == menuid){
				existFlag = true;
				break;
			}
		}
		return existFlag;
	},
	/**
	 * 初始化菜单搜索
	 */
	initAutoComplete: function(){
		var self = this;
        var nav = $('#navText');
		var menuData = dmList.menu;
        nav.autocomplete({
            lookup: self._formatAutoCompleteData(menuData, ""),
            minChars: 1,
            right: 50,
            triggerSelectOnValidInput: false,
            showNoSuggestionNotice: true,
            noSuggestionNotice: "没有匹配菜单",
            groupBy: 'category',
            onSelect: function (suggestion) {
            	if(Common.isNotNull(nav.val())){
            		//console.log(suggestion);
            		var title = suggestion.data.qxmc;
            		var href = suggestion.data.url;
            		var separator = "?";
            		if(href.search(/\?/)>0){
            			separator = "&";
            		}
            		href = href+separator+"qxdm="+suggestion.data.qxdm+"&qxmc="+encodeURIComponent(suggestion.data.qxmc);
            		self._addTab(title, href);
            	}
            }
        });
	},
	/**
	 * 处理菜单数据，转化为菜单搜索控件可用的格式数据
	 */
	_formatAutoCompleteData: function(menuData, searchText){
		var self = this;
        if(searchText==null || searchText==undefined){
            return menuData;
        }
        var filterData = [];
        var reg = new RegExp(searchText);
        //获得所有直接节点数组
        for(var i=0;i<menuData.length;i++){
            var item = menuData[i];
            var subVal = self._getCrumbs(menuData,item.sjqxdm,"/");
            if((reg.test(item.qxmc) || searchText=="") && !self._hasChildren(menuData,item.qxdm)){
                filterData.push({"value":item.qxmc,subValue:subVal, "data":item});
            }
        }
        return filterData;
    },
    /**
     * 判断某菜单节点是否有子节点
     */
	_hasChildren: function(menuData,qxdm){
		for(var i=0;i<menuData.length;i++){
			var item = menuData[i];
			if(item.sjqxdm == qxdm){
				return true
			}
		}
		return false;
	},
	/**
	 * 根据菜单层级关系，获得模块面包屑
	 */
	_getCrumbs: function(menuData, sjqxdm, separator, result){
		var hasParent = false;
		if(!result) result="";
		var self = this;
		separator = separator?separator:"/";
		if(sjqxdm != menuRootId && sjqxdm){
			for(var i=0;i<menuData.length;i++){
	    		var item = menuData[i];
	    		if(item.qxdm == sjqxdm){
	    			result = item.qxmc+separator+result;
	    			sjqxdm = item.sjqxdm;
	    			hasParent = true;
	    		}
	    	}
			//防止stack溢出，最大300长度
			if(result.length>300){
				return;
			}
			if(hasParent){
				return self._getCrumbs(menuData,sjqxdm,separator,result);
			}
		}
		return result;
    },
    /**
     * 获得当前模块信息
     * @param win iframe内window对象
     */
    whoami: function(win){
    	var self = this;
    	if(win && win.location){
    		var url = win.location.pathname;
    		//去掉contexpath
    		url = url.replace(url.split(new RegExp("(/[^/]+)"))[1],"");
    		if(url!=null){
    			var selectedMenu = {url:url};
    			var menus = dmList.menu;
    			for(var i=0;i<menus.length;i++){
    				var menu = menus[i];
    				var menuUrl = menu.url;
    				if(self._ctrlUri(url)==self._ctrlUri(menuUrl)){
    					return menu;
    				}
    			}
    		}
    	}
    },
	/**
	 * 添加tabs面板，存在刷新，不存在添加
	 * title	标签名称(title)
	 * href		标签地址(url)
	 * options	其他可选项
	 * 	closable	标签是否可以被关闭
	 * 	tempParam	href后面的参数是否为临时（true：仅在第一次打开时有效，刷新后失效。默认false）
	 * 	nameAttr	标签内部iframe标记的name属性值
	 * 	classAttr	标签内部iframe标记的class属性值
	 *
	 */
	_addTab:function(title,href,options) {
		var self = this;
		var closable=true;
		var nameAttr=null;
		var classAttr=null;
		var tempParam=false;
		if(Common.isObject(options)){
			closable = options.closable;
			nameAttr = options.nameAttr;
			classAttr = options.classAttr;
			tempParam = options.tempParam;
		}
		if(Common.isNotValid(closable)){
			closable=true;
		}
		if ($('#tabs').tabs('exists', title)){
			$('#tabs').tabs('select', title);//选中并刷新
			var currTab = $('#tabs').tabs('getSelected');
			var url = $(currTab.panel('options').content).attr('src');
			if(href != url){
				url = href;
			}
			var content = self._createFrame(url,{nameAttr:nameAttr,classAttr:classAttr,tempParam:tempParam});
			var menu = self._getQxiddByUrl(url);
			//保存当前模块信息
			$.ajax({
				url: 'basic/main/saveModuleId',
				data: {"qxId": menu["qxdm"]},
				success: function(){
					//刷新模块
					$('#tabs').tabs('update',{
						tab:currTab,
						options:{
							content:content
						}
					});
				}
			});
		} else {
			var content = self._createFrame(href,{nameAttr:nameAttr,classAttr:classAttr,tempParam:tempParam});
			var menu = self._getQxiddByUrl(href);
			//保存当前模块信息
			$.ajax({
				url: 'basic/main/saveModuleId',
				data: {"qxId": menu["qxdm"]},
				success: function(){
					//打开新模块
					$('#tabs').tabs('add',{
						title:title,
						closable:closable,
						content:content
					});
				}
			});
		};
	},
	/**
	 * 显示已打开的标签，并调用打开iframe内的指定方法
	 */
	tabShow: function(title, func) {
		var $iframe = $(".easyui-tabs").tabs("getTab",title).find(">iframe");
		if($iframe && $iframe.length==1){
			$(".easyui-tabs").tabs("select",title);
			var win = $iframe.get(0).contentWindow;
			try {
				win[func]();
			} catch (e) {}
		}
	},
	/**
	 * 添加tabs鼠标右键菜单、双击关闭监听事件
	 */
	tabClose: function() {
		/*双击关闭TAB选项卡*/
		$(".easyui-tabs").on("dblclick", ".tabs-inner", function(){
			var subtitle = $(this).children(".tabs-closable").text();
			$('#tabs').tabs('close',subtitle);
		})
		/*为选项卡绑定右键*/
		$(".easyui-tabs").on('contextmenu',".tabs-inner", function(e){
			if($(this).find(".tabs-closable").length>0){
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}
			var subtitle =$(this).children(".tabs-title").text();
			$('#mm').data("currtab",subtitle);
			$('#tabs').tabs('select',subtitle);
			return false;
		});
	},
	/**
	 * 创建tabs面板内容，本工程采用iframe方式进行添加
	 * url 地址
	 * options 可选项
	 * 	tempParam	href后面的参数是否为临时（true：仅在第一次打开时有效，刷新后失效。默认false）
	 * 	nameAttr	标签内部iframe标记的name属性值
	 * 	classAttr	标签内部iframe标记的class属性值
	 */
	_createFrame:function(url,options) {
		var nameAttr=null;
		var classAttr=null;
		var tempParam=false;
		if(Common.isObject(options)){
			nameAttr = options.nameAttr;
			classAttr = options.classAttr;
			tempParam = options.tempParam;
		}

		var attr = "";
		if(nameAttr){
			attr += "name=\""+nameAttr+"\" ";
		}
		if(classAttr){
			attr += "class=\""+classAttr+"\" ";
		}
		if(tempParam){
			attr += "tempParam=\"true\" ";
		}
		var s = '<iframe '+attr+' scrolling="auto" allowfullscreen="true" frameborder= "0" src="'+basePath+url+'" style="width:100%;height:100%;"></iframe>';
		return s;
	},
	/**
	 * 处理uri
	 */
	_ctrlUri: function(uri){
		if(uri){
			uri = uri.split("?")[0];
			if(!uri.startsWith("/")){
				uri = "/"+uri;
			}
		}
		return uri;
	},
	/**
	 * 根据url获得资源信息
	 */
	_getQxiddByUrl: function(url){
		var self = this;
		var menus = dmList.menu;
		for(var i=0;i<menus.length;i++){
			var menu = menus[i];
			var menuUrl = menu.url;
			if(self._ctrlUri(url)==self._ctrlUri(menuUrl)){
				selectedMenu = menu;
				return menu;
			}
		}
		return {};
	}
 };
 INDEXModel.init();

 //菜单收藏
 var NavMenu = {
	init: function(){
		//收藏右键菜单
		var self = this;
		$('#collectMenu').menu({
			onClick: function(item){
				var menuid = $(this).data("favorite");
				self.getNavWindow().INDEXModel.contentClick(menuid, item.id);
				$(this).data("favorite",null);
			}
		});
	},
	//获得菜单导航窗口
	getNavWindow: function(){
		return document.getElementsByName("navMenuTab")[0].contentWindow;
	}
 }
 NavMenu.init();
});
