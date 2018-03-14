try {
    var grabber = new Grabber();// 创建
    grabber.show();// 显示
    grabber.loadUrl('http://www.taobao.com', function () {
        // 开始garb

    }, function (reason) {
        // 处理错误，日志、提示

    });// 加载URL，分别传入加载成功和加载失败的回调函数

    window.task.end();// 通知命令解释器，命令执行完毕，也就是说，所有的任务描述最后都必须加上这一条命令调用，只有加上此命令调用后，命令解释器才会继续向下执行其他的一个任务
} catch (e) {// 程序在执行过程中发生了错误
    Log.error(e.message);// 记录错误信息
    Log.error("任务终止,发生严重错误");
    Task.sendError(new ErrorModel(Log.getAllLogs()));
    window.task.end();
}