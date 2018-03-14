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