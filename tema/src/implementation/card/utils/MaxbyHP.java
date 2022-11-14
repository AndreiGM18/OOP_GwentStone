package implementation.card.utils;

import implementation.card.minion.Minion;

import java.util.Comparator;

public class MaxbyHP implements Comparator<Minion> {
    public int compare(Minion m1, Minion m2) {
        Integer hp1 = m1.getHealth();
        Integer hp2 = m2.getHealth();

        return hp1.compareTo(hp2);
    }
}
