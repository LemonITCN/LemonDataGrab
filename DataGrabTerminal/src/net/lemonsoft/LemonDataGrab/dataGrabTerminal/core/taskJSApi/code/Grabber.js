function Grabber() {

    /**
     * 采集器标识
     * @type {string} 采集器的标识字符串
     */
    var identity = Util.createUUID();

    /**
     * 获取采集器的身份Identity
     * @returns {string} 采集器标识字符串
     */
    this.getIdentity = function () {
        return identity;
    };

    /**
     * 数据采集类
     * @type {DataGrab}
     */
    this.dataGrab = new DataGrab(identity);

    /**
     * 网页操作类
     * @type {Operate}
     */
    this.operate = new Operate(identity);

    /**
     * 显示数据采集面板
     */
    this.show = function () {
        Log.info("showshow~");
        window.grabber.show(identity);
    };

    /**
     * 隐藏数据采集面板
     */
    this.hide = function () {
        window.grabber.hide(identity);
    };

    /**
     * 删除采集数据采集器,那么将不可用
     */
    this.delete = function () {
        window.grabber.delete(identity);
        identity = "";
    };

    window.grabber.create(identity);// 调用原生创建一个采集器
    // window.grabber.show(identity);
    this.show();
    // window.grabber.load(identity);
    // this.operate.loadURL("http://www.taobao.com" , 60 , function () {
    //     Log.info("succ");
    // } , function () {
    //     Log.error("fai");
    // });
    // window.grabber.loadURL(identity , "http://www.taobao.com" , 60 , function () {
    //     Log.info("success load@");
    // } , function () {
    //     Log.error("error when load");
    // });
}