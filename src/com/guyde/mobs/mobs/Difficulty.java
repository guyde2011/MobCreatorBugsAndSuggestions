package com.guyde.mobs.mobs;

/**
 * Created by CH on 05/07/2015.
 */
public enum Difficulty {
    WEAK(1,0.5,0.3),
    NORMAL(1.4,1,0.7),
    HARD(4.2,3,1),
    BOSS(13.33,10,1.3);

    public final double healthMulti;
    public final double damageMulti;
    public final double expMulti;

    Difficulty(double health, double exp, double damage){
        healthMulti = health;
        expMulti = exp;
        damageMulti = damage;
    }

    private static final int[] damageValues = new int[]{3, 5, 8, 11, 14, 18, 24, 30, 36, 44, 52, 62, 74, 86, 98, 110, 124, 138, 154, 170, 186, 194, 212, 230, 250, 270, 292, 314, 336};
    private static final int[] damageBonus = new int[]{0, 10, 20, 30, 40, 50, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 320, 340, 360, 380, 400, 420, 440, 460, 480};
    private static final int[] healthExtra = new int[]{1, 3, 7, 12, 18, 26, 37, 50, 67, 88, 112, 144, 182, 210, 240, 272, 306, 342, 380, 420, 462, 506, 552, 600, 650, 702, 756, 812, 870};

    public int getHealth(int level){
        double basic = Math.pow(0.993,100-level) * 7 * healthExtra[level/5];
        double base1 = healthMulti * (0.25 + 0.0025 * level);
        return (int) (( (basic * 4) + 10 ) * base1);
    }

    private double getDamage(int level){
        double assDamage = getAssassinDMG(level);
        double percentage = getDMGBonus(level);
        return assDamage * damageMulti * (1d + ((double)percentage)/100);
    }

    private double getAssassinDMG(int level){
        int index = level / 5;
        int cur = damageValues[index];
        int next = damageValues[index + 1];
        int dif = next - cur;
        double add = ((double) (level % 5) / 5) * dif;
        return add + cur;
    }

    private double getDMGBonus(int level){
        int index = level / 5;
        int cur = damageBonus[index];
        int next = damageBonus[index + 1];
        int dif = next - cur;
        double add = ((double) (level % 5) / 5) * dif;
        return add + cur;
    }

    private double getEXP(int level){
        int div = level / 5;
        int ext = level % 5;
        double exp = 13;
        for (int i = 0; i<div; i++){
            exp = 1.1 * exp + 5;
        }
        for (int i = 0; i<ext; i++){
            exp = 1.02 * exp + 0.82;
        }
        return exp * expMulti;
    }

    public int getMinDMG(int level){
        return (int) (getDamage(level) * 0.8);
    }

    public int getMaxDMG(int level){
        return (int) (getDamage(level) * 1.2);
    }

    public int getMinEXP(int level){
        return (int) (getEXP(level) * 0.8);
    }

    public int getMaxEXP(int level){
        return (int) (getEXP(level) * 1.2);
    }
}
