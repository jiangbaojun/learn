$(function(){
	init();
});

/**
 * 初始化工作
 * @Date：		2018年4月25日 下午4:13:19
 * @Version		1.0
 */
function init(){
	//创建表格
	createGrid();
	//初始化监听
	initListener();

	//获得当前模块信息
	//console.log(top.INDEXModel.whoami(window));
}

/**
 * 初始化监听事件，不需要该功能删除，删除该方法
 */
function initListener(){
	//监听主页tabs改变
	window.addEventListener("tabChange",function(e){
		//console.log(e.detail);
	});
}

/**
 * 使用js初始化表格
 * @Date：		2018年4月25日 下午2:14:30
 * @Version		1.0
 */
function createGrid(){
	 $('#datagrid').vstdatagrid({
         url : basePath+"demo1/selectList",
         border: false,
         striped:true,
         pagination : true, //分页
         fit : true,		//使表格自适应
         fitColumns : true,		//使表格列宽自适应
         sortName: "WORKID",
         sortOrder: "asc",
         multiSort:false,
         idFeild : 'id',//标识、会记录我们选中项的id、不一定是id、通常使用主键
         columns : [[
            {title:'工号', field:'workId', width:100, sortable:true},
            {title:'姓名', field:'xm', width:120, sortable:true},
            {title:'手机', field:'sj', width:130, align:'right',sortable:true},
            {title:'入职日期', field:'rzrq', align:'center', width:150, sortable:true},
            {title:'职务', field :'zw', width:160, sortable:true },
            {title:'部门', field :'bm', width:160, sortable:true },
            {title:'附件', field :'fileCount', width:120, formatter:fileCount},
            {title:'操作', field :'_option', align:'center', width:130, formatter:operate},
            {title:'id', field:'id', width:100, checkbox:true}
         ]],
         onDblClickRow : function(rowIndex, rowData){
        	 dblClickRow(rowIndex, rowData);
         },onLoadSuccess:function(){
			 $('#datagrid').datagrid("loaded");
		 }
     });
}

/**
 * 双击行触发函数
 * @param rowIndex	行索引
 * @param rowData	行数据
 * @Date：		2018年4月25日 下午2:05:23
 * @Version		1.0
 */
function dblClickRow(rowIndex, rowData){
//	 $('#datagrid').datagrid('unselectAll');
//     $.messager.alert('提示', '双击'+rowIndex, 'info');
}

/**
 * 添加操作列过滤函数
 * @param value	字段值
 * @param row	行记录数据
 * @param index	行索引
 * @Date：		2018年4月25日 下午2:04:04
 * @Version		1.0
 */
function operate(value,row,index){
	//空数据（id为空的）不添加修改选项
	if(row.id!=undefined){
		var btns = "";
		btns = '<a class="datagrid-operate edit" title="编辑" href="javascript:void(0)" onclick="edit('+index+')"></a>';
		btns += '<a class="datagrid-operate remove" title="删除" href="javascript:void(0)" onclick="removeOnly('+index+')"></a>';
		btns += '<a class="datagrid-operate query" title="查看" href="javascript:void(0)" onclick="view('+index+')"></a>';
		return btns
	}
}
//查看附件
function fileCount(value,row,index){
	if(row.id!=undefined){
		return '<a href="javascript:void(0)" style="cursor:pointer;" onclick="FileModel.showFiles(\''+row.id+'\')">查看附件</a>';
	}
}


//编辑、添加的url
var url;
/**
 * 添加数据-使用easyui-dialog控件展示添加窗口
 * @Date：		2018年4月25日 下午4:19:31
 * @Version		1.0
 */
function add() {
	$("#win").dialog({
		title: "添加员工",
		cache: true,
		closed:false,
	    href: basePath+"demo1/winDialog",
    	onLoad:function(){
    		//初始化vsta相关组件，保证组件显示正常
    		VST.initWidget("#win");
    		//重置表单
    		$("#operForm").form("reset");
    		//强制重新表单校验，可选
//    		$("#operForm").form('disableValidation');
//    		$("#operForm").form('enableValidation');
    		//取消初始表单校验，为了初始不提示，可选
    		$('#operForm').form("cancelInitialValidate");
    	}
	});
	url = basePath+"demo1/saveUploadForm";
}

/**
 * 添加数据-使用vst-window控件展示添加窗口
 * @Date：		2018年5月22日 下午4:51:50
 * @Version		1.0
 */
function addFull() {
	$("#winFull").dialog({
		title: "添加员工",
		cache: true,
		closed:false,
	    href: basePath+"demo1/winFullDialog",
    	onLoad:function(){
    		VST.initWidget("#winFull");
    		$("#operFormFull").form("reset");
    		$('#operFormFull').form("cancelInitialValidate");
    	}
	});
	url = basePath+"demo1/saveUploadForm";
}

/**
 * 编辑数据
 * @param index	待编辑数据的索引
 * @Date：		2018年4月25日 下午4:19:44
 * @Version		1.0
 */
function edit(index) {
	$("#operForm").form("reset");
	var row = $("#datagrid").datagrid("getRow", index);
	if(!row){
		return;
	}
	//打开窗口
	$("#win").dialog({
		title: "修改员工信息",
		cache: true,
		closed:false,
	    href: basePath+"demo1/winDialog",
    	onLoad:function(){
    		//初始化vsta相关组件，保证组件显示正常
    		VST.initWidget("#win");
    		$.ajax({
    			url : basePath+'demo1/edit?id=' + row.id,
    			mask:true,
    			success : function(o) {
    				$('#operForm').form('vstLoad', {
    					data:o
    				});
    				//初始化已有附件信息
    				FileModel.initViewDomByDataId(row.id);
    			}
    		});
    	}
	});
	url = basePath+"demo1/saveUpdate";
}
/**
 * 查看数据
 * @param index	待编辑数据的索引
 * @Date：		2018年4月25日 下午4:19:44
 * @Version		1.0
 */
function view(index) {
	$("#operFormView").form("resetAll");
	var row = $("#datagrid").datagrid("getRow", index);
	if(!row){
		return;
	}
	//打开窗口
	$("#winView").dialog({
		title: "查看员工信息",
		cache: true,
		closed:false,
	    href: basePath+"demo1/dialogView",
    	onLoad:function(){
    		$.ajax({
    			url : basePath+'demo1/edit?id=' + row.id,
    			mask:true,
    			success : function(o) {
    				$('#operFormView').form('vstLoad', {
    					data:o
    				});
    			}
    		});
    	}
	});
}



/**
 * 保存数据，编辑或者添加 - easyui-dialog窗口控件
 * @Date：		2018年4月25日 下午4:20:43
 * @Version		1.0
 */
function save(){
	if(!$("#operForm").form('validateAndWarn')){
		return;
	}
	$('#operForm').ajaxSubmit({
		url : url,
		traditional : true,
		type : 'post',
		mask : true,
		success : function(o){
			$('#win').dialog('close');
			$('#datagrid').datagrid('reload');
		}
	})
}
/**
 * 保存数据，编辑或者添加 - 全屏窗口控件
 * @Date：		2018年5月23日 下午2:46:47
 * @Version		1.0
 */
function saveFull(){
	if(!$("#operFormFull").form('validateAndWarn')){
		return;
	}
	alert("提交demo");
}
/**
 * 单条删除
 * @param index	待删除数据的行号
 * @Date：		2018年4月25日 下午1:55:33
 * @Version		1.0
 */
function removeOnly(index){
	var row = $("#datagrid").datagrid("getRow", index);
	removeRows([row]);
}

/**
 * 批量删除
 * @Date：		2018年4月25日 下午1:54:34
 * @Version		1.0
 */
function remove() {
	var rows = $('#datagrid').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择删除数据！','warning');
	} else {
		removeRows(rows);
	}
}

/**
 * 删除操作
 * @param rows	待删除的行数据rows
 * @Date：		2018年4月25日 下午2:01:25
 * @Version		1.0
 */
function removeRows(rows){
	$.messager.confirm('提示', '你确定要删除信息吗？', function(r) {
		if (r) {
			var ids = [];
            for(var i = 0; i< rows.length; i++){
                ids.push(rows[i].id);
            }
            if(ids.length==0){
            	$.messager.alert('提示', '没有需要删除的数据！','warning');
            	return;
            }
			$.ajax({
				url : basePath+'demo1/delete',
				data : {
                    "string[]-ids" : ids.join(',')
                },
				success : function(o) {
					$('#datagrid').datagrid('reload');
					$('#datagrid').datagrid('unselectAll');
				}
			});
		}

	});
}


/**
 * 查询数据
 * @Date：		2018年4月25日 下午4:21:57
 * @Version		1.0
 */
function searchForm() {
    $('#datagrid').datagrid('load', $('#searchForm').form("serializeObject"));
}

/**
 * 清除查询条件、返回初始数据展示状态
 * @Date：		2018年4月25日 下午4:22:06
 * @Version		1.0
 */
function clean() {
    $('#datagrid').datagrid('load', {});
    $('#searchForm').find('input').val('');
}

/**
 * @Comments		下载文件
 */
function downLoadByTableSelect(){
	//选择的行
	var rows = $('#datagrid').datagrid('getSelections');
	if(rows && rows.length>0){
		//把每行中包含的文件，放入files数组
		var dataIds = [];
		for(var i=0;i<rows.length;i++){
			var dataId = rows[i].id;
			if(dataId && dataId!=""){
				dataIds.push(dataId);
			}
		}
		FileModel.downloadByDataId(dataIds);
	}else{
		$.messager.alert('提示', '请至少选择一条记录！','info');
	}
}
