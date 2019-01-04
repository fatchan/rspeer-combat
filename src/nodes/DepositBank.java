package nodes;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;

import res.Settings;

public class DepositBank extends Node  {

    @Override
    public boolean isValid() {
        return Bank.isOpen();
    }

    @Override
    public void execute() {
        Settings.state = "depositing";
        Bank.depositInventory();
        Time.sleepUntil(() -> Inventory.isEmpty(), 2000);
    }

}
