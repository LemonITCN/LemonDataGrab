$(function () {
    resetWeights();

    $(window).resize(function () {
        resetWeights();
    });

    $('[type="checkbox"]').bootstrapSwitch();

    $("#userIcon").hover(function () {
        $("#userIcon").animate({
            boxShadow: "10px 10px 5px red"
        });
    }, function () {
        $("#userIcon").animate({
            "box-shadow": "10px 10px 2px red"
        });
    });

    $("#signOutButton").click(function () {
        $.ajax({
            url: URL_USER_SIGN_OUT,
            method: "post",
            data: {
                sessionFingerprint: window.system.getSessionFingerprint()
            },
            success: function (data) {
                window.func.signOutSuccess();
            },
            error: function (error) {
                window.alert.showError("注销失败,无法连接到服务器。");
            }
        });
    });
});

function resetWeights() {
    $("#userIcon").width($("#userIcon").height()).css("border-radius", $("#userIcon").height() / 2.0 + "px");
    $(".operationPanel").css("line-height", $("#userIcon").height() / 4 + "px");
    $("#userName").css("line-height", $("#userIcon").height() / 4 + "px");
    if ($("#userIcon").height() < 70) {
        $(".operationPanel button").removeClass("btn-sm");
        $(".operationPanel button").addClass("btn-xs");
    }
    else if ($("#userIcon").height() < 100) {
        $(".operationPanel button").addClass("btn-sm");
        $(".operationPanel button").removeClass("btn-xs");
    }
    else {
        $(".operationPanel button").removeClass("btn-sm");
        $(".operationPanel button").removeClass("btn-xs");
    }
}

function addTaskToUI(task) {
    TaskList.addTask(task);
    TaskList.refreshDisplay();
}