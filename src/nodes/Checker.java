package nodes;

import org.rspeer.runetek.api.scene.Players;

public final class Checker {

    private Checker() { }

    //TODO: add more of these here
    public static boolean notInCombat() {
        return Players.getLocal().getTargetIndex() == -1 || (Players.getLocal().getTarget() != null && Players.getLocal().getTarget().getHealthPercent() == 0);
    }

}
