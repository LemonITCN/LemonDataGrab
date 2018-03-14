var brow = new Browser();
brow.show();
brow.operate.loadURL('http://www.taobao.com' , function(){
	brow.operate.mouseoverDom('body > div.screen-outer.clearfix > div.main > div.tbh-service.J_Module.tbh-loaded > div > ul > li:nth-child(1)');
	setTimeout(function(){
		var items = brow.dataGet.getChildren('body > div.screen-outer.clearfix > div.main > div.tbh-service.J_Module.tbh-loaded > div > div.service-float > div > div.service-fi-links > div:nth-child(1) > p');
		Log.success('子元素获取成功，子元素总数量：' + items.length);
		for (var i = 0 ; i < items.length ; i ++){
			Log.info(items[i].innerText);
			Log.info(items[i].href);
			DataCollection.put(items[i].innerText,items[i].href);
		}
		Communication.call('task' , 'success');
	},2000);
} , function(){

});

var brow=new Browser();brow.show();brow.operate.loadURL('http://www.taobao.com',function(){brow.operate.mouseoverDom('body > div.screen-outer.clearfix > div.main > div.tbh-service.J_Module.tbh-loaded > div > ul > li:nth-child(1)');setTimeout(function(){var items=brow.dataGet.getChildren('body > div.screen-outer.clearfix > div.main > div.tbh-service.J_Module.tbh-loaded > div > div.service-float > div > div.service-fi-links > div:nth-child(1) > p');Log.success('子元素获取成功，子元素总数量：'+items.length);for(var i=0;i<items.length;i++){Log.info(items[i].innerText);Log.info(items[i].href);DataCollection.put(items[i].innerText,items[i].href)}Communication.call('task','success')},2000)},function(){});

// 获取淘宝首页主题市场第一行鼠标悬浮之后弹出面板的第一个区域中所有超链接名称和URL的脚本