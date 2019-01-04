package nodes;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;

import res.Settings;

public class OpenBank extends Node  {

    @Override
    public boolean isValid() {
        return Inventory.isFull() && Settings.bank.distance() < 3 && !Bank.isOpen();
    }

    @Override
    public void execute() {
        Settings.state = "opening bank";
        Bank.open();
        Time.sleepUntil(() -> Bank.isOpen(), 10000);
    }

}
