package Implementation;

import java.util.ArrayList;

abstract class Minion extends Card {
    protected int health;
    protected int attackDamage;
    protected String name;
    protected boolean frozen;

    public Minion(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors);
        this.health = health;
        this.attackDamage = attackDamage;
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    protected void attackMinion(Minion minion) {
        minion.setHealth(minion.getHealth() - this.attackDamage);
    }

    protected void attackHero(Hero hero) {
        hero.setHealth(hero.getHealth() - this.attackDamage);
    }
}

class SBGW extends Minion {
    public SBGW(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    protected void action (Minion minion) {
    }
}

class The_Ripper extends Minion {
    public The_Ripper(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }
    SBGW s;
    protected void action(Minion minion) {
        minion.setAttackDamage(getAttackDamage() - 2);
    }
}

class Miraj extends Minion {
    public Miraj(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    protected void action(Minion minion) {
        int hp = minion.getHealth();

        this.health = this.health ^ hp;
        hp = this.health ^ minion.health;
        this.health = this.health ^ hp;

        minion.setHealth(hp);
    }
}

class The_Cursed_One extends Minion {
    public The_Cursed_One(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    protected void action(Minion minion) {
        int hp = minion.getHealth();

        this.health = this.health ^ hp;
        hp = this.health ^ minion.health;
        this.health = this.health ^ hp;

        minion.setHealth(hp);

        int atk = minion.getAttackDamage();

        this.attackDamage = this.attackDamage ^ atk;
        atk = this.attackDamage ^ atk;
        this.attackDamage = this.attackDamage ^ atk;

        minion.setAttackDamage(atk);
    }
}

class Disciple extends Minion {
    public Disciple(int mana, String description, ArrayList<String> colors, int health, int attackDamage, String name) {
        super(mana, description, colors, health, attackDamage, name);
    }

    protected void action(Minion minion) {
        minion.setHealth(minion.getHealth() + 2);
    }
}
