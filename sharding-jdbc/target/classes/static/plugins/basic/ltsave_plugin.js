(function (jq) {
    jq.fn.extend({
		"ltSave": function() {
            var formId = this.attr('id');
            $.post(basePath+'test/getLtsr',
                {'formId': formId},
                function(fdata) {
                    for (var ee in fdata ) {
                        var eid_type = ee.split('_');
                        var type = eid_type[1];
                        var a = eid_type[0];
                        switch (type) {
                            case '1':
                                $("#"+ a).numberbox('setValue', fdata[ee]);
                                break;
                            case '2':
                                $("#"+ a).textbox('setValue', fdata[ee]);
                                break;
                            case '3':
                                $("#"+ a).datebox('setValue', fdata[ee]);
                                break;
                            case '4':
                                $("#"+ a).combobox('setValue', fdata[ee]);
                                break;
                            case '5':
                                $("#"+ a).combotree('setValue', fdata[ee]);
                                break;
                            case '6':
                                $("#"+ a).numberspinner('setValue', fdata[ee]);
                                break;
                        }
                    }
                }, 'json');
            tvl(1500, formId);
        },
        'unLtSave': function() {
            var formId = this.attr('id');
            $.post(basePath+'test/clearLT',
                {'formId':formId},
                function (data) {
                    clearInterval($.stimer);
                },'json');
        }

    });

    /**
	 * 定时存储大表数据
     * @param interval
     * @param formId
     */
    function tvl(interval, formId) {
        $.stimer = setInterval(lsave, interval, formId);
    }
    /** 将获取到的数据存储到后台  */
    function lsave(formId) {
        var hdata = ltdata();
        hdata['formId'] = formId;
        $.post(basePath+'test/ltsr',
            hdata,
            function(data) {
            }, 'json');
    }
    /** 获取数据  */
    function ltdata() {
        var bval = {};
        $("*[data-lt]").each(function(i, n){
            var crn = $(n);
            var ltkey = crn.attr('data-lt');
            var eid_type = ltkey.split('_');
            var type = eid_type[1];
            var a = eid_type[0];
            switch (type) {
                case "1":
                    bval[ltkey] = $("#"+ a).numberbox('getValue');
                    break;
                case "2":
                    bval[ltkey] = $("#"+ a).textbox('getValue');
                    break;
                case "3":
                    bval[ltkey] = $("#"+ a).datebox('getValue');
                    break;
                case "4":
                    bval[ltkey] = $("#"+ a).combobox('getValue');
                    break;
                case "5":
                    bval[ltkey] = $("#"+ a).combotree('getValue');
                    break;
                case "6":
                    bval[ltkey] = $("#"+ a).numberspinner('getValue');
                    break;
            }
        });
        return bval;
    }

})(jQuery);

/**
 *  大表保存类型说明
 1	numbox
 2	textbox
 3	datebox
 4	combobox
 5	combotree
 6 	numberspinner
 */

