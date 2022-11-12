package Implementation;

import java.util.ArrayList;

abstract class Hero extends Card{
    protected int health = 30;

    public Hero(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

class Sub_Zero extends Hero {
    public Sub_Zero(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions) {
        minions.stream().max(new MaxbyATK()).get().setFrozen(true);
    }
}

class EmpressThorina extends Hero {
    public EmpressThorina(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions) {
        minions.stream().max(new MaxbyHP()).get().setHealth(0);
    }
}

class KingMudface extends Hero {
    public KingMudface(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.setHealth(minion.getHealth() + 1);
        }
    }
}

class GeneralKocioraw extends Hero {
    public GeneralKocioraw(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.setAttackDamage(minion.getAttackDamage() + 1);
        }
    }
}
