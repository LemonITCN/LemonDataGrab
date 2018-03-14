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