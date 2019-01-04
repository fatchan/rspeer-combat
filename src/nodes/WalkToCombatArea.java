package nodes;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.ui.Log;
import res.Settings;

public class WalkToCombatArea extends Node  {

    @Override
    public boolean isValid() {
        return !Settings.area.contains(Players.getLocal().getPosition()) && !Inventory.isFull();
    }

    @Override
    public void execute() {
        Settings.state = "walking to combat area";
        Movement.walkToRandomized(Settings.area.getCenter());
        Time.sleepUntil(()-> Settings.area.contains(Players.getLocal().getPosition()), 5000);
    }

}
