package net.lemonsoft.LemonDataGrab.MainControlMachine.Processor;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEUser;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSUser;

/**
 * Created by LiuRi on 5/29/16.
 */
public class LPUser {

    public static void clearSensitiveData(LEUser leUser){
        leUser.setIdentity("");
        leUser.setUid(0L);
        leUser.setUsergroup(0L);
        leUser.setPassword("");
        leUser.setRegistrationTime(0L);
    }

}
