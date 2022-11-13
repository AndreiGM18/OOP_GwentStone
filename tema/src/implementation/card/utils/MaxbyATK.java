package implementation.card.utils;

import implementation.card.minion.Minion;

import java.util.Comparator;

public class MaxbyATK implements Comparator<Minion> {
    public int compare(Minion m1, Minion m2) {
        return m1.getAttackDamage() - m2.getAttackDamage();
    }
}
