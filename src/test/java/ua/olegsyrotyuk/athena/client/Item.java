package ua.olegsyrotyuk.athena.client;

import java.util.HashMap;
import java.util.Map;

public class Item {

    private final int damage;
    private final int level;

    private Map<Integer, Float> modify = new HashMap<>();
    private Map<Integer, ItemStat> test = new HashMap<>();

    public Item(int damage, int level) {
        float modifier = 0.3F;
        for (int i = 0; i < 100; i++) {
            modify.put(i, modifier);
            modifier = modifier+0.7F*1.1F;
            System.out.println(modifier);
        }
        this.damage = damage;
        this.level = level;
    }

    public int getDamage() {
        return damage;
    }

    public int getLevel() {
        return level;
    }

    public int getDamageByLevel(int baseDamage, int level, boolean secondDamage) {
        if (secondDamage) {
            return (int) ((int) (baseDamage*modify.get(level)));
        }
        return (int) (baseDamage*modify.get(level));
    }

    public int getDamage0(int damage, int level) {
        damage = (int) (damage*(1+level*0.05F));
        return damage;
    }
}
