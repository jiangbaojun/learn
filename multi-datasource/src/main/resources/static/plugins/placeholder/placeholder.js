/**
 * 源码名称：placeholder.js
 * 实现功能：让低版本的浏览器(主要是IE8)支持placeholder
 * 作者主页：http://www.miaoqiyuan.cn/
 * 联系邮箱：mqycn@126.com
 * 使用说明：http://www.miaoqiyuan.cn/p/placeholder-js
 * 最新版本：http://git.oschina.net/mqycn/placeholder.js
 */

(function() {
    //仅在不支持 placeholder 的时候执行
    if (!('placeholder' in document.createElement('input'))) {

        var listenerName = 'attachEvent';
        var listenerPrefix = 'on';
        if ('addEventListener' in window) {
            listenerName = 'addEventListener';
            listenerPrefix = '';
        }

        window[listenerName](listenerPrefix + 'load', function() {
            var placeholderListener = {
                //添加输入项
                add: function(tagName) {
                    var list = document.getElementsByTagName(tagName);
                    for (var i = 0; i < list.length; i++) {
                        this.render(list[i]);
                    }
                    return this;
                },
                //渲染
                render: function(dom) {
                    var text = dom.getAttribute('placeholder');
                    if (!!text && !$(dom).val()) {
                        this.attachEvent(dom, this.getDiv(dom, text));
                    }
                },
                //设置样式
                getDiv: function(dom, text) {
                    var div = document.createElement('div');

                    div.style.position = 'absolute';
                    div.style.width = this.getPosition(dom, 'width') + 'px';
                    div.style.height = this.getPosition(dom, 'height') + 'px';
                    div.style.left = this.getPosition(dom, 'left') + 'px';
                    div.style.top = this.getPosition(dom, 'top') + 'px';
                    div.style.color = $(dom).css("color");
                    div.style.textIndent = '5px';
                    div.style.zIndex = 999;
                    div.style.background = dom.style.background;
                    div.style.border = dom.style.border;
                    div.style.cursor = 'text';
                    div.innerHTML = text;
                    div.className = "vst-placeholder";

                    if ('TEXTAREA' == dom.tagName.toUpperCase()) {
                        div.style.lineHeight = '35px';
                    } else {
                        div.style.lineHeight = div.style.height;
                    }
                    document.getElementsByTagName('body')[0].appendChild(div);
                    return div;
                },
                //计算当前输入项目的位置
                getPosition: function(dom, name, parentDepth) {
                	var offsetVal = 0;
                	if(name=="width"){
                		offsetVal = $(dom).width();
                	}
                	if(name=="height"){
                		offsetVal = $(dom).height();
                	}
                	if(name=="left" ||name=="top"){
                		var offsetObj = $(dom).offset();
                		offsetVal = offsetObj[name]+2;
                	}
                    var parentDepth = parentDepth || 0;
                    if (!offsetVal && parentDepth < 3) {
                        offsetVal = this.getPosition(dom.parentNode, name, ++parentDepth);
                    }
                    return offsetVal;
                },
                //添加事件
                attachEvent: function(dom, div) {

                    //激活时，隐藏 placeholder
                    dom[listenerName](listenerPrefix + 'focus', function() {
                        div.style.display = 'none';
                    });

                    //失去焦点时，隐藏 placeholder
                    dom[listenerName](listenerPrefix + 'blur', function(e) {
                        if (e.srcElement.value == '') {
                            div.style.display = '';
                        }
                    });

                    //placeholder 点击时，对应的输入框激活
                    div[listenerName](listenerPrefix + 'click', function(e) {
                        e.srcElement.style.display = 'none';
                        dom.focus();
                    });
                },

            };

            //防止在 respond.min.js和html5shiv.min.js之前执行
            setTimeout(function() {
                placeholderListener.add('input').add('textarea');
            }, 0);
        });
    }
})();