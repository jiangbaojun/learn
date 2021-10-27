$(function(){
	init();
});

/**
 * 初始化工作
 */
function init(){
	initCompent();
}

function initCompent(){

	var OrgMap = {};
	$.ajax({
		url: 'basic/system/organization/selectCombobox',
		success: function(data){
			var opt = {
				parentField: "_parentId",
				idFiled: "ZZJGID",
				textFiled: "ZZJGMC",
			};
			var rootData = [];
			var idFiled, textFiled, parentField;
			if (opt.parentField) {
				idFiled = opt.idFiled || 'id';
				textFiled = opt.textFiled || 'text';
				parentField = opt.parentField;

				var i, l, treeData = [], tmpMap = [];


				for (i = 0, l = data.length; i < l; i++) {
					tmpMap[data[i][idFiled]] = data[i];
				}

				for (i = 0, l = data.length; i < l; i++) {
					if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
						if (!tmpMap[data[i][parentField]]['children'])
							tmpMap[data[i][parentField]]['children'] = [];
						data[i]['id'] = data[i][idFiled];
						data[i]['text'] = data[i][textFiled];
						tmpMap[data[i][parentField]]['children'].push(data[i]);
					} else {
						data[i]['id'] = data[i][idFiled];
						data[i]['text'] = data[i][textFiled];
						treeData.push(data[i]);
						//根数据
						rootData.push({
							"id": data[i][idFiled],
							"text": data[i][textFiled],
							"state": "closed"
						});
					}
				}
				OrgMap = tmpMap;
			}else {
				OrgMap = data;
			}
			//第一级数据,初始化
			initOrgComboTree(rootData);
		}
	});
	function initOrgComboTree(data){
		$('#organizationId').combotree({
			idFiled: "id",
			textFiled: "text",
			data: data,
			onBeforeExpand: function (node) {
				if(!node.isAdded){
					node.isAdded = true;
				}else{
					return true;
				}
				var pNode = OrgMap[node.id];
				if(pNode && pNode.children && pNode.children.length>0){
					var nodes = pNode.children;
					var childNodes = [];
					for (var i=0;i<nodes.length;i++) {
						var item = nodes[i];
						var obj = {
							"id": item.id,
							"text": item.text,
						};
						if(item.children&& item.children.length>0){
							obj.state = "closed";
						}
						childNodes.push(obj)
					}
					$("#organizationId").combotree('tree').tree('append', {
						parent: node.target,
						data: childNodes
					});
				}else{

				}
			}
		});
	}

	var eventNode = null;
	$('#resourceId').tree({
		url: 'basic/system/resource/selectCombobox',
		parentField: "_parentId",
		idFiled: "XTQXID",
		textFiled: "QXMC",
		//lines: true,
		checkbox:true,
		cascadeCheck: false,
		loadFilter: function(data, parent){
			var opt = $(this).data().tree.options;
			var idFiled, textFiled, parentField;
			if (opt.parentField) {
				idFiled = opt.idFiled || 'id';
				textFiled = opt.textFiled || 'text';
				parentField = opt.parentField;

				var i, l, treeData = [], tmpMap = [];

				for (i = 0, l = data.length; i < l; i++) {
					tmpMap[data[i][idFiled]] = data[i];
				}

				for (i = 0, l = data.length; i < l; i++) {
					if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
						if (!tmpMap[data[i][parentField]]['children'])
							tmpMap[data[i][parentField]]['children'] = [];
						data[i]['id'] = data[i][idFiled];
						data[i]['text'] = data[i][textFiled];
						tmpMap[data[i][parentField]]['children'].push(data[i]);
					} else {
						data[i]['id'] = data[i][idFiled];
						data[i]['text'] = data[i][textFiled];
						treeData.push(data[i]);
					}
				}
				//alert(JSON.stringify(treeData));
				return treeData;
			}
			return data;
		},
		onCheck: function (node, checked) {
			if (checked) {
				if (!eventNode) {
					eventNode = node;
					treeup = true;//向上遍历标志，由鼠标勾选的节点eventNode开始向上遍历
				}

				// 由鼠标勾选的节点eventNode开始向上遍历
				if(treeup) {
					var parentNode = $(this).tree('getParent', node.target);
					if (parentNode != null) {
						$(this).tree('check', parentNode.target);
					}
					treeup = false; //向上遍历结束
				}

				// 到达根部节点或向上遍历结束之后再回到原始节点开始向下遍历
				if(!treeup && eventNode) {
					var childNode = $(this).tree('getChildren', eventNode.target);
					if (childNode.length > 0) {
						for (var i = 0; i < childNode.length; i++) {
							$(this).tree('check', childNode[i].target);
						}
					}
				}
				eventNode = null; //标志本次鼠标勾选事件遍历结束
			} else {
				// 取消当前节点的所有子节点选中状态
				var childNode = $(this).tree('getChildren', node.target);
				if (childNode.length > 0) {
					for (var i = 0; i < childNode.length; i++) {
						$(this).tree('uncheck', childNode[i].target);
					}
				}

				// 判断当前节点的兄弟节点是否选中，如果都没有选中，父级节点取消选中状态
				// 当前节点的父级节点
				var parentNode = $(this).tree('getParent', node.target);
				if (parentNode != null) {
					// 当前节点的兄弟节点
					var siblingsNode = $(this).tree('getChildren', parentNode.target);
					var checkedCount = 0;
					for (var i = 0; i < siblingsNode.length; i++) {
						if(siblingsNode[i].checked) {
							checkedCount = checkedCount + 1;
						}
					}
					// 兄弟节点都未选中，父节点取消选中状态
					if (checkedCount == 0) {
						$(this).tree('uncheck', parentNode.target);
					}
				}
			}
		}
	});

	$('#roleId').combobox({
		url:'basic/system/role/selectCombobox',
		multiple: true,
		valueField:'ROLEID',
		textField:'ROLENAME',
		onChange: function(newValue, oldValue) {
			preview();
		}
	});
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
	$("#win").dialog({title: "保存用户"});
	$("#win").dialog("open");
	$("#operForm").form("clear");
	$('#operForm').form("cancelInitialValidate");
	url = "basic/system/user/saveForm";
}

/**
 * 编辑数据
 */
function edit(index) {
	$("#operForm").form("clear");
	var row = $("#datagrid").datagrid("getRow", index);
	$.ajax({
		url : 'basic/system/user/edit?id=' + row.USERID,
		mask:true,
		success : function(o) {
			$('#operForm').form('vstLoad', {
				data:o,
				callback:function(){
					$("#win").dialog({title: "修改用户"});
					$("#win").dialog("open");
					$("#operForm").form('validate');
				}
			});
		}
	});
	url = "basic/system/user/update?id=" + row.USERID;
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
                ids.push(rows[i].USERID);
            }
			$.ajax({
				url : 'basic/system/user/delete',
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

function preview(newValue) {
	// clear all checks
	var root = $('#resourceId').tree('getRoot');
	$('#resourceId').tree('uncheck', root.target);
	var roleId = $('#roleId').combobox('getValue');
	if(roleId) {
		$.ajax({
			url : 'basic/system/role/selectPermissionIds',
			data : {
				"string[]-ids" : roleId
			},
			success : function(o) {
				// selected role's perms are checked
				var arr = o.split(',');
				for(var i = 0; i <arr.length; i++ ) {
					var node = $('#resourceId').tree('find', arr[i]);
					//alert(node.text);
					$('#resourceId').tree('check', node.target);
				}
			}
		});
	}
}
function formatUserStatus(val,row){
	if (val == '0') {
		return '注销';
	} else if (val == '1') {
		return '正常';
	} else if (val == '2') {
		return '锁定';
	} else {
		return "";
	}
}

function formatOperationBtn(val, row, index){
	//空数据（id为空的）不添加修改选项
	if(row.USERID == undefined){
		return;
	}
	var btns = '';
	btns = '<a class="datagrid-operate edit" title="编辑" href="javascript:void(0)" onclick="edit('+index+')"></a>';
	btns += '<a class="datagrid-operate remove" title="删除" href="javascript:void(0)" onclick="removeOnly('+index+')"></a>';
	return btns;
}
