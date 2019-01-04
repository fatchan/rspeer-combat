package nodes;

import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import res.Settings;

import java.util.Arrays;
import java.util.function.Predicate;

public class PickupLoot extends Node  {

    private static final Predicate<Pickable> IN_RANGE_ITEM = pickable -> Settings.area.contains(pickable.getPosition())
            && Arrays.stream(Settings.loot).anyMatch(pickable.getName().toLowerCase()::equals)
            && Movement.isInteractable(pickable.getPosition());

    @Override
    public boolean isValid() {
        return Settings.area.contains(Players.getLocal().getPosition()) && !Inventory.isFull() && Pickables.getNearest(IN_RANGE_ITEM) != null && Checker.notInCombat();
    }

    @Override
    public void execute() {
        Settings.state = "getting Loot";
        Pickable item = Pickables.getNearest(IN_RANGE_ITEM);
        int inventoryAmount = Inventory.getCount(true);
        while (item != null) {
            if (Inventory.isFull()) {
                break;
            }
            item.interact("Take");
            Time.sleepUntil(() -> Inventory.getCount(true) > inventoryAmount, Random.low(3000, 5000));
            Time.sleep(Random.high(0,350));
            item = Pickables.getNearest(IN_RANGE_ITEM);
        }
    }

}
