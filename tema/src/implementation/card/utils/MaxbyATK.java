package implementation.card.utils;

import implementation.card.minion.Minion;

import java.util.Comparator;

public class MaxbyATK implements Comparator<Minion> {
    public int compare(Minion m1, Minion m2) {
        Integer atk1 = m1.getAttackDamage();
        Integer atk2 = m2.getAttackDamage();

        return atk1.compareTo(atk2);
    }
}
