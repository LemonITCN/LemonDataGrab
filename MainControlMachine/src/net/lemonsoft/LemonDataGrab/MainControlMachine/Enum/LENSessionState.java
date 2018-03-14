package net.lemonsoft.LemonDataGrab.MainControlMachine.Enum;

/**
 * Created by LiuRi on 5/29/16.
 */
public enum LENSessionState {

    SIGN_OUT(0L, "当前未在线"),
    SIGN_IN_DATA_GRAB(1L, "登录到采集终端"),
    SIGN_IN_ADMINISTRATOR(2L, "登录到管理员终端");

    private Long stateCode;

    private String stateInfo;

    LENSessionState(Long stateCode, String stateInfo) {
        this.stateCode = stateCode;
        this.stateInfo = stateInfo;
    }

    public Long getStateCode() {
        return stateCode;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    }
