layui.use(['form', 'table', 'laydate'], function () {
	var $ = layui.jquery,
		form = layui.form,
		laydate = layui.laydate,
		table = layui.table;

	laydate.render({
		elem: '#rzrq1'
	});
	laydate.render({
		elem: '#rzrq2'
	});
	table.render({
		elem: '#currentTableId',
		url : basePath+"demo1/selectList",
		toolbar: '#toolbarDemo',
		autoSort:false,
		defaultToolbar: ['filter', 'exports', 'print', {
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		cols: [[
			{type: "checkbox", width: 50},
			{field: 'id', width: 80, title: 'ID', sort: true},
			{field: 'workId', width: 80, title: '工号'},
			{field: 'xm', width: 100, title: '姓名', sort: true},
			{field: 'sj', width: 120, title: '手机'},
			{field: 'rzrq', title: '入职日期', width: 120},
			{field: 'zw', width: 80, title: '职务', sort: true},
			{field: 'bm', width: 80, title: '部门', sort: true},
			{title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
		]],
		response: {statusName: 'status',statusCode: 200,countName: 'total',dataName: 'rows'},
		request:{pageName:'page',limitName:'rows'},
		limits: [10, 15, 20, 25, 50, 100],
		limit: 10,
		page: true
	});

	table.on('sort(currentTableFilter)', function(obj){
		table.reload('currentTableId', { where: {sort:obj.field,order:obj.type}});
	});

	// 监听搜索按钮操作
	form.on('submit(data-search-btn)', function (data) {
		//执行搜索重载
		table.reload('currentTableId', {
			page: {
				curr: 1
			},
			where: data.field
		});
		return false;
	});

	/**
	 * toolbar监听事件
	 */
	table.on('toolbar(currentTableFilter)', function (obj) {
		if (obj.event === 'add') {  // 监听添加操作
			var index = layer.open({
				title: '添加用户',
				type: 2,
				shade: 0.2,
				maxmin:true,
				shadeClose: true,
				area: ['100%', '100%'],
				content: basePath+"demo1/layui/winDialog"
			});
		} else if (obj.event === 'delete') {  // 监听删除操作
			var checkStatus = table.checkStatus('currentTableId')
				, rows = checkStatus.data;
			if(!rows || rows.length==0){
				layer.alert('没有选择数据', {icon: 3});
				return;
			}
			layer.confirm('真的要删除选择的数据吗？', function (index) {
				var ids = [];
				for(var i = 0; i< rows.length; i++){
					ids.push(rows[i].id);
				}
				deleteTableData(ids);
				layer.close(index);
			});
		}
	});

	/**
	 * toolbar监听事件-操作列
	 */
	table.on('tool(currentTableFilter)', function (obj) {
		var data = obj.data;
		if (obj.event === 'edit') {
			var index = layer.open({
				title: '添加用户',
				type: 2,
				shade: 0.2,
				maxmin:true,
				shadeClose: true,
				area: ['100%', '100%'],
				content: basePath+"demo1/layui/winDialog",
				success: function(layero, index){
					var body = layer.getChildFrame('body', index);
					var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
					for(var key in data){
						body.find('[name='+key+']').val(data[key]);
					}
				}
			});
			return false;
		} else if (obj.event === 'delete') {
			layer.confirm('真的删除行么', function (index) {
				deleteTableData([obj.data.id])
				layer.close(index);
			});
		}
	});

	/**
	 * 删除数据
	 * @param ids
	 */
	function deleteTableData(ids){
		$.ajax({
			url : basePath+'demo1/delete',
			data : {
				"string[]-ids" : ids.join(',')
			},
			success : function(o) {
				layer.alert('删除成功', {icon: 1});
				table.reload('currentTableId');
			}
		});
	}

});
