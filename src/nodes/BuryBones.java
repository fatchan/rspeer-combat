package nodes;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.ui.Log;

import java.util.function.Predicate;

public class BuryBones extends Node  {

    private static final Predicate<Item> BONES_ITEM = item -> item.getName().toLowerCase().contains("bone") && item.containsAction("Bury");

    @Override
    public boolean isValid() {
        return Inventory.contains(BONES_ITEM) && Checker.notInCombat();
    }

    @Override
    public void execute() {
        Log.info("Burying bones");
        Item bone = Inventory.getFirst(BONES_ITEM);
        int inventoryAmount = Inventory.getCount();
        while (bone != null) {
            bone.interact("Bury");
            Time.sleepUntil(()-> Inventory.getCount() < inventoryAmount, Random.low(3000,5000));
            Time.sleep(Random.low(750, 1500)); //oof
            bone = Inventory.getFirst(BONES_ITEM);
        }
    }

}
