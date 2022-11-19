package implementation.card.utils;

import implementation.card.minion.Minion;

import java.util.Comparator;

public class MaxByATK implements Comparator<Minion> {
    /**
     * Compares the minions by their attackDamage
     * @param m1 the first object to be compared.
     * @param m2 the second object to be compared.
     * @return the object with the greater attackDamage
     */
    public int compare(final Minion m1, final Minion m2) {
        Integer atk1 = m1.getAttackDamage();
        Integer atk2 = m2.getAttackDamage();

        return atk1.compareTo(atk2);
    }
}
