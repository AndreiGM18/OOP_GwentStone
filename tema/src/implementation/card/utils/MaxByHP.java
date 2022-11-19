package implementation.card.utils;

import implementation.card.minion.Minion;

import java.util.Comparator;

public class MaxByHP implements Comparator<Minion> {
    /**
     * Compares the minions by their HP
     * @param m1 the first object to be compared.
     * @param m2 the second object to be compared.
     * @return the object with the greater HP
     */
    public int compare(final Minion m1, final Minion m2) {
        Integer hp1 = m1.getHealth();
        Integer hp2 = m2.getHealth();

        return hp1.compareTo(hp2);
    }
}
