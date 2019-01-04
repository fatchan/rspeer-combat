import nodes.*;
import org.rspeer.runetek.api.ClientSupplier;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import res.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GUI extends JFrame {

    public GUI(){
        super("Fighter");

        //TODO: add these to an enum maybe idk
        String[] bankNames = new String[]{"Falador East"};
        HashMap<String, Position> banks = new HashMap<>();
        banks.put("Falador East", new Position(3012, 3355));

        String[] areaNames = new String[]{"Falador South Cows", "Lumbridge East Chickens"};
        HashMap<String, Area> areas = new HashMap<>();
        areas.put("Falador South Cows", Area.rectangular(3043, 3313, 3021, 3299));
        areas.put("Lumbridge East Chickens", Area.rectangular(3186, 3303, 3169, 3288));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel targetTitle = new JLabel("NPC Names, one per line:");
        JTextArea targetList = new JTextArea("");
        targetList.setColumns(10);
        targetList.setRows(5);

        JLabel lootTitle = new JLabel("Loot names, one per line:");
        JTextArea lootList = new JTextArea("");
        lootList.setColumns(10);
        lootList.setRows(5);

        JCheckBox buryCheck = new JCheckBox("Bury bones?");

        JLabel bankTitle = new JLabel("Bank:");
        JComboBox bankName = new JComboBox(bankNames);
        JLabel areaTitle = new JLabel("Area:");
        JComboBox areaName = new JComboBox(areaNames);
        JButton start = new JButton("Start");

        setLocationRelativeTo(ClientSupplier.get().getCanvas());
        setSize(new Dimension(450, 95));

        panel.add(targetTitle);
        panel.add(targetList);
        panel.add(lootTitle);
        panel.add(lootList);
        panel.add(buryCheck);
        panel.add(bankTitle);
        panel.add(bankName);
        panel.add(areaTitle);
        panel.add(areaName);
        panel.add(start);
        add(panel);

        pack();

        setDefaultCloseOperation(HIDE_ON_CLOSE);

        //Add action listener to start script when button is pressed
        start.addActionListener(event -> {
            if (lootList.getText() != null && lootList.getText().length() > 0) {
                Settings.loot = lootList.getText().toLowerCase().split("\n");
            }
            if (buryCheck.isSelected()) {
                Settings.bury = true;
            }
            Settings.bank = banks.get(bankName.getSelectedItem());
            Settings.area = areas.get(areaName.getSelectedItem());

            Settings.nodes.add(new DismissRandoms());
            Settings.nodes.add(new EnableRun());
            Settings.nodes.add(new CloseLevelDialog());
            Settings.nodes.add(new WalkToCombatArea());
            if (Settings.loot.length > 0) {
                Settings.nodes.add(new PickupLoot());
                if (Settings.bury) {
                    Settings.nodes.add(new BuryBones());
                }
                Settings.nodes.add(new TravelToBank());
                Settings.nodes.add(new OpenBank());
                Settings.nodes.add(new DepositBank());
            }
            if (targetList.getText() != null && targetList.getText().length() > 0) {
                Settings.targets = targetList.getText().toLowerCase().split("\n");
                Settings.nodes.add(new Combat());
            }

            Settings.start = true;
            setVisible(false);
        });
    }

}
