/**
 //恢复数据
 splugin.resumedData("ltform");
 //临时取储数据
 splugin.tvl(3000,'ltform');
 // 取消临时存储
 //splugin.qxTimmer();
 */


var splugin = {
    /** 定时存取对象 */
    stimer:null,
    /** 临时存储数据 */
    tvl: function(interval,formId) {
        this.stimer = setInterval(splugin.lsave, interval, formId);
    },
    lsave: function(formId) {
        var hdata = splugin.ltdata();
        hdata['formId'] = formId;
        $.post(basePath+'test/ltsr',
            hdata,
            function(data) {
            }, 'json');
    },
    ltdata: function () {
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
    },
    resumedData: function(formId) {
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
    },
    qxTimmer: function() {
        clearInterval(splugin.stimer);
    }
};