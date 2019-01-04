import nodes.*;
import org.rspeer.runetek.adapter.scene.PathingEntity;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

import res.Settings;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@ScriptMeta(developer = "fatchan", desc = "fight, loot, bury bones, bank", name = "fighter")
public class Main extends Script implements RenderListener {

    private long startTime;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
        new GUI().setVisible(true);
    }

    public int loop () {
        if (!Settings.start) {
            return 1000;
        }
        for (Node n : Settings.nodes) {
            if (n.isValid()) {
                n.execute();
            }
        }
        return 1000;
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        if (!Settings.start) {
            return;
        }
        Graphics g = renderEvent.getSource();
        long runtime = System.currentTimeMillis() - startTime;
        String dateString = simpleDateFormat.format(new Date(runtime));
        g.setColor(Color.BLACK);
        g.fillRect(7, 459, 506, 15);
        g.setColor(Color.GREEN);
        g.drawString("Runtime: " + dateString, 8, 471);
        g.drawString("Status: " + Settings.state, 150, 471);
        if (Game.isLoggedIn()) {
            PathingEntity target = Players.getLocal().getTarget();
            if(target != null) {
                g.setColor(Color.RED);
                target.getPosition().outline(g);
            }
            Position destination = Movement.getDestination();
            if (destination != null) {
                g.setColor(Color.BLUE);
                destination.outline(g);
            }
        }

    }


}