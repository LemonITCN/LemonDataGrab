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