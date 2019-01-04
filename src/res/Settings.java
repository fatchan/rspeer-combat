package res;

import nodes.Node;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

import java.util.ArrayList;

public class Settings {

    public static String[] loot;
    public static String[] targets;
    public static String state = "setup";
    public static boolean bury = false;
    public static boolean start = false;
    public static Position bank;
    public static Area area;
    public static ArrayList<Node> nodes = new ArrayList<>();

}
