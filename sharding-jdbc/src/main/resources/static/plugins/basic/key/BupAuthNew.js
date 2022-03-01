var appid = "64";
var setIntervalNumber = 5000;
var redisKey;
var ip = "172.26.57.34";
//判断新驱动
var isocxIE = true;
try {
    var plugin = document.getElementById("BhoPlugin");
    var userList = plugin.SOF_GetUserList();
    isocxIE = true;
    if (userList == "" || userList == undefined) {
        isocxIE = false;
    }
}
catch (e) {
    isocxIE = false;
}
if (!isocxIE) {
	document.write("<script for='bjcactrl' event='OnUsbkeyChange' language='javascript'> ChangeDrivers();</script >");
}
function checkBrowser() {

    if (window.location.href.indexOf('111.198.186.110') != -1) {
        ip = "111.198.186.110";
    }
    var ua = navigator.userAgent.toLocaleLowerCase();
    var bversion = navigator.appVersion;
    var browserType = null;
    var browserVersion = null;
    if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
        browserType = "IE";
        browserVersion = ua.match(/msie ([\d.]+)/) != null ? ua.match(/msie ([\d.]+)/)[1] : ua.match(/rv:([\d.]+)/)[1];
    } else if (ua.match(/firefox/) != null) {
        browserType = "火狐";
        browserVersion = ua.match(/firefox\/([\d.]+)/)[1];

    } else if (ua.match(/ubrowser/) != null) {
        browserType = "UC";
        browserVersion = ua.match(/ubrowser.([\d.]+)/)[1];
    } else if (ua.match(/opera/) != null) {
        browserType = "欧朋";
        browserVersion = ua.match(/opera.([\d.]+)/)[1];
    } else if (ua.match(/bidubrowser/) != null) {
        browserType = "百度";
        browserVersion = ua.match(/bidubrowser\/([\d.]+)/)[1];
    } else if (ua.match(/metasr/) != null) {
        browserType = "搜狗";
        browserVersion = ua.match(/metasr\/([\d.]+)/)[1];
    } else if (ua.match(/tencenttraveler/) != null || ua.match(/qqbrowse/) != null) {
        browserType = "QQ";
        browserVersion = ua.match(/qqbrowser\/([\d.]+)/)[1]
    } else if (ua.match(/maxthon/) != null) {
        browserType = "遨游";
        browserVersion = ua.match(/maxthon\/([\d.]+)/)[1];
    } else if (ua.match(/chrome/) != null) {
        var is360 = _mime("type", "application/vnd.chromium.remoting-viewer");
        function _mime(option, value) {
            var mimeTypes = navigator.mimeTypes;
            for (var mt in mimeTypes) {
                if (mimeTypes[mt][option] == value) {
                    return true;
                }
            }
            return false;
        }
        if (is360) {
            browserType = '360';
            browserVersion = ua.match(/rv:([\d.]+)/)[1];
        } else {
            browserType = "谷歌";
            browserVersion = ua.match(/chrome\/([\d.]+)/)[1];
        }

    } else if (ua.match(/safari/) != null) {
        browserType = "Safari";
        browserVersion = ua.match(/version\/([\d.]+)/)[1];
    }
    return browserType + " " + browserVersion.split('.')[0];
}


//定期执行更新在线人信息
function updateOneLine(user)
{
    var ticket = document.getElementById("hdTicket").value;
    USERINFOJSON.GetValue(ticket, function (certid) {
        var list = user.split('&');
        for (var i = 0; i < list.length; i++) {
            if (list[i] == "") continue;
            var str = list[i].split('||');
            var keys = str[1].split('/');
            if (str[1] == certid) {
                redisKey = keys[1] + '|' + appid + '|' + appid + '|' + str[1] + '|' + str[0];
                //redisKey = encodeURI(redisKey);
            }
        }

        var rk = ConvertToBase64(redisKey);
        USERINFOJSON.UpdateOnline(rk);
        setInterval(execUpdateOnline, setIntervalNumber);
    });
}


function execUpdateOnline() {
    var rk = ConvertToBase64(redisKey)
    USERINFOJSON.UpdateOnline(rk);
}

function updateOneLineIE() {
    var plugin = document.getElementById("BhoPlugin");
    var ticket = document.getElementById("hdTicket").value;
    var certid = document.getElementById("hdCertId").value;

    var user = plugin.SOF_GetUserList();
    var list = user.split('&');
    for (var i = 0; i < list.length; i++) {
        if (list[i] == "") continue;
        var str = list[i].split('||');
        var keys = str[1].split('/');
        if (str[1] == certid) {
            redisKey = keys[1] + '|' + appid + '|' + appid + '|' + str[1] + '|' + str[0];
            //redisKey = encodeURI(redisKey);
        }
    }
    var rk = ConvertToBase64(redisKey)
    var rs = plugin.UpdateOnline(rk);
    setInterval(execUpdateOnlineIE, setIntervalNumber);
}


function execUpdateOnlineIE() {
    var plugin = document.getElementById("BhoPlugin");
    var rk = ConvertToBase64(redisKey);
    var rs = plugin.UpdateOnline(rk);
}

function validatTicketEx(appid, homepage) {
    var baseUrl = "";
    if (location.search != "")
        baseUrl = (/[?&]webChannelBaseUrl=([A-Za-z0-9\-:/\.]+)/.exec(location.search)[1]);
    else
        baseUrl = "rmq://localhost:12345";
    var socket = new WebSocket(baseUrl);
    socket.onclose = function () {
        console.error("web channel closed");
    };
    socket.onerror = function (error) {
        console.error("web channel error: " + error);
    };

    socket.onopen = function () {
        new QWebChannel(socket, function (channel) {
            window.USERINFOJSON = channel.objects.USERINFOJSON;
            var ticket = document.getElementById("hdTicket").value;

            if (ticket == '') {
                alert('非法登录系统1');
                window.location.href = homepage;
            } else {
                USERINFOJSON.CheckTicketReturnJsonFun(appid, ticket, function (rs) {
                    if (rs == "") {
                        alert('非法登录系统2（票据为验证通过）');
                        window.location.href = homepage;
                    }
                });
            }
            USERINFOJSON.SOF_GetUserList(function (arg) {
                updateOneLine(arg);
            });
            USERINFOJSON.OnUSBKeyChange.connect(function (text) {
                USERINFOJSON.SOF_GetUserList(function (arg) {
                    var userlist = arg;
                    var sfrzcertid = document.getElementById("hdCertId").value;
                    if (sfrzcertid != '') {
                        if (userlist.indexOf(sfrzcertid) < 0) {
                            window.location.href = homepage;
                        }
                    }
                });
            });

            USERINFOJSON.UpdateOnlineRtn.connect(function (text) {
                USERINFOJSON.SOF_GetUserList(function (arg) {
                    var userlist = arg;
                    var ticket = document.getElementById("hdTicket").value;
                    USERINFOJSON.GetValue(ticket, function (sfrzcertid) {
                        if (text.indexOf(sfrzcertid) >= 0) {
                            window.location.href = homepage;
                        }
                    });
                });

            });
        });
    }
}


function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}



//验证票据 0成功 -1 获取票据异常 -2 未通过验证票据,-3 组件调用异常
function validatTicket(appid) {

    var op = 0;

    if (isocxIE) {
        try {
            var ticket = document.getElementById("hdTicket").value;

            if (ticket == '') {
                op = -1;
            } else {
                var plugin = document.getElementById("BhoPlugin");
                var rs = plugin.CheckTicketReturnJsonFun(appid, ticket);
                if (rs != "") {
                    op = 0;
                } else {
                    op = -2;
                }
            }
        } catch (err) {
            op = -3;
        } finally {
            updateOneLineIE();
            return op;
        }
    } else
    {
        return op;
    }
}

function getCert(certid) {
    var plugin = document.getElementById("BhoPlugin");
    if (plugin != null) {
        var cert = plugin.SOF_ExportUserCert(certid);
        return cert;
    } else {
        return "";
    }
}

//根据容器号取证书
function getCertEx(certid) {

    USERINFOJSON.SOF_ExportUserCert(certid, function (cert) {
        //cert 证书
    });
}

//证书登出
function certLogout() {
    try {
        var certid = document.getElementById("hdCertId").value;
        if (certid == "") return;
        var plugin = document.getElementById("BhoPlugin");
        if (plugin != null) {
            var appid = document.getElementById("hdappid").value;
            var cert = plugin.SOF_ExportUserCert(certid);
            var data = plugin.SOF_GetCertInfoByOid(cert, "1.2.86.11.7.1.8");
            plugin.UpdateOnline(data, "2", appid);
        }
    } catch (err) { }
}

//关闭页面
function closePageForm(browserType, version, homepage) {

    if (browserType == "IE") {
        var plugin = document.getElementById("BhoPlugin");
        var userlist = plugin.SOF_GetUserList();
        var sfrzcertid = document.getElementById("hdCertId").value;
        if (sfrzcertid != '') {
            if (userlist.indexOf(sfrzcertid) < 0) {
            	closeWindows();
            }
        }
    } else {
        USERINFOJSON.SOF_GetUserList(function (arg) {
            var userlist = arg;
            var sfrzcertid = document.getElementById("hdCertId").value;
            if (sfrzcertid != '') {
                if (userlist.indexOf(sfrzcertid) < 0) {
                	closeWindows();
                }
            }
        });
    }

}

function closeWindows() {

	if(top.location!=self.location)
	{
		top.opener = null;
		top.open('', '_self');
		top.close();
	}
	else
	{
		window.opener = null;
		window.open('', '_self');
		window.close();
	}
}

function SignOut(homepage,info) {
    var sfrzcertid = document.getElementById("hdCertId").value;
    if (sfrzcertid != '') {
        if (info.indexOf(sfrzcertid) >= 0) {
            window.location.href = homepage;
        }
    }
}



/////const //////////////////////////

/////define object  /////////////////////////////////


try {
    var oUtil = new ActiveXObject("BJCASecCOM.Util");
    document.writeln("<OBJECT classid=\"clsid:FCAA4851-9E71-4BFE-8E55-212B5373F040\" height=1 id=bjcactrl  style=\"HEIGHT: 1px; LEFT: 10px; TOP: 28px; WIDTH: 1px\" width=1 VIEWASTEXT>");
    document.writeln("</OBJECT>");
    bjcactrl.GetUserListPnp();
}
catch (e) {
    //alert("ActiveXObject BJCASECCOM.dll可能没有注册成功！"+e.message);
}


var errorMsg = "请确认插入正确的证书介质或重新登录系统！";
/////组件接口转换为脚本接口////////////////////////
var g_xmluserlist;

//得到用户列表
function GetUserList() {

    try {
        g_xmluserlist = bjcactrl.GetUserList();
    }
    catch (e) {
        g_xmluserlist = "";
        //	    alert(e.message);

    }
    return g_xmluserlist;

}

//得到用户信息
function GetCertBasicinfo(Cert, Type) {

    return bjcactrl.GetCertInfo(Cert, Type);

}
function GetExtCertInfoByOID(Cert, oid) {

    return bjcactrl.GetCertInfoByOid(Cert, oid);
}

//登录
function Login(strFormName, strContainerName, strPin) {
    var ret;
    var objForm = eval(strFormName);

    if (objForm == null) {
        alert("Form Error");
        return false;
    }
    if (strPin == null || strPin == "") {
        alert("请输入Key的保护口令");
        return false;
    }
    //Add a hidden item ...
    var strSignItem = "<input type=\"hidden\" name=\"UserSignedData\" value=\"\">";
    if (objForm.UserSignedData == null) {
        objForm.insertAdjacentHTML("BeforeEnd", strSignItem);
    }
    var strCertItem = "<input type=\"hidden\" name=\"UserCert\" value=\"\">";
    if (objForm.UserCert == null) {
        objForm.insertAdjacentHTML("BeforeEnd", strCertItem);
    }
    var strContainerItem = "<input type=\"hidden\" name=\"ContainerName\" value=\"\">";
    if (objForm.ContainerName == null) {
        objForm.insertAdjacentHTML("BeforeEnd", strContainerItem);
    }


    if (!bjcactrl.UserLogin(strContainerName, strPin)) {
        var extLib = bjcactrl.GetUserInfo(strContainerName, 15);

        var remainTimes = bjcactrl.GetBjcaKeyParam(extLib, 8);
        if (Number(remainTimes) <= 0) {
            alert("您输入了10次错误密码,证书已经被锁死,请尽快联系技术人员解锁");
            return false;
        }
        alert("密码错误,剩余密码重试次数：" + remainTimes);
        return false;
    }

    //strClientSignedData = SignedData(strContainerName,strServerRan);
    //objForm.UserSignedData.value = strClientSignedData;

    var varCert = GetSignCert(strContainerName);
    objForm.UserCert.value = varCert;
    objForm.ContainerName.value = strContainerName;

    return alertValidDay(varCert);
}

//根据证书惟一标识，获取Base64编码的证书字符串。指定获取加密（交换）证书。
function GetExchCert(strContainerName) {
    var UserCert = bjcactrl.ExportExChangeUserCert(strContainerName);

    return UserCert;
}

//签名证书
function GetSignCert(strContainerName) {
    var UserCert = bjcactrl.ExportUserCert(strContainerName);

    return UserCert;
}


//xml签名
function SignedDataXML(signdata, ContainerName) {
    return bjcactrl.SignDataXML(ContainerName, signdata);
}

function SignedData(sContainerName, sInData) {
    if (sContainerName != null)
        return bjcactrl.SignData(sContainerName, sInData);
    else
        return "";
}


function VerifySignedData(cert, indata, signvalue) {
    return bjcactrl.VerifySignedData(cert, indata, signvalue);

}


function PubKeyEncrypt(exchCert, inData) {
    try {
        var ret = bjcactrl.PubKeyEncrypt(exchCert, inData);
        return ret;
    }
    catch (e) {

    }
}


function PriKeyDecrypt(sContainerName, inData) {
    try {
        var ret = bjcactrl.PriKeyDecrypt(sContainerName, inData);
        return ret;
    }
    catch (e) {

    }
}


function EncryptData(sKey, inData) {
    try {
        var ret = bjcactrl.EncryptData(sKey, inData);
        return ret;
    }
    catch (e) {

    }
}


function DecryptData(sKey, inData) {
    try {
        var ret = bjcactrl.DecryptData(sKey, inData);
        return ret;
    }
    catch (e) {

    }
}

function GenerateRandom(RandomLen) {

    return bjcactrl.GenRandom(RandomLen);
}


//文件签名 返回签名数据
function SignFile(sFileName, sContainerName) {
    return bjcactrl.SignFile(sContainerName, sFileName)
}

function VerifySignFile(sFileName, sCert, SignData) {
    return bjcactrl.VerifySignedFile(sCert, sFileName, SignData);
}

//修改密码
function ChangeUserPassword(strContainerName, oldPwd, newPwd) {
    return bjcactrl.ChangePasswd(strContainerName, oldPwd, newPwd);
}

//xml签名
function SignedDataXML(signdata, ContainerName) {
    return bjcactrl.SignDataXML(ContainerName, signdata);
}

//xml验证签名
function VerifySignXML(signxml) {
    return bjcactrl.verifySignedDataXML(signxml);
}

//p7签名
function SignByP7(CertID, InData) {
    return bjcactrl.SignDataByP7(CertID, InData)
}

//验证p7签名
function VerifyDatabyP7(P7Data) {
    return bjcactrl.VerifySignedDatabyP7(P7Data);
}

//p7数字信封加密
function EncodeP7Enveloped(Cert, InData) {
    return bjcactrl.EncodeP7EnvelopedData(Cert, InData);
}

//p7数字信封解密
function DecodeP7Enveloped(CertID, InData) {
    return bjcactrl.DecodeP7EnvelopedData(CertID, InData);
}

function base64EncodeFile(sFilePath) {

    return oUtil.base64EncodeFile(sFilePath);
}

function alertValidDay(cert) {
    var endDate = GetCertBasicinfo(cert, 12);
    var nowDate = new Date().Format("yyyy/MM/dd");
    var days = (Date.parse(endDate) - Date.parse(nowDate)) / (1000 * 60 * 60 * 24);
    if (days <= 60 && days > 0) {
        alert("您的证书还有" + days + "天过期,请尽快更新");
    }
    if (days <= -45) {
        alert("您的证书已经过期" + (-days) + "天,超过了最后使用期限,请尽快更新");
        return false;
    }
    if (days <= 0) {
        alert("您的证书已经过期" + (-days) + "天,请尽快更新");
        return false;
    }
    return true;
}


// 日期类型格式化方法的定义
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


//页面载入读取User列表
function GetList(strListID) {

    var strTemp;
    var strOption;
    var len;
    var strName;
    var strUniqueID;
    var objListID = eval(strListID);

    strTemp = GetUserList();
    g_xmluserlist = "";
    while (1) {
        i = strTemp.indexOf("&&&");
        if (i <= 0) {
            break;
        }
        strOption = strTemp.substring(0, i);
        strName = strOption.substring(0, strOption.indexOf("||"));
        strUniqueID = strOption.substring(strOption.indexOf("||") + 2, strOption.length);
        var objItem = new Option(strName, strUniqueID)
        objListID.add(objItem);
        g_xmluserlist = g_xmluserlist + strName + "||";
        g_xmluserlist = g_xmluserlist + strUniqueID + "&&&";

        len = strTemp.length;
        strTemp = strTemp.substring(i + 3, len);
    }
    console.log(objListID);
    var objListID = null;
}

//登录页响应Key插拔动作
function GetList_pnp(strListID) {
    var i;
    var strOption;
    var strName;
    var strUniqueID;
    var objListID = eval(strListID);
    var n = objListID.length;

    for (i = 0; i < n; i++)
        objListID.remove(0);

    var strTemp = bjcactrl.GetUserListPnp();
    while (1) {
        i = strTemp.indexOf("&&&");
        if (i <= 0) {
            break;
        }
        strOption = strTemp.substring(0, i);
        strName = strOption.substring(0, strOption.indexOf("||"));
        strUniqueID = strOption.substring(strOption.indexOf("||") + 2, strOption.length);
        var objItem = new Option(strName, strUniqueID)
        objListID.add(objItem);
        len = strTemp.length;
        strTemp = strTemp.substring(i + 3, len);

        var strContainerName = document.getElementById('UserList').value;
        var varCert = GetSignCert(strContainerName);

    }
}

//业务页面响应拔Key事件
function ChangeDrivers() {

	if(top.location!=self.location)
	{
		top.opener = null;
		top.open('', '_self');
		top.close();
	}
	else
		{
		window.opener = null;
		window.open('', '_self');
		window.close();
		}
}


//登录
function LoginNew(strFormName) {
    console.log("loginnew");
    //var scripts = document.getElementsByTagName('script');

    //var currentScript = scripts[scripts.length - 1];

    //var logincodeStr = currentScript.getAttribute('src').split("=")[1];

    var ret;
    var objForm = eval(strFormName);

    //获取当前证书容器名
    var strContainerName = objForm.UserList.value;
    //获取当前证书密码
    var strPin = objForm.txtpin.value;

    if (objForm == null) {
        alert("Form Error");
        return false;
    }

    var obj = objForm.UserList;
    var certcount = 0;
    for (i = 0; i < obj.length; i++) {
        if (obj[i].value != "")
            certcount++;
    }


    if (certcount == 0) {
        alert("请插入证书！");
        return false;
    }

    if (certcount > 1) {
        alert("您只能插入一张证书！");
        return false;
    }

    if (strContainerName == "") {
        alert("请选择一证通证书！");
        return false;
    }

    if (strPin == null || strPin == "") {
        alert("请输入Key的保护口令");
        return false;
    }


    if (strPin.length < 4 || strPin.length > 16) {
        alert("密码长度应该在4-16位之间");
        return false;
    }

    //Add a hidden item ...
    var strSignItem = "<input type=\"hidden\" id=\"UserSignedData\" name=\"UserSignedData\" value=\"\">";
    if (objForm.UserSignedData == null) {
        objForm.insertAdjacentHTML("BeforeEnd", strSignItem);
    }
    var strCertItem = "<input type=\"hidden\" name=\"UserCert\" value=\"\">";
    if (objForm.UserCert == null) {
        objForm.insertAdjacentHTML("BeforeEnd", strCertItem);
    }
    var strContainerItem = "<input type=\"hidden\" name=\"ContainerName\" value=\"\">";
    if (objForm.ContainerName == null) {
        objForm.insertAdjacentHTML("BeforeEnd", strContainerItem);
    }
    var LoginCode = "<input type=\"hidden\" id=\"LoginCode\" name=\"LoginCode\" value=\"\">";
    if (objForm.LoginCode == null) {
        objForm.insertAdjacentHTML("BeforeEnd", LoginCode);
    }


    if (!bjcactrl.UserLogin(strContainerName, strPin)) {
        var extLib = bjcactrl.GetUserInfo(strContainerName, 15);

        var remainTimes = bjcactrl.GetBjcaKeyParam(extLib, 8);
        if (Number(remainTimes) <= 0) {
            alert("您输入了10次错误密码,证书已经被锁死,请尽快联系技术人员解锁");
            return false;
        }
        alert("密码不正确,剩余密码重试次数：" + remainTimes);
        return false;
    }
    var logincodeStr = guid();
    objForm.LoginCode.value = logincodeStr;
    strClientSignedData = SignedData(strContainerName, logincodeStr);

    objForm.UserSignedData.value = strClientSignedData;
    var varCert = GetSignCert(strContainerName);
    objForm.UserCert.value = varCert;
    objForm.ContainerName.value = strContainerName;

    if (alertValidDay(varCert)) {
        $.ajax({
            url: "http://"+ip+":8003/certserver/api/CertityAuth/GetCertAuthen",
            type: "get",
            dataType: "jsonp",
            jsonp: "callback",
            data: {
                loginCode: logincodeStr,
                clientSignData: strClientSignedData,
                userKey: varCert,
                sysAppid: "0004"
            },
            success: function (res) {
                if (res.statusCode == 200) {
                    var url = "welcome.html?containerName=" + strContainerName;
                    window.location.href = url;
                }
                else {
                    $("#login_button").html('登录');
                    $("#txtpin").html("");
                    document.getElementById("login_button").removeAttribute("disabled");
                }
            },
            error: function (e) {
                console.log(e);
            }
        })

    }
}

//验证老的身份认证并跳转业务系统
function ValidayOldSfrz(url, appid, posttype) {
    //alert(getQueryVariable("containerName"));
   // getQueryVariable("containerName");
    var containerName = getQueryVariable("containerName");
    ////获取当前证书容器名 containerName
    var logincodeStr = guid();
    strClientSignedData = SignedData(containerName, logincodeStr);
    var varCert = GetSignCert(containerName);

    if (alertValidDay(varCert)) {

        $.ajax({
            url: "http://"+ip+":8003/certserver/api/CertityAuth/GetCertAuthen",
            type: "get",
            dataType: "jsonp",
            jsonp: "callback",
            data: {
                loginCode: logincodeStr,
                clientSignData: strClientSignedData,
                userKey: varCert,
                sysAppid: appid
            },
            success: function (res) {
                if (res.statusCode == 200) {
                    postOpenWindow(url, {
                        "hdTicket": res.data.ticket,
                        "hdCert": res.data.cert,
                        "hdCertId": res.data.certid,
                        "hdappid": appid,
                        "hid_username": res.data.username,
                        "hdqrcode": 0
                    }, posttype, S4());
                }
                else {
                    alert('身份认证失败，提示信息：' + res.statusCode);

                }
            }
        })
    }
}

function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);

}
function guid() {

    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());

}

function GetRequest() {
    var url = location.search;
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

















