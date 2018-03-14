/**
 * 加载指定的采集地址URL
 * AID: 1a
 * 
 * @param grabURL 要加载的URL地址
 * @param timeoutInterval 加载URL的最长允许超时时间
 * @param successFunction URL加载成功且完毕时候的回调函数
 * @param failedFunction URL加载失败(包括超时)时候的回调函数
 */
function loadURL(grabURL , timeoutInterval , successFunction , failedFunction) {}

/**
 * 在指定的Input文本控件中填写值
 * AID: 1b
 * 
 * @param domSelector 要填写值的文本输入控件的css选择器字符串
 * @param fillText 要填写的文本值
 */
function fillInputText(domSelector , fillText) {}

/**
 * 选择指定的下拉菜单的下拉项目
 * AID: 1c
 *
 * @param domSelector 要选择项目的css选择器字符串
 * @param selectedIndex 要让其选择的项目的索引
 */
function selectItem(domSelector) {}

/**
 * 选中指定radio或checkbox
 * AID: 1d
 *
 * @param domSelector 要选择项目的css选择器字符串
 * @param checked 设置是选中还是取消选中,boolean值
 */
function checkedItem(domSelector) {}

/**
 * 触发提交指定的表单
 * AID: 1e
 * 
 * @param domSelector 要提交的表单的css选择器字符串
 */
function submitForm(domSelector) {}

/**
 * 休眠指定的时间之后再继续执行
 * AID: 1f
 * 
 * @param sleepInterval 休眠的时间,单位ms
 */
function sleep(sleepInterval) {}

/**
 * 让当前页面滚动到指定的位置
 * AID: 1g
 * 
 * @param x 让页面要滚动到的位置的x坐标
 * @param y 让页面要滚动到的位置的y坐标
 */
function scrollToPosition(x , y) {}

/**
 * 点击指定的Dom元素
 * AID: 1h
 * 
 * @param domSelector 要点击的Dom元素的css选择器字符串
 */
function clickDom(domSelector) {}

/**
 * 触发指定Dom元素的鼠标悬浮事件
 * AID: 1i
 * 
 * @param domSelector 要触发hover事件的dom元素的css选择器字符串
 */
function mouseoverDom(domSelector) {}

/**
 * 移动系统鼠标箭头到指定的屏幕位置
 * AID: 1i [为了辅助完成鼠标悬浮事件的完成]
 * 
 * @param x 要将鼠标箭头移动到的位置的屏幕x坐标
 * @param y 要将鼠标箭头移动到的位置的屏幕y坐标
 */
function moveMouseToPosition(x , y) {}

/**
 * 获取指定的Dom元素在采集器浏览器中的位置
 * AID: 1i [为了辅助完成鼠标悬浮事件的完成]
 * 
 * @param domSelector 要获取位置的Dom元素的css选择器字符串
 * @returns {x , y} 包含x/y两个坐标的Object
 */
function getDomPositionInGrabber(domSelector) {}

/**
 * 获取指定Dom元素的InnerHTML
 * AID: 2a
 * 
 * @param domSelector 要获取InnerHTML的Dom元素的css选择器字符串
 * @returns String InnerHTML字符串
 */
function getDomInnerHTML(domSelector) {}

/**
 * 获取指定Dom元素的OuterHTML
 * AID: 2b
 * 
 * @param domSelector 要获取OuterHTML的Dom元素的css选择器字符串
 * @returns String OuterHTML字符串
 */
function getDomOuterHTML(domSelector) {}

/**
 * 获取指定Dom元素的InnerText
 * AID: 2c
 * 
 * @param domSelector 要获取InnerText的Dom元素的css选择器字符串
 * @returns String InnerText字符串
 */
function getDomInnerText(domSelector) {}

/**
 * 获取当前采集数据的界面的URL
 * AID: 2d
 * 
 * @returns String URL字符串
 */
function getURL() {}

/**
 * 获取指定图片Dom元素的图片URL
 * AID: 2e
 * 
 * @param domSelector 要获取图片URL的图片Dom元素的css选择器
 * @returns String 图片URL字符串
 */
function getImgDomURL(domSelector) {}

/**
 * 获取指定A标记的的链接URL
 * AID: 2f
 * 
 * @param domSelector 要获取超链接URL的A标记的css选择器字符串
 * @returns String A标记的链接的URL字符串
 */
function getADomURL(domSelector) {}