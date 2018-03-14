function getClientFingerprint(callback) {
    if (window.system.getClientFingerprint() == "") {
        // 没有获取过客户端指纹
        $.ajax({
            url: URL_CLIENT_SIGN_UP,
            method: "post",
            data: {
                identity: window.system.getIdentity(),
                system: window.system.getSystemName(),
                terminalType: window.system.getTerminalType(),
                version: window.system.getAppVersion(),
                device: window.system.getDeviceName()
            },
            success: function (data) {
                window.console.log("CLIENT_FINGERPRINT = " + data.result.info);
                window.system.setClientFingerprint(data.result.info);
                callback(data.result.info);
            },
            error: function (error) {
                window.console.log("错误啦 : " + error);
            }
        });
    }
    else {
        // 本地存储了客户端指纹
        callback(window.system.getClientFingerprint());
    }
}