$(function () {
    $(".form_datetime").datetimepicker({
        format: "yyyy/mm/dd hh:ii:00",
        autoclose: true,
        todayBtn: true
    });
    $(".spinnerDay").TouchSpin({
        min: 0,
        max: 1000,
        step: 1,
        boostat: 5,
        maxboostedstep: 10,
        postfix: "天"
    });
    $(".spinnerHour").TouchSpin({
        min: 0,
        max: 60,
        step: 1,
        boostat: 5,
        maxboostedstep: 10,
        postfix: "小时"
    });
    $(".spinnerMinute").TouchSpin({
        min: 0,
        max: 60,
        step: 1,
        boostat: 5,
        maxboostedstep: 10,
        postfix: "分钟"
    });
    $(".spinnerSecond").TouchSpin({
        min: 0,
        max: 60,
        step: 1,
        boostat: 5,
        maxboostedstep: 10,
        postfix: "秒"
    });
    $(".spinnerTime").TouchSpin({
        min: 0,
        max: 500,
        step: 1,
        boostat: 5,
        maxboostedstep: 10,
        postfix: "次"
    });
    $(".bootstrap-switch").bootstrapSwitch();

    $("#addTaskButton").click(function () {
        // 立即添加任务按钮被点击
        var task_name = $("#taskNameField").val();
        var task_fingerprint = window.system.createUUID();
        var task_description = $("#taskDescriptionTextArea").val();
        var task_publishTime = Date.parse(new Date($("#taskPublishTimeField").val())) / 1000;
        var task_distributeRepeatInterval =
            Number($("#distributeRepeatIntervalDayField").val()) * 24 * 60 * 60 +
            Number($("#distributeRepeatIntervalHourField").val()) * 60 * 60 +
            Number($("#distributeRepeatIntervalMinuteField").val()) * 60 +
            Number($("#distributeRepeatIntervalSecondField").val());
        var task_distributeRepeatCount = $("#distributeRepeatCountField").val();
        var task_distributedNumber = 0;
        var task_executeScript = $("#executeScriptTextArea").val().replace(/[\r\n]/g, "");
        var task_state = 0;
        var task_expiredInterval =
            Number($("#expiredDayField").val()) * 24 * 60 * 60 +
            Number($("#expiredHourField").val()) * 60 * 60 +
            Number($("#expiredMinuteField").val()) * 60 +
            Number($("#expiredSecondField").val());
        var task_theTop = $("#theTopSwitch").bootstrapSwitch("state");

        // 数据整理完毕,准备ajax提交
        $.ajax({
            url: URL_TASK_ADD,
            method: "post",
            type: "json",
            data: {
                sessionFingerprint: window.system.getSessionFingerprint(),
                name: task_name,
                fingerprint: task_fingerprint,
                description: task_description,
                publishTime: task_publishTime,
                distributeRepeatInterval: task_distributeRepeatInterval,
                distributeRepeatCount: task_distributeRepeatCount,
                distributedNumber: task_distributedNumber,
                executeScript: task_executeScript,
                state: task_state,
                expired: task_expiredInterval,
                theTop: task_theTop
            },
            success: function (data) {
                if (data.success) {
                    // 添加成功
                    window.addTask.success();
                }
                else {
                    // 添加失败
                    window.addTask.error(data.info);
                }
            },
            error: function (error) {
                window.addTask.error("无法连接到服务器,")
            }
        })
    });
});