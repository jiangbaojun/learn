$(function(){
	init();
});

/**
 * 初始化工作
 */
function init(){

}

/**
 * 双击行触发函数
 */
function dblClickRow(rowIndex, rowData){
	 $('#datagrid').datagrid('unselectAll');
     $.messager.alert('提示', '双击'+rowIndex, 'info');
}

//编辑、添加的url
var url;
/**
 * 添加数据
 */
function add() {
	$("#win").dialog({title: "保存角色"});
	$("#win").dialog("open");
	$("#operForm").form("clear");
	$('#operForm').form("cancelInitialValidate");
	url = "basic/system/role/saveForm";
}

/**
 * 编辑数据
 */
function edit(index) {
	$("#operForm").form("clear");
	var row = $("#datagrid").datagrid("getRow", index);
	$.ajax({
		url : 'basic/system/role/edit?id=' + row.ROLEID,
		mask:true,
		success : function(o) {
			$('#operForm').form('vstLoad', {
				data:o,
				callback:function(){
					$("#win").dialog({title: "修改角色"});
					$("#win").dialog("open");
					$("#operForm").form('validate');
				}
			});
		}
	});
	url = "basic/system/role/update?id=" + row.ROLEID;
}

/**
 * 保存数据，编辑或者添加
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
 * 单条删除
 */
function removeOnly(index){
	var row = $("#datagrid").datagrid("getRow", index);
	removeRows([row]);
}

/**
 * 批量删除
 */
function remove() {
	var rows = $('#datagrid').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择删除数据！','info');
	} else {
		removeRows(rows);
	}
}

/**
 * 删除操作
 */
function removeRows(rows){
	$.messager.confirm('提示', '你确定要删除信息吗？', function(r) {
		if (r) {
			var ids = [];
            for(var i = 0; i< rows.length; i++){
                ids.push(rows[i].ROLEID);
            }
			$.ajax({
				url : 'basic/system/role/delete',
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
 */
function searchForm() {
    $('#datagrid').datagrid('load', $('#searchForm').form("serializeObject"));
}

/**
 * 清除查询条件、返回初始数据展示状态
 */
function clean() {
    $('#datagrid').datagrid('load', {});
    $('#searchForm').find('input').val('');
}
