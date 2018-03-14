$(function () {
    const CONFIG_NUMBER = "CONFIG_NUMBER";
    const CONFIG_PASSWORD = "CONFIG_PASSWORD";
    $("#numberField").val(window.config.getOrDefault(CONFIG_NUMBER , ""));
    $("#passwordField").val(window.config.getOrDefault(CONFIG_PASSWORD , ""));
    $(".bootstrap-switch input").bootstrapSwitch();
    $("#rememberMeSwitch").bootstrapSwitch("state" , $("#numberField").val() != "");
    $("#loginButton").click(function () {
        // 登录按钮点击
        getClientFingerprint(function (clientFingerprint) {
            window.alert.showWaiting("正在登录中");
            $.ajax({
                url: URL_USER_SIGN_IN,
                method: "post",
                data: {
                    number: $("#numberField").val(),
                    password: $("#passwordField").val(),
                    clientFingerprint: clientFingerprint,
                    isAdministrator: 1
                },
                success: function (data) {// 登录成功
                    if (data.success) {
                        window.config.set(CONFIG_NUMBER , $("#rememberMeSwitch").bootstrapSwitch("state") ? $("#numberField").val() : "");
                        window.config.set(CONFIG_PASSWORD , $("#rememberMeSwitch").bootstrapSwitch("state") ? $("#passwordField").val() : "");
                        window.system.setSessionFingerprint(data.result.info);
                        window.mina.connectToServer(data.result.info);
                        window.login.success();// 调用java的登录成功回调函数
                    }
                    else {
                        window.alert.hideWaiting();
                        window.console.log("登录失败,err:" + data.info);
                        window.login.failInfo(data.info);
                    }
                },
                error: function (error) {// 登录失败
                    window.alert.hideWaiting();
                    window.console.log("登录失败 , " + error);
                    window.login.fail();// 调用java的登录失败回调函数
                }
            })
        });
    });
});