package net.lemonsoft.LemonDataGrab.MainControlMachine.Model;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Core.LCTaskManager;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;

import java.util.ArrayList;

/**
 * Created by LiuRi on 6/24/16.
 */
public class LMDataGrabMapper {

    private LMOnlineSession onlineSession;

    private ArrayList<LETask> toDoTasksList;

    public LMDataGrabMapper(LMOnlineSession onlineSession) {
        super();
        this.onlineSession = onlineSession;
        toDoTasksList = new ArrayList<>();
    }

    public LMOnlineSession getOnlineSession() {
        return onlineSession;
    }

    public void addTask(LETask task){
        toDoTasksList.add(task);
    }

    public Integer countToDoTasks(){
        return toDoTasksList.size();
    }

    public void removeAllTask(){
        for(LETask task : toDoTasksList){
            try {
                LCTaskManager.sharedInstance().resetToReadyToDistribute(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
