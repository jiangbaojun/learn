/**
 * 注意，此处选项设置是针对整个系统的
 */
VSTOptions = {
		/******************** 自定义组件默认扩展 *********************************/
		"custom": {
			/** 主题相关 */
			style:{
				//主题文件存放路径
				path: "/css/basic/layout",
				//主题设置信息通过cookie缓存，主题cookie缓存的有效时间（毫秒值，默认2592000000），null永不失效
				cacheMsec: null
			},
			/**  绑定代码表数据（dmList）的下拉框 */
			combobox:{
				//是否默认自动初始化下拉框
				defauleInit: true,
				//默认选择第一个选项
				defauleSelectFirst: true,
				//代码表数据中的字段，与combobox中的valueField属性对应
				valueField: 'DM',
				//代码表数据中的字段，与combobox中的textField属性对应
				textField: 'MC'

			},
			/** 快捷键tab焦点控制,将焦点控制在document范围内 */
			tabFocusScope:{
				//开关
				enable: true,
				//是否循环切换
				cycleFocus: true
			},
			/** 在form表单内，使用快捷键enter切换焦点 */
			enterSwitchFocus:{
				//开关
				enable: true,
				//是否循环切换
				cycleFocus: true
			},
			/** 表单 */
			form:{
				//表单项.vst-form-span标签最大字数，查过该字数将会换行
				maxFontNum: 6,
				//表单项水平间距
				spaceH: 20,
				//表单项垂直间距
				spaceV: 15,
				//是否强制对齐表单项.vst-form-span标签
				labelAlign: true,
				//.vst-form-span标签文字对齐方式。left、right、center
				labelPosition: "right"
			},
			/** 文件 */
			fileModel:{
				//是否一次可以选择多个文件
				upload_multiple: true,
				//是否在组件中添加文件信息的隐藏input框
				upload_input_info: true,
				//版本(v1、v2)
				version: "v1"
			},
			/** 系统配置 */
			system:{
				//登录成功后是否在独立窗口打开主页面
				ownWinOpen: false,
				//双击主页面logo切换全屏
				fullScreenOpen: true,
				//退出登录后，是否关闭窗口
				logoutClose: false
			}
		},
		/****************************** easyui默认配置扩展 *******************************************/
		"easyui": {
			/** 下拉 */
			combobox: {
				defaults: {
					//方向快捷键循环选择
					keySelectCycle: true,
					//方向快捷键选择是否显示下拉面板
					keySelectPannel: true,
				}
			},
			/** panel面板 */
			panel: {
				defaults: {
					//弹出窗口尺寸
					sizeMap: {
						"d":{width:820}
					}
				}
			}
		}
}

