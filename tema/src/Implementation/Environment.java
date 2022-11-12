package Implementation;

import java.util.ArrayList;

class Environment extends Card {
    public Environment(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }
}

class Firestorm extends Environment {
    public Firestorm(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions) {
        for (Minion minion : minions)
            minion.setHealth(minion.getHealth() - 1);
    }
}

class Winterfell extends Environment {
    public Winterfell(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions) {
        for (Minion minion : minions)
            minion.setFrozen(true);
    }
}

class HeartHound extends Environment {
    public HeartHound(int mana, String description, ArrayList<String> colors) {
        super(mana, description, colors);
    }

    protected void action(ArrayList<Minion> minions, ArrayList<Minion> advMinions) {
        Minion maxMinion = minions.stream().max(new MaxbyHP()).get();

        minions.remove(maxMinion);

        advMinions.add(maxMinion);
    }
}