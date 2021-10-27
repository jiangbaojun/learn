$(function(){
	init();
});

/**
 * 初始化工作
 */
function init(){
	$('#parentId').combotree({
		url: 'basic/system/organization/selectCombobox',
		parentField: "_parentId",
		idFiled: "ZZJGID",
		textFiled: "ZZJGMC",
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
		}
	});

}

//编辑、添加的url
var url;
/**
 * 添加数据
 */
function add(parentId) {
	$("#win").dialog({title: "保存组织机构"});
	$("#win").dialog("open");
	$("#operForm").form("clear");
	$('#operForm').form("cancelInitialValidate");
	if(parentId) {
		$("#parentId").combotree("setValues", parentId);
	}
	url = "basic/system/organization/saveForm";
}

/**
 * 编辑数据
 */
function edit(id) {
	$("#operForm").form("clear");
	$('#operFormFull').form("cancelInitialValidate");
	$.ajax({
		url : 'basic/system/organization/edit?id=' + id,
		mask:true,
		success : function(o) {
			$('#operForm').form('vstLoad', {
				data:o,
				callback:function(){
					$("#win").dialog({title: "修改组织机构"});
					$("#win").dialog("open");
					$("#operForm").form('validate');
				}
			});
		}
	});
	url = "basic/system/organization/update?id=" + id;
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
			$('#treegrid').treegrid('reload');
		}
	})
}

/**
 * 单条删除
 */
function removeOnly(id){
	removeRows([{ZZJGID:id}]);
}

/**
 * 批量删除
 */
function remove() {
	var rows = $('#treegrid').treegrid('getSelections');
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
                ids.push(rows[i].ZZJGID);
                //判断该节点是不是叶子节点
                var nodes = $('#treegrid').treegrid('getChildren', rows[i].ZZJGID);
                if(nodes.length > 0){
                	$.messager.alert('提示', '请先删除子节点！','info');
                	return false;
                }
            }
			$.ajax({
				url : 'basic/system/organization/delete',
				data : {
                    "string[]-ids" : ids.join(',')
                },
				success : function(o) {
					$('#treegrid').treegrid('reload');
					$('#treegrid').treegrid('unselectAll');
				}
			});
		}

	});
}


/**
 * 查询数据
 */
function searchForm() {
	// 服务器端检索
	//$('#treegrid').treegrid('load', $('#searchForm').form("serializeObject"));

	// 设置本地树检索条件
	var orgCode = $('#organizationCode').val();
	var orgName = $('#organizationName').val();

	//从根节点开始遍历
	var findedId = searchTree('#treegrid', orgCode, orgName);
	if(findedId) {
		$('#treegrid').treegrid('unselectAll');
		$('#treegrid').treegrid('select', findedId);
    }
}

/**
 * 本地树检索
 *
 * 返回符合条件的第一个节点ID
 */
function searchTree(treeId, code, name) {
	var root = $(treeId).treegrid('getRoot');
	if(root.ZZBM.indexOf(code) > -1 && root.ZZJGMC.indexOf(name) > -1) {
		return root.ZZJGID;
	} else {
		var childrenNode =  $(treeId).treegrid('getChildren', root.ZZJGID);
		if (childrenNode.length > 0) {
			for (var i = 0; i < childrenNode.length; i++) {
				if(childrenNode[i].ZZBM.indexOf(code) > -1 &&
						childrenNode[i].ZZJGMC.indexOf(name) > -1) {
					return childrenNode[i].ZZJGID;
				}
			}
		}
    }
}

/**
 * 清除查询条件、返回初始数据展示状态
 */
function clean() {
    $('#treegrid').treegrid('load', {});
    $('#searchForm').find('input').val('');
}


function formatOperationBtn(val, row, index){
	var btns = '';
	var btnAdd = '';
	var btnDelete = '';

	btnAdd = '<a class="datagrid-operate add" title="添加" href="javascript:void(0)" onclick="add('+ "'" + row.ZZJGID + "'" +')"></a>';
	btns += btnAdd;

	btns += '<a class="datagrid-operate edit" title="编辑" href="javascript:void(0)" onclick="edit(\''+row.ZZJGID+'\')"></a>';

	btnDelete = '<a class="datagrid-operate remove" title="删除" href="javascript:void(0)" onclick="removeOnly(\''+row.ZZJGID+'\')"></a>';
	// 不允许删除根节点
	var root = $('#treegrid').treegrid('getRoot');
	if(row.ZZJGID == root.ZZJGID) {
		btnDelete = '';
	}
	btns += btnDelete;

	return btns;
}
