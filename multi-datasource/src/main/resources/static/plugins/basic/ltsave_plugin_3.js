(function (jq) {
    jq.fn.extend({
        "lSave": function() {
            var qxdm = top.INDEXModel.whoami(window).qxdm;
            var formId = this.attr('id');
            lsave(formId, qxdm);
        },
        "lEcho": function() {
            var qxdm = top.INDEXModel.whoami(window).qxdm;
            var formId = this.attr('id');
            $.post(basePath+'test/getLtsr',
                {'formId': qxdm + "_"+ formId},
                function(fdata) {
                    for (var ee in fdata ) {
                        var eid_type = ee.split('_');
                        var tar_id = eid_type[0];
                        var tar_c = eid_type[1];
                        switch (tar_c) {
                            case 'numberbox':
                                $("#"+ tar_id).numberbox('setValue', fdata[ee]);
                                break;
                            case 'textbox':
                                $("#"+ tar_id).textbox('setValue', fdata[ee]);
                                break;
                            case 'datebox':
                                $("#"+ tar_id).datebox('setValue', fdata[ee]);
                                break;
                            case 'combobox':
                                $("#"+ tar_id).combobox('setValue', fdata[ee]);
                                break;
                            case 'combotree':
                                $("#"+ tar_id).combotree('setValue', fdata[ee]);
                                break;
                            case 'numberspinner':
                                $("#"+ tar_id).numberspinner('setValue', fdata[ee]);
                                break;
                            case 'vsttextarea':
                                $("#"+ tar_id).val(fdata[ee]);
                                break;
                        }
                    }
                }, 'json');
        },
        "ltSave": function() {
            var qxdm = top.INDEXModel.whoami(window).qxdm;
            var formId = this.attr('id');
            $.post(basePath+'test/getLtsr',
                {'formId': qxdm + "_"+ formId},
                function(fdata) {
                    for (var ee in fdata ) {
                        var eid_type = ee.split('_');
                        var tar_id = eid_type[0];
                        var tar_c = eid_type[1];
                        switch (tar_c) {
                            case 'numberbox':
                                $("#"+ tar_id).numberbox('setValue', fdata[ee]);
                                break;
                            case 'textbox':
                                $("#"+ tar_id).textbox('setValue', fdata[ee]);
                                break;
                            case 'datebox':
                                $("#"+ tar_id).datebox('setValue', fdata[ee]);
                                break;
                            case 'combobox':
                                $("#"+ tar_id).combobox('setValue', fdata[ee]);
                                break;
                            case 'combotree':
                                $("#"+ tar_id).combotree('setValue', fdata[ee]);
                                break;
                            case 'numberspinner':
                                $("#"+ tar_id).numberspinner('setValue', fdata[ee]);
                                break;
                            case 'vsttextarea':
                                $("#"+ tar_id).val(fdata[ee]);
                                break;
                        }
                    }
                }, 'json');
            var stimer = setInterval(lsave, 3500, formId, qxdm);
            this.data({"stimer": stimer});
        },
        'unLtSave': function() {
            var qxdm = top.INDEXModel.whoami(window).qxdm;
            var formId = this.attr('id');
            $.post(basePath+'test/clearLT',
                {'formId':qxdm + "_"+ formId},
                function (data) {

                },'json');
            clearInterval(this.data("stimer"));
        }

    });

    /**
     * 定时存储大表数据
     * @param interval
     * @param formId
     */
    function tvl(interval, formId) {

    }
    /** 将获取到的数据存储到后台  */
    function lsave(formId, qxdm) {
        var hdata = ltdata(formId);
        hdata['formId'] = qxdm + "_"+ formId;
        $.post(basePath+'test/ltsr',
            hdata,
            function(data) {
            }, 'json');
    }
    /** 获取数据  */
    function ltdata(formId) {
        var bval = {};
        $("#"+ formId).find(".textbox-value").each(function(i, n){
            var cur_n = $(n);
            if (typeof(cur_n.attr("id"))=="undefined") {
                var tar_n = cur_n.parent().prev();
                var tar_cls = tar_n.attr("class");
                var tar_id = tar_n.attr("id");
                var tar_c = tar_cls.split(" ")[0].split("-")[1];
                var ltkey = tar_id + "_"+ tar_c;
                switch (tar_c) {
                    case 'numberbox':
                        bval[ltkey] = $("#"+ tar_id).numberbox('getValue');
                        break;
                    case 'textbox':
                        bval[ltkey] = $("#"+ tar_id).textbox('getValue');
                        break;
                    case 'datebox':
                        bval[ltkey] = $("#"+ tar_id).datebox('getValue');
                        break;
                    case 'combobox':
                        bval[ltkey] = $("#"+ tar_id).combobox('getValue');
                        break;
                    case 'combotree':
                        bval[ltkey] = $("#"+ tar_id).combotree('getValue');
                        break;
                    case 'numberspinner':
                        bval[ltkey] = $("#"+ tar_id).numberspinner('getValue');
                        break;
                }
            } else {
                var tar_cls = cur_n.attr("class");
                var tar_id = cur_n.attr("id");
                var tar_c = tar_cls.split(" ")[0];
                bval[tar_id + "_"+ tar_c] = cur_n.val();
            }

        });
        return bval;
    }

})(jQuery);
