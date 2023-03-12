package net.villenium.athena.client;

public class ItemStat {
    private int damage;
    private int magicalDamage;
    private int level;
    public ItemStat(int damage, int magicalDamage) {
        this.damage = damage;
        this.magicalDamage = magicalDamage;
        this.level = 1;
    }
    public void setLevel(int level) {
        this.level = level;
        this.damage = (int) Math.round(this.damage * Math.pow(1.1F, level - 1));
        this.magicalDamage = (int) Math.round(this.magicalDamage * Math.pow(1.1F, level - 1));
        System.out.printf("Lvl %s: %s-%s\n", level, damage, magicalDamage);
    }
    public int getDamage() {
        return this.damage;
    }
    public int getMagicalDamage() {
        return this.magicalDamage;
    }
    public int getLevel() {
        return this.level;
    }
}
