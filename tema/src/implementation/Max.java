package implementation;

import java.util.Comparator;

class MaxbyATK implements  Comparator<Minion> {
    public int compare(Minion m1, Minion m2) {
        return m1.getAttackDamage() - m2.getAttackDamage();
    }
}

class MaxbyHP implements Comparator<Minion> {
    public int compare(Minion m1, Minion m2) {
        return m1.getHealth() - m2.getHealth();
    }
}
