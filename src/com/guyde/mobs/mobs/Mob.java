package com.guyde.mobs.mobs;

import com.guyde.mobs.components.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by CH on 05/07/2015.
 */
public class Mob {

    private String type;

    private static final String[] skills = new String[]{
        "null", "Arrow Storm", "Heavy Arrow Storm", "Heal", "Heavy Heal", "Charge", "Heavy Charge", "Multihit", "Heavy Multihit", "Spiderweb",
        "Self Destruct", "Meteor", "Teleport", "Heavy Teleport", "Slowness", "Heavy Slowness", "Weakness", "Heavy Weakness", "Vanish",
        "Heavy Vanish", "Flame Thrower", "Heavy Flame Thrower"
    };

    private static final String[] arts = new String[]{
            "Alban",
            "Aztec",
            "Aztec2",
            "Bomb",
            "Burningskull",
            "Bust",
            "Courbet",
            "Creebet",
            "Donkeykong",
            "Fighters",
            "Graham",
            "Kebab",
            "Match",
            "Pigscene",
            "Plant",
            "Pointer",
            "Pool",
            "Sea",
            "Skeleton",
            "Skull And Roses",
            "Stage",
            "Sunset",
            "Void",
            "Wanderer",
            "Wasteland",
            "Wither"
    };




    public Mob(String mobType){
        type = mobType;
    }

    public void createMob(OrderedPanel panel, int level, Difficulty difficulty){
        int health = difficulty.getHealth(level);
        panel.addField("Health","health", "" + health, Converters.INT);
        panel.addField("Minimum Damage","minDam", "" + difficulty.getMinDMG(level), Converters.INT);
        panel.addField("Maximum Damage","maxDam", "" + difficulty.getMaxDMG(level), Converters.INT);
        panel.addField("Minimum EXP","minXP", "" + difficulty.getMinEXP(level), Converters.INT);
        panel.addField("Maximum EXP","maxXP", "" + difficulty.getMaxEXP(level), Converters.INT);
        panel.addField("Regeneration", "regen", "" + (health/10), Converters.INT );
        panel.addField("Attack Rate", "attackRate", "1.0", Converters.NUMBER );
        panel.addField("Projectile YML File", "projectile", "", Converters.RAW );
        panel.addField("Projectile Speed", "projectileSpeed", "1.0", Converters.NUMBER );
        panel.addField("Drops Multiplier", "lootMultiplier", "1.0", Converters.NUMBER );
        panel.addField("Mob Speed", "speed", "1.0", Converters.NUMBER );
        panel.addField("Mob Vision", "vision", "16", Converters.INT );
        panel.addSelectionField("Invisible(Vanished)", "vanished", new String[]{"false","true"}, Converters.RAW );
        panel.addSelectionField("Dungeon/Raid Boss", "boss", new String[]{"false","true"}, Converters.RAW );
        panel.addSelectionField("Burning", "burning", new String[]{"false","true"}, Converters.RAW );
        panel.addSelectionField("Baby", "baby", new String[]{"false","true"}, Converters.RAW );
        panel.addSelectionField("Projectile To Mob", "projectileToMob", new String[]{"false","true"}, Converters.RAW );
        panel.addField("Spawned ID", "spawnedID", "null", Converters.STRING );
        panel.addField("Max Spawned", "maxSpawned", "0", Converters.INT );
        panel.addSelectionField("Has Spells", "spells", new String[]{"false","true"}, Converters.RAW );
        panel.addSelectionField("Spell", "spell", skills,  Converters.SKILLS );
        panel.addField("Spell Cooldown", "spellCooldown", "20", Converters.INT );
        panel.addSoundField("Hurt Sound", "soundHurt");
        panel.addSoundField("Death Sound", "soundDeath");
        panel.addSoundField("Idle Sound", "soundRandom");
        createElementalFields(panel);
        createExtraFields(panel, level);
    }

    public void createElementalFields(final OrderedPanel panel){
        int xOff = 20;
        int xOff1 = 80;
        int xOff2 = 300;
        int xOff3 = 480;
        int a = 60;
        int xOff4 = xOff3 + a;
        int xOff5 = xOff4 + 85;
        int xOff6 = xOff5 + a + 30;
        int i = 1;
        TexturedToggleButton button1 = new TexturedToggleButton("autofill.png") {
            @Override
            public void onClick() {
                panel.frame.repaint();
            }
        };
        final String[] strings = new String[]{"Thunder","Fire","Water","Air","Earth"};
        String[] weak = new String[]{"Earth","Water","Thunder","Fire","Air"};
        String[] strong = new String[]{"Water","Air","Fire","Earth","Thunder"};
        for (String element : strings){
            String unlocal = element.toLowerCase();
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(TexturedButton.class.getResource(unlocal + ".png")));
            label.setSize(new Dimension(32, 32));
            panel.addLabelAt(label, xOff, i * 40 - 8);
            panel.addFieldAt(element + " Damage", unlocal + "Damage", "0-0", Converters.ELEMENT_DMG, xOff1, i * 40);
            panel.addFieldAt(element + " Defense",unlocal + "Defense", "0", Converters.ELEMENT_DEF(element, button1), xOff2,i * 40);
            Converters.addElement(element,panel);
            JLabel label1 = new JLabel();
            label1.setIcon(new ImageIcon(TexturedButton.class.getResource(weak[i-1].toLowerCase() + ".png")));
            label1.setSize(new Dimension(32,32));
            JLabel label2 = new JLabel("Weak To:");
            int width1 = (int) label2.getPreferredSize().getWidth();
            label2.setSize(new Dimension(width1,18));
            JLabel label3 = new JLabel();
            label3.setIcon(new ImageIcon(TexturedButton.class.getResource(strong[i-1].toLowerCase() + ".png")));
            label3.setSize(new Dimension(32,32));
            JLabel label4 = new JLabel("Strong Against:");
            int width2 = (int) label4.getPreferredSize().getWidth();
            label4.setSize(new Dimension(width2,18));
            panel.addLabelAt(label2, xOff3,i * 40);
            panel.addLabelAt(label1, xOff4,i * 40 - 8);
            panel.addLabelAt(label4, xOff5,i * 40);
            panel.addLabelAt(label3, xOff6,i * 40 - 8);
            i++;
        }

        TexturedButton button = new TexturedButton("elements.png") {
            @Override
            public void onClick() {
                JFrame frame = new JFrame();
                frame.setSize(new Dimension(700,1300));
                JLabel background = new JLabel(new ImageIcon(OrderedPanel.class.getResource("TempElement.png")));
                frame.setContentPane(background);
                frame.pack();
                frame.setIconImage(new ImageIcon(OrderedPanel.class.getResource("icon.png")).getImage());
                frame.setVisible(true);
            }
        };

        panel.addComponentAt(button1,180,240);
        panel.addComponentAt(button,30,240);

    }

    public void createExtraFields(OrderedPanel panel, int level){
        if (type.equalsIgnoreCase("item")){
            panel.addExtraField("Item Unlocalized Name:", "item", "Stone", Converters.RAW);
            panel.addExtraField("Item Damage Value", "itemdamage", "0", Converters.INT);
        }

        if (type.equalsIgnoreCase("zombie")){
            panel.addExtraSelectionField("Zombie Villager", "villager", new String[]{"false", "true"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("fallingblock")){
            panel.addExtraField("Block Unlocalized Name", "block", "Stone", Converters.RAW);
            panel.addExtraField("Block Damage Value", "blockdamage", "0", Converters.INT);
        }

        if (type.equalsIgnoreCase("villager")){
            panel.addExtraSelectionField("Profession", "occupation", new String[]{"Farmer", "Butcher", "Blacksmith", "Priest", "Librarian"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("bat")){
            panel.addExtraSelectionField("Is Able To Fly", "flying", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("blaze")){
            panel.addExtraSelectionField("Is Blazing", "blazing", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("painting")){
            panel.addExtraSelectionField("Painting Name", "art", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("creeper")){
            panel.addExtraSelectionField("Is Powered", "powered", new String[]{"true","false"}, Converters.RAW);
            panel.addExtraSelectionField("Ignited", "ignited", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("enderman") || type.equalsIgnoreCase("ghast")){
            panel.addExtraSelectionField("Is Aggressive", "aggressive", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("pig")){
            panel.addExtraSelectionField("Has Saddle", "saddled", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("witherskull")){
            panel.addExtraSelectionField("Is Blue", "blue", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("potion")){
            panel.addExtraField("Potion ID", "potionid", "1", Converters.INT );
        }

        if (type.equalsIgnoreCase("slime")){
            panel.addExtraField("Slime Size", "size", "1", Converters.INT );
        }

        if (type.equalsIgnoreCase("wolf")){
            panel.addExtraSelectionField("Is Sitting", "sitting", new String[]{"true","false"}, Converters.RAW);
            panel.addExtraSelectionField("Is Tamed", "tamed", new String[]{"true","false"}, Converters.RAW);
            String[] colors = new String[]{"Black","Blue","Brown","Cyan","Gray","Green","Light Blue","Lime","Magenta","Orange","Pink","Purple","Red","Silver","White","Yellow"};
            panel.addExtraSelectionField("Collar Color", "colour",colors,Converters.SKILLS);
            panel.addExtraSelectionField("Is Angry","angry",new String[]{"false","true"},Converters.RAW);
        }

        if (type.equalsIgnoreCase("sheep")){
            String[] colors = new String[]{"Black","Blue","Brown","Cyan","Gray","Green","Light Blue","Lime","Magenta","Orange","Pink","Purple","Red","Silver","White","Yellow"};
            panel.addExtraSelectionField("Sheep Color", "colour",colors,Converters.SKILLS);
            panel.addExtraSelectionField("Is Sheared", "sheared",new String[]{"false","true"},Converters.RAW);
        }

        if (type.equalsIgnoreCase("horse")){
            panel.addExtraSelectionField("Colour", "colour", new String[]{"Black","White","Chestnut","Creamy","Gray","Brown","Dark Brown"}, Converters.SKILLS);
            panel.addExtraSelectionField("Horse Style", "style", new String[]{"None","Black Dots","White","White Dots","WhiteField"}, Converters.SKILLS);
            panel.addExtraSelectionField("Has Chest", "carryingChest", new String[]{"true","false"}, Converters.RAW);
            panel.addExtraSelectionField("Is Mount Open", "mountOpen", new String[]{"true","false"}, Converters.RAW);
            panel.addExtraSelectionField("Is Rearing", "rearing", new String[]{"true","false"}, Converters.RAW);
            panel.addExtraSelectionField("Has Saddle", "saddled", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("ocelot")){
            panel.addExtraSelectionField("Ocelot Style", "type", new String[]{"Wild Ocelot","Siamese Cat","Red Cat","Black Cat"}, Converters.SKILLS);
            panel.addExtraSelectionField("Is Sitting", "sitting", new String[]{"true","false"}, Converters.RAW);
            panel.addExtraSelectionField("Is Tamed", "tamed", new String[]{"true","false"}, Converters.RAW);
        }

        if (type.equalsIgnoreCase("arrow")){
            panel.addExtraSelectionField("Is Critical Arrow", "critical", new String[]{"true","false"}, Converters.RAW);
        }



        panel.frame.repaint();
    }


}
