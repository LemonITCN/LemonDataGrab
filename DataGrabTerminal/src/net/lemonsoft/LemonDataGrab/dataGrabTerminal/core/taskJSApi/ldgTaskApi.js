function Task() {
}
/**
 * 任务标识 - taskIdentity
 * @type {string}
 */
Task.identity = "";

window.onload = function () {
    Terminal.success("┏━━━━━━━━━━━━━━━━━━━━━━━┓");
    Terminal.success("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎使用LemonDataGrab分布式数据采集器");
    Terminal.success("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Copyright （c） 2016&nbsp;&nbsp;&nbsp;1em0nsOft ");
    Terminal.success("┗━━━━━━━━━━━━━━━━━━━━━━━┛");
    Terminal.info("Lemon Distributed data acquisition system start success!");
};
function DataGrab(grabberIdentity) {

    /**
     * 作用于的采集器
     */
    var grabberIdentity = grabberIdentity;

    /**
     * 在数据采集容器中执行指定的JS代码,并且返回代码执行的返回值
     * @param jsCode 要在采集容器中执行的JS代码
     */
    this.executeJS = function (jsCode) {
        return window.grabber.executeJS(grabberIdentity , jsCode);
    };

    /**
     * 获取指定Dom元素的attribute参数值
     * @param domSelector 指定元素的选择器
     * @param attributeName 要获取的属性名
     * @returns {string} 获取到的属性值
     */
    this.getDomAttribute = function (domSelector , attributeName) {
        return this.executeJS("document.querySelector('" + domSelector + "').getAttribute('" + attributeName + "')");
    };

    /**
     * 获取指定Dom元素的属性值
     * @param domSelector
     * @param propertyName
     */
    this.getDomProperty = function (domSelector, propertyName) {
        return this.executeJS("document.querySelector('" + domSelector + "')." + propertyName);
    };

    /**
     * 获取指定元素的innerHTML
     * @param domSelector 要获取innerHTML的元素的选择器
     * @returns {string} 获取到的innerHTML
     */
    this.getInnerHTML = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector , "innerHTML");
            Log.success("根据脚本命令获取指定元素的innerHTML成功");
            return result;
        }catch (e){
            Log.warn("获取指定元素的innerHTML失败:" + e);
            return null;
        }
    };

    /**
     * 获取指定元素的outerHTML
     * @param domSelector 要获取outerHTML的元素的选择器
     * @returns {string} 获取到的outerHTML
     */
    this.getOuterHTML = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector , "outerHTML");
            Log.success("根据脚本命令获取指定元素的outerHTML成功");
            return result;
        }catch (e){
            Log.warn("获取指定元素的outerHTML失败:" + e);
            return null;
        }
    };

    /**
     * 获取指定元素的innerText
     * @param domSelector 要获取innerText的元素的选择器
     * @returns {string} 获取到的outerHTML
     */
    this.getInnerText = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector , "innerText");
            Log.success("根据脚本命令获取指定元素的innerText成功");
            return result;
        }catch (e){
            Log.warn("获取指定元素的innerText失败:" + e);
            return null;
        }
    };

    /**
     * 获取当前采集数据的界面的URL
     * AID: 2d
     *
     * @returns String URL字符串
     */
    this.getURL = function () {
        try {
            var result = this.executeJS("location.href");
            Log.success("根据脚本命令获取当前采集数据的界面的URL成功");
            return result;
        }catch (e){
            Log.warn("获取当前采集数据的界面的URL失败:" + e);
            return null;
        }
    };

    /**
     * 获取指定图片Dom元素的图片URL
     * AID: 2e
     *
     * @param domSelector 要获取图片URL的图片Dom元素的css选择器
     * @returns String 图片URL字符串
     */
    this.getImgDomURL = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector , "src");
            Log.success("根据脚本命令获取指定图片Dom元素的图片URL成功");
            return result;
        }catch (e){
            Log.warn("获取指定图片Dom元素的图片URL失败:" + e);
            return null;
        }
    };

    /**
     * 获取指定A标记的的链接URL
     * AID: 2f
     *
     * @param domSelector 要获取超链接URL的A标记的css选择器字符串
     * @returns String A标记的链接的URL字符串
     */
    this.getADomURL = function (domSelector) {
        try {
            var result = this.getDomProperty(domSelector, "href");
            Log.success("根据脚本命令获取指定A标记的的链接URL成功");
            return result;
        }catch (e){
            Log.warn("获取指定A标记的的链接URL失败:" + e);
            return null;
        }
    };

}
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
function Operate(identity) {

    /**
     * 作用于的采集器
     */
    var grabberIdentity = identity;

    /**
     * 在数据采集容器中执行指定的JS代码,并且返回代码执行的返回值
     * @param jsCode 要在采集容器中执行的JS代码
     */
    this.executeJS = function (jsCode) {
        return window.grabber.executeJS(grabberIdentity, jsCode);
    };

    /**
     * 加载指定的采集地址URL
     * AID: 1a
     *
     * @param grabURL 要加载的URL地址
     * @param timeoutInterval 加载URL的最长允许超时时间
     * @param successFunction URL加载成功且完毕时候的回调函数
     * @param failedFunction URL加载失败(包括超时)时候的回调函数
     */
    this.loadURL = function (grabURL, timeoutInterval, successFunction, failedFunction) {
        Log.info("开始加载URL:" + grabURL);
        console.log('START LOAD URL' + grabURL  + " - " + grabberIdentity);
        // window.grabber.loadURL(grabberIdentity, grabURL, timeoutInterval, successFunction, failedFunction);
        this.executeJS("location.href=" + grabURL);
        console.log("LOAD URL END!!!!" + grabURL);
    };

    /**
     * 设置指定Dom元素的Attribute参数
     * @param domSelector 要设置Attribute参数的Dom元素的css选择器
     * @param attributeName 要设置的Attribute参数名
     * @param attributeValue 要设置的Attribute参数的值
     */
    this.setDomAttribute = function (domSelector , attributeName , attributeValue) {
        this.executeJS("document.querySelector('" + domSelector + "').setAttribute('" + attributeName + "' , '" + attributeValue +"')");
    };

    /**
     * 设置指定Dom元素的属性
     * @param domSelector 要设置的属性的Dom元素的css选择器
     * @param propertyName 要设置的属性名
     * @param propertyValue 要设置的属性值
     */
    this.setDomProperty = function (domSelector , propertyName , propertyValue) {
        this.executeJS("document.querySelector('" + domSelector + "')." + propertyName + " = " + "'" + propertyValue + "'");
    };

    /**
     * 触发指定Dom元素的指定函数
     * @param domSelector 要触发对应函数的Dom元素
     * @param functionName 要触发指定Dom元素的函数名称
     * @param attributes 要传入的参数列表,数组类型
     */
    this.invokeDomFunction = function (domSelector, functionName , attributes) {
        this.executeJS("document.querySelector('" + domSelector + "')." + functionName + "(" + attributes.join(",") + ")");
    };

    /**
     * 触发指定Dom元素的鼠标事件,如点击/鼠标悬浮等操作
     * @param domSelector 要触发鼠标事件的Dom元素
     * @param actionName 要触发的鼠标事件名称  ‘click’, ‘mousedown’, ‘mousemove’, ‘mouseout’, ‘mouseover’, ‘mouseup’.
     */
    this.invokeDomMouseAction = function (domSelector , actionName) {
        this.executeJS(String.format("window.eval(\"var event = document.createEvent('MouseEvents');event.initMouseEvent('%s',true,true,window,0,0,0,0,0,false,false,false,false,0,null);document.querySelector('%s').dispatchEvent(event);\")" , actionName , domSelector));
    };

    /**
     * 在指定的Input文本控件中填写值
     * AID: 1b
     *
     * @param domSelector 要填写值的文本输入控件的css选择器字符串
     * @param fillText 要填写的文本值
     */
    this.fillInputText = function(domSelector , fillText) {
        this.setDomAttribute(domSelector , "value" , fillText);
        Log.success("在指定的Input文本控件中填写值成功");
    };

    /**
     * 选择指定的下拉菜单的下拉项目
     * AID: 1c
     *
     * @param domSelector 要选择项目的css选择器字符串
     * @param selectedIndex 要让其选择的项目的索引
     */
    this.selectItem = function(domSelector , selectedIndex) {
        this.setDomProperty("selectedIndex" , selectedIndex);
        Log.success("选择指定的下拉菜单的下拉项目成功");
    };

    /**
     * 选中指定radio或checkbox
     * AID: 1d
     *
     * @param domSelector 要选择项目的css选择器字符串
     * @param checked 设置是选中还是取消选中,boolean值
     */
    this.checkedItem = function(domSelector , checked) {
        this.setDomProperty("checked" , checked ? "checked" : "");
        Log.success("选中指定radio或checkbox成功");
    };

    /**
     * 触发提交指定的表单
     * AID: 1e
     *
     * @param domSelector 要提交的表单的css选择器字符串
     */
    this.submitForm = function (domSelector) {
        this.invokeDomFunction(domSelector , "submit" , []);
        Log.success("触发提交指定的表单成功");
    };

    /**
     * 休眠指定的时间之后再继续执行
     * AID: 1f
     *
     * @param sleepInterval 休眠的时间,单位ms
     */
    this.sleep = function (sleepInterval) {
        Log.info("休眠" + sleepInterval + " 开始");
        Util.sleep(sleepInterval);
        Log.info("休眠" + sleepInterval + " 结束");
    };

    /**
     * 让当前页面滚动到指定的位置
     * AID: 1g
     *
     * @param x 让页面要滚动到的位置的x坐标
     * @param y 让页面要滚动到的位置的y坐标
     */
    this.scrollToPosition = function (x , y) {
        this.executeJS("window.scrollTo(" + x + " , " + y + ")");
        Log.success("让当前页面滚动到指定的位置成功");
    };

    /**
     * 点击指定的Dom元素
     * AID: 1h
     *
     * @param domSelector 要点击的Dom元素的css选择器字符串
     */
    this.clickDom = function (domSelector) {
        this.invokeDomMouseAction(domSelector , "click");
        Log.success("点击指定的Dom元素成功");
    };

    /**
     * 触发指定Dom元素的鼠标悬浮事件
     * AID: 1i
     *
     * @param domSelector 要触发hover事件的dom元素的css选择器字符串
     */
    this.mouseoverDom = function (domSelector) {
        this.invokeDomMouseAction(domSelector , "mouseover");
        Log.success("触发指定Dom元素的鼠标悬浮事件成功");
    }

}
function Log() {
}
Log.logs = [];

/**
 * 获取当前的所有Log
 * @returns {Array} 存放LogModel的数组
 */
Log.getAllLogs = function () {
    return this.logs;
};

/**
 * 清除当前的所有日志
 */
Log.removeAllLogs = function () {
    this.logs = [];
};

/**
 * 日志模型
 */
function LogModel(time, type, message) {
    this.time = time;
    this.type = type;
    this.message = message;
}

const LOG_TYPE_SUCCESS = 0;
const LOG_TYPE_ERROR = 1;
const LOG_TYPE_WARN = 2;
const LOG_TYPE_INFO = 3;
const LOG_TYPE_MSG = 4;

/**
 * 记录日志,成功的消息
 * @param message 要记录的文本
 */
Log.success = function (message) {
    this.message(LOG_TYPE_SUCCESS, message);
};

/**
 * 记录日志,错误的消息
 * @param message 要记录的文本
 */
Log.error = function (message) {
    this.message(LOG_TYPE_ERROR, message);
};

/**
 * 记录日志,警告的消息
 * @param message 要记录的文本
 */
Log.warn = function (message) {
    this.message(LOG_TYPE_WARN, message);
};

/**
 * 记录日志,要提示信息的消息
 * @param message 要记录的文本
 */
Log.info = function (message) {
    this.message(LOG_TYPE_INFO, message);
};

/**
 * 记录日志,只是普通的提示
 * @param message 要记录的文本
 */
Log.msg = function (message) {
    this.message(LOG_TYPE_MSG, message);
};

/**
 * 通用日志记录,所有的日志记录都要直接或间接的调用此函数来记录日志
 * @param messageType 日志的类型
 * @param message 日志的信息
 */
Log.message = function (messageType, message) {
    switch (messageType) {
        case LOG_TYPE_SUCCESS:
            Terminal.success(message);
            break;
        case LOG_TYPE_ERROR:
            Terminal.error(message);
            break;
        case LOG_TYPE_WARN:
            Terminal.warn(message);
            break;
        case LOG_TYPE_INFO:
            Terminal.info(message);
            break;
        case LOG_TYPE_MSG:
            Terminal.msg(message);
            break;
        default:
    }
    var logModel = new LogModel(Util.getUnixTimeStamp(), messageType, message);
    this.logs.push(logModel);
};
/**
 * 错误反馈模型
 */
function ErrorModel(logs) {
    this.log = logs;
}
/**
 * 任务结果模型
 */
function ResultModel() {
    this.data = {};
    this.log = [];
}
function TaskModel() {

    this.tid = "";
    this.name = "";
    this.fingerprint = "";
    this.description = "";
    this.createTime = "";
    this.publishTime = "";
    this.distributeRepeatInterval = "";
    this.distributeRepeatCount = "";
    this.distributedNumber = "";
    this.executeScript = "";
    this.state = "";
    this.theTop = "";
    this.expired = "";

}
function Task(taskModel) {

    var taskModel = taskModel;

    /**
     * 发送错误消息到服务器
     * @param errorModel 错误模型
     */
    this.sendError = function (errorModel) {
        $.ajax({
            url: "",
            method: "post",
            type: "json",
            data: {
                taskFingerprint: taskModel.fingerprint,
                log: errorModel.log
            }
        })
    };

    /**
     * 向服务器发送数据采集结果
     * @param resultModel 采集结果模型
     */
    this.sendResult = function (resultModel) {
        $.ajax({
            url: "",
            method: "post",
            type: "json",
            data: {
                fingerprint: taskModel.fingerprint,
                data: resultModel.data,
                log: resultModel.log
            }
        })
    }

}
function TaskList() {
}
TaskList.list = new Array();// 任务列表存储数组

TaskList.addTask = function (taskModel) {
    TaskList.list.push(taskModel);
};

TaskList.removeTaskByIndex = function (index) {
    TaskList.list.remove(index);
};

TaskList.updateTaskByIndex = function (index, newTaskModel) {
    TaskList.list[index] = newTaskModel;
};

TaskList.setSuccessByIndex = function (index) {
    TaskList.list[index].state = TASK_STATE_SUCCESS;
};

TaskList.setErrorByIndex = function (index) {
    TaskList.list[index].state = TASK_STATE_ERROR;
};

TaskList.setWaitingByIndex = function (index) {
    TaskList.list[index].state = TASK_STATE_WAITING;
};

TaskList.setRunningByIndex = function (index) {
    TaskList.list[index].state = TASK_STATE_RUNNING;
};

TaskList.taskCount = function () {
    return TaskList.list.length;
};

TaskList.getByIndex = function (index) {
    return TaskList.list[index];
};

TaskList.refreshDisplay = function () {
    var rowStateClassArr = ["active", "info", "success", "danger", "warning"];
    var rowStateNameArr = ["待运行", "执行中", "成功", "失败", "有错误"];
    var listInnerHTML = "";
    for (var index = 0; index < TaskList.taskCount(); index++) {
        var task = TaskList.getByIndex(index);
        console.log("TASK JSON:" + task);
        listInnerHTML += "<tr class='" + rowStateClassArr[task.state] + "'><td>" + (index + 1) + "</td><td>" + task.name + "</td><td>" + rowStateNameArr[task.state] + "</td></tr>";
    }
    document.getElementById("taskListContentBody").innerHTML = listInnerHTML;
};
function Terminal() {
}

/**
 * 终端消息序号
 * @type {number} 终端消息序号
 */
Terminal.id = 0;

/**
 * 输出普通的消息
 * @param message 要打印的文本
 */
Terminal.info = function (message) {
    Terminal.message("info", message, "提示");
};

/**
 * 输出警告消息
 * @param message 要打印的警告消息
 */
Terminal.warn = function (message) {
    Terminal.message("warning", message, "警告");
};

/**
 * 输出错误消息
 * @param message 要打印的错误消息
 */
Terminal.error = function (message) {
    Terminal.message("danger", message, "错误");
};

/**
 * 输出成功消息
 * @param message 要打印的成功消息
 */
Terminal.success = function (message) {
    Terminal.message("success", message, "成功");
};

/**
 * 控制台输出一个普通的消息
 * @param message 要输出的消息
 */
Terminal.msg = function (message) {
    Terminal.message("active", message, "消息");
};

/**
 * 输出自定义样式的消息
 * @param className 种类名称
 * @param message 消息内容
 * @param messageType 消息类型
 */
Terminal.message = function (className, message, messageType) {
    var terminalContentBody = document.getElementById("terminalPanelContentBody");
    var newNode = document.createElement("tr");
    newNode.setAttribute("class", className);
    newNode.innerHTML = "<td>" + ++Terminal.id + "</td><td>" + message + "</td><td>" + messageType + "</td>";
    terminalContentBody.appendChild(newNode);
    document.getElementsByClassName("terminalPanel")[0].scrollTop = terminalContentBody.children.length * 40;
};
function Util() {
}

/**
 * 创建一个随机的UUID
 * @returns {string} 生成的UUID字符串
 */
Util.createUUID = function () {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
};

/**
 * 获取当前的系统时间戳
 * @returns {number} 当前的UNIX时间戳
 */
Util.getUnixTimeStamp = function () {
    return Math.round(new Date().getTime() / 1000);
};

/**
 * 休眠(阻塞)指定的时间
 * @param timeInterval 要休眠(阻塞)的时间,单位ms
 */
Util.sleep = function (timeInterval) {
    var now = new Date();
    var exitTime = now.getTime() + timeInterval;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)
            return;
    }
};