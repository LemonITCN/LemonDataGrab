var brow = new Browser();// 创建一个浏览器
brow.getId();//获取浏览器ID
brow.show();//显示这个浏览器
brow.hide();//隐藏这个浏览器
brow.close();//关闭这个浏览器
brow.setSize(width, height);//设置浏览器窗口的大小尺寸
brow.setPosition(x,y);//设置浏览器窗口的位置
brow.executeJavaScript(code);//在浏览器中执行指定的js代码

brow.operate.loadURL(url , successCallback , failedCallback);//加载URL
brow.operate.setDomAttribute(domSelector , attributeName , attributeValue);//设置指定Dom元素的Attribute参数
brow.operate.setDomProperty(domSelector , propertyName , propertyValue);//设置指定Dom元素的属性
brow.operate.invokeDomFunction(domSelector, functionName , attributes);//触发指定Dom元素的指定函数
brow.operate.invokeDomMouseAction(domSelector , actionName);//触发指定Dom元素的鼠标事件,如点击/鼠标悬浮等操作 ‘click’, ‘mousedown’, ‘mousemove’, ‘mouseout’, ‘mouseover’, ‘mouseup’
brow.operate.fillInputText(domSelector , fillText);//在指定的Input文本控件中填写值
brow.operate.selectItem(domSelector , selectedIndex);//选择指定的下拉菜单的下拉项目
brow.operate.checkedItem(domSelector , checked);//选中指定radio或checkbox
brow.operate.submitForm(domSelector);//触发提交指定的表单
brow.operate.sleep(sleepInterval);//休眠指定的时间之后再继续执行
brow.operate.scrollToPosition(x , y);//让当前页面滚动到指定的位置
brow.operate.clickDom(domSelector);//点击指定的Dom元素
brow.operate.mouseoverDom(domSelector);//触发指定Dom元素的鼠标悬浮事件

brow.dataGet.getDomAttribute(domSelector , attributeName);//获取指定Dom元素的attribute参数值
brow.dataGet.getDomProperty(domSelector, propertyName);// 获取指定Dom元素的属性值
brow.dataGet.getInnerHTML(domSelector);//获取指定元素的innerHTML
brow.dataGet.getOuterHTML(domSelector);//获取指定元素的outerHTML
brow.dataGet.getInnerText(domSelector);//获取指定元素的innerText
brow.dataGet.getURL();//获取当前采集数据的界面的URL
brow.dataGet.getImgDomURL(domSelector);//获取指定图片Dom元素的图片URL
brow.dataGet.getADomURL(domSelector);//获取指定A标记的的链接URL
brow.dataGet.getChildren(domSelector);//获取指定节点的所有的子元素节点数组
brow.dataGet.getChildrenCount(domSelector);//获取指定节点的所有的子元素节点数组中的元素数量


Communication.call(name, callbackData);//调用指定的通讯handler

DataCollection.put(key, value);//将指定的键值对数据放入到数据收集池中
DataCollection.get(key);//将数据收集池中的指定数据取出
DataCollection.remove(key);//将数据收集池中的指定的数据删除
DataCollection.removeAll();//删除数据收集池中的所有数据

Log.success(info);//打印成功的日志
Log.error(info);//打印错误信息的日志
Log.info(info);//打印普通的信息日志
Log.warning(info);//打印警告信息的日志

Util.createUUID();//创建一个随机的UUID
Util.getUnixTimeStamp();//获取当前的系统时间戳
