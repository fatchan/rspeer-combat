package nodes;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;

import res.Settings;

public class TravelToBank extends Node  {

    @Override
    public boolean isValid() {
        return Checker.notInCombat() && Inventory.isFull() && Settings.bank.distance() > 3;
    }

    @Override
    public void execute() {
        Settings.state = "Walking to bank";
        Movement.walkToRandomized(Settings.bank);
        Time.sleepUntil(() -> Settings.bank.distance() < 3, 50,2000);
    }

}
