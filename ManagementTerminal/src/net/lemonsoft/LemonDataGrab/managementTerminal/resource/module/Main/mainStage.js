$(function () {
    $("#addTaskButton").click(function () {
        window.topControlAction.addTask();
    });

    $("#signOutButton").click(function () {
        $.ajax({
            url: URL_USER_SIGN_OUT,
            method: "post",
            data: {
                sessionFingerprint: window.system.getSessionFingerprint()
            },
            success: function (data) {
                
            },
            error: function (error) {
                window.alert.showError("注销失败,无法连接到服务器。");
            }
        });
    });
});