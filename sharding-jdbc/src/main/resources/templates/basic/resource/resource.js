$(function(){
	init();
});

/**
 * 初始化工作
 */
function init(){
	//类型选择
	$('#type').combobox({
		onSelect: function(row){
			if(row.value=="2"){
				$("#datasourceDiv").show();
			}else{
				$("#datasourceDiv").hide();
			}
		}
	});

}

//编辑、添加的url
var url;
/**
 * 添加数据
 */
function add(parentId,type) {
	$("#win").dialog({title: "保存资源"});
	$("#win").dialog("open");
	$("#operForm").form("clear");
	$('#operForm').form("cancelInitialValidate");
	if(parentId) {
		$("#parentId").combotree("setValues", parentId);
	}
	if(type=="1"){
		$("#datasourceDiv").show();
	}else{
		$("#datasourceDiv").hide();
	}
	url = "basic/system/resource/saveForm";
}

/**
 * 编辑数据
 */
function edit(id) {
	$("#operForm").form("clear");
	$('#operForm').form("cancelInitialValidate");
	$.ajax({
		url : 'basic/system/resource/edit?id=' + id,
		mask:true,
		success : function(o) {
			$('#operForm').form('vstLoad', {
				data:o,
				callback:function(){
					$("#win").dialog({title: "修改资源"});
					$("#win").dialog("open");
					$("#operForm").form('validate');
					if(o.type=="2"){
						$("#datasourceDiv").show();
					}else{
						$("#datasourceDiv").hide();
					}
				}
			});
		}
	});
	url = "basic/system/resource/update?id=" + id;
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
	removeRows([{XTQXID:id}]);
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
                ids.push(rows[i].XTQXID);
                //判断该节点是不是叶子节点
                var nodes=$('#treegrid').treegrid('getChildren', rows[i].XTQXID);
                if(nodes.length > 0){
                	$.messager.alert('提示', '请先删除子节点！','info');
                	return false;
                }
            }

			$.ajax({
				url : 'basic/system/resource/delete',
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
	var code = $('#code').val();
	var name = $('#name').val();

	//从根节点开始遍历
	var findedId = searchTree('#treegrid', code, name);
	if(findedId) {
		$('#treegrid').treegrid('unselectAll');
		$('#treegrid').treegrid('select', findedId);
		$('#treegrid').treegrid('expandTo', findedId);
    }
}

/**
 * 本地树检索
 *
 * 返回符合条件的第一个节点ID
 */
function searchTree(treeId, code, name) {
	var root = $(treeId).treegrid('getRoot');
	if(root.QXDM.indexOf(code) > -1 && root.QXMC.indexOf(name) > -1) {
		return root.XTQXID;
	} else {
		var childrenNode =  $(treeId).treegrid('getChildren', root.ZZJGID);
		if (childrenNode.length > 0) {
			for (var i = 0; i < childrenNode.length; i++) {
				if(childrenNode[i].QXDM.indexOf(code) > -1 &&
						childrenNode[i].QXMC.indexOf(name) > -1) {
					return childrenNode[i].XTQXID;
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
