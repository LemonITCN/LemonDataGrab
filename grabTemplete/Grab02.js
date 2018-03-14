var myBrowser = new Browser();
myBrowser.show();
myBrowser.operate.loadURL('http://www.taobao.com' , function(){
	var itemCount = myBrowser.dataGet.getChildrenCount('body > div.cup.J_Cup > div.top.J_Top > div > div.ta-rect > div > div > div > div.search-ft.J_SearchFt > div.J_TbSearchContent.J_HotWord > div > div > div.search-hots-fline');
    for (var i = 0 ; i < itemCount ; i ++){ 
    	var nameStr = myBrowser.dataGet.getInnerText('body > div.cup.J_Cup > div.top.J_Top > div > div.ta-rect > div > div > div > div.search-ft.J_SearchFt > div.J_TbSearchContent.J_HotWord > div > div > div.search-hots-fline > a:nth-child(' + (i + 1) + ')');
    	var urlStr = myBrowser.dataGet.getADomURL('body > div.cup.J_Cup > div.top.J_Top > div > div.ta-rect > div > div > div > div.search-ft.J_SearchFt > div.J_TbSearchContent.J_HotWord > div > div > div.search-hots-fline > a:nth-child(' + (i + 1) + ')');
    	DataCollection.put('itemName' +  (i + 1)  , nameStr);
    	DataCollection.put('itemUrl' +  (i + 1)  , urlStr);
    }
    Communication.call('task' , 'success');
} , function(){
    console.log('failed');
});


var myBrowser=new Browser();myBrowser.show();myBrowser.operate.loadURL('http://www.taobao.com',function(){var itemCount=myBrowser.dataGet.getChildrenCount('body > div.cup.J_Cup > div.top.J_Top > div > div.ta-rect > div > div > div > div.search-ft.J_SearchFt > div.J_TbSearchContent.J_HotWord > div > div > div.search-hots-fline');for(var i=0;i<itemCount;i++){var nameStr=myBrowser.dataGet.getInnerText('body > div.cup.J_Cup > div.top.J_Top > div > div.ta-rect > div > div > div > div.search-ft.J_SearchFt > div.J_TbSearchContent.J_HotWord > div > div > div.search-hots-fline > a:nth-child('+i+')');var urlStr=myBrowser.dataGet.getADomURL('body > div.cup.J_Cup > div.top.J_Top > div > div.ta-rect > div > div > div > div.search-ft.J_SearchFt > div.J_TbSearchContent.J_HotWord > div > div > div.search-hots-fline > a:nth-child('+i+')');DataCollection.put('itemName'+i,nameStr);DataCollection.put('itemUrl'+i,urlStr)}Communication.call('task','success')},function(){console.log('failed')});

// 打开淘宝首页，获取顶部搜索框下面的推荐搜索词的标题和对应超链接URL


var b = new Browser();
b.show();
b.operate.loadURL('http://www.taobao.com',function(){Log.info(1);},function(){Log.info(2)})