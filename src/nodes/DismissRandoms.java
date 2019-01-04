package nodes;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import res.Settings;

import java.util.function.Predicate;

public class DismissRandoms extends Node  {

    private static final Predicate<Npc> RANDOM_EVENT = npc -> npc.containsAction("Dismiss") && npc.getTarget() != null && npc.getTarget().equals(Players.getLocal());

    @Override
    public boolean isValid() {
        return Npcs.getNearest(RANDOM_EVENT) != null;
    }

    @Override
    public void execute() {
        Settings.state = "dismissing random";
        Npcs.getNearest(RANDOM_EVENT).interact("Dismiss");
        Time.sleepUntil(()-> !isValid(), Random.low(2000,3000));
    }

}
