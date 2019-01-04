package nodes;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;

import java.util.Arrays;
import java.util.function.Predicate;

import res.Settings;

public class Combat extends Node  {

    private Predicate<Npc> NOT_IN_COMBAT_TARGET = npc -> Settings.area.contains(npc.getPosition())
            && Arrays.stream(Settings.targets).anyMatch(npc.getName().toLowerCase()::startsWith)
            && npc.getTargetIndex() == -1
            && npc.getHealthBar() == null
            && npc.getHealthPercent() > 0
            && Movement.isInteractable(npc.getPosition());

    private Predicate<Npc> IN_COMBAT_TARGET = npc -> Settings.area.contains(npc.getPosition())
            && Arrays.stream(Settings.targets).anyMatch(npc.getName().toLowerCase()::startsWith)
            && npc.getTargetIndex() != -1
            && npc.getTarget() == Players.getLocal()
            && npc.getHealthPercent() > 0
            && Movement.isInteractable(npc.getPosition());

    @Override
    public boolean isValid() {
        return Settings.area.contains(Players.getLocal().getPosition()) && Checker.notInCombat() && !Inventory.isFull();
    }


    @Override
    public void execute() {
        Npc npc = Npcs.getNearest(IN_COMBAT_TARGET) != null ? Npcs.getNearest(IN_COMBAT_TARGET) : Npcs.getNearest(NOT_IN_COMBAT_TARGET);
        if (npc != null) {
            Settings.state = "found target";
            npc.interact("Attack");
            Settings.state = "attacking";
            Time.sleepUntil(()-> npc.getHealthBar() != null, Random.low(5000,10000));
            if (npc.getTarget() != null && !npc.getTarget().equals(Players.getLocal())) {
                Settings.state =  "target lost/stolen";
                return;
            }
            Settings.state = "in combat";
        }
    }

}
