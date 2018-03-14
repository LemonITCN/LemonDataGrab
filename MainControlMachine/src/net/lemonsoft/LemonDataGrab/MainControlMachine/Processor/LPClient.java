package net.lemonsoft.LemonDataGrab.MainControlMachine.Processor;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;

/**
 * Created by LiuRi on 16/5/11.
 */
public class LPClient {

    public static void clearSensitiveData(LEClient leClient){
        leClient.setRegistrationTime(0L);
        leClient.setCid(0L);
    }

}
