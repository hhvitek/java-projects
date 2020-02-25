package model;

import gui_swing.ScheduledActionsUI;
import model.sql.DbConnectionErrorException;
import model.sql.ISqlDbDao;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TimerTask;

public class ModelImplTask extends TimerTask {

    private final ISqlDbDao db;
    private static final Logger logger = LoggerFactory.getLogger(ModelImplTask.class);

    public ModelImplTask(@NotNull ISqlDbDao db) {
        this.db = db;
    }

    @Override
    public void run() {
        logger.info("Model tick()");

        try {
            List<ScheduledAction> scheduledActions = db.getAllExpiredScheduledActions();
            for (ScheduledAction scheduledAction: scheduledActions) {
                scheduledAction.setStatus("RUNNING");
                db.saveScheduledAction(scheduledAction);
                logger.info("Executing scheduled Action: {}", scheduledAction.getAction().getName());
                scheduledAction.setStatus("FINISHED");
                db.saveScheduledAction(scheduledAction);
            }
        } catch (DbConnectionErrorException e) {
            e.printStackTrace();
        }

    }
}
