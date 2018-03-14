var myBrowser = new Browser();
myBrowser.show();
myBrowser.operate.loadURL('https://s.taobao.com/search?q=iPhone6' , function(){
	myBrowser.operate.scrollToPosition(0,5000);
    setTimeout('grabFunc()' , 2000);
} , function(){
    console.log('failed');
});
function grabFunc(){
	for (var i = 0 ; i < 30 ; i ++){ 
    	var urlStr = myBrowser.dataGet.getADomURL('#J_itemlistListItem' + i + ' > div.col.col-2 > p > a');
    	DataCollection.put('data' + i , urlStr);
    }
    Communication.call('task' , 'success');
}

var myBrowser = new Browser();
myBrowser.show();
myBrowser.operate.loadURL('https://s.taobao.com/search?q=iPhone6' , function(){
	myBrowser.executeJavaScript("document.querySelector('#J_itemlistListItem0') != null");
	for (var i = 0 ; i < 30 ; i ++){ 
    	var urlStr = myBrowser.dataGet.getADomURL('#J_itemlistListItem' + i + ' > div.col.col-2 > p > a');
    	DataCollection.put('data' + i , urlStr);
    }
    Communication.call('task' , 'success');
} , function(){
    console.log('failed');
});


var myBrowser=new Browser();myBrowser.show();myBrowser.operate.loadURL('https://s.taobao.com/search?q=iPhone6',function(){for(var i=0;i<30;i++){var urlStr=myBrowser.dataGet.getADomURL('#J_itemlistListItem'+i+' > div.col.col-2 > p > a');DataCollection.put('data'+i,urlStr)}Communication.call('task','success')},function(){console.log('failed')});

// 打开iPhone6的搜索结果页面。然后获取结果列表中的前30条信息