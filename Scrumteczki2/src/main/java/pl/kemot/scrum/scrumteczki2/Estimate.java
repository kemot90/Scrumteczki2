package pl.kemot.scrum.scrumteczki2;

/**
 * Created by Tomek on 05.10.13.
 */
public class Estimate {
    /** Etykieta estymaty. */
    private final String label;
    /** Estymowany czas trwania w godzinach. */
    private final int estimateTimeInHours;
    /** Stała określająca liczbę godzin w dobie. */
    private static final int HOURS_IN_DAY = 24;

    private Estimate(String label, int estimateTimeInHours) {
        this.label = label;
        this.estimateTimeInHours = estimateTimeInHours;
    }

    /**
     * Tworzy instancje estymaty podanej w godzinach.
     * @param hours Estymowany czas w godzinach.
     * @return Estymata w godzinach.
     */
    public static Estimate createInstanceByHours(int hours) {
        String label = String.valueOf(hours);
        return new Estimate(label, hours);
    }

    /**
     * Tworzy instancje estymaty podanej w dniach.
     * @param days Estymowany czas w dniach.
     * @return Estymata w dniach.
     */
    public static Estimate createInstanceByDays(short days) {
        String label = String.valueOf(days) + "d";
        int durationTimeInHours = days * HOURS_IN_DAY;
        return new Estimate(label, durationTimeInHours);
    }

    /**
     * @return Etykieta estymaty.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return Wartość estymaty w godzinach.
     */
    public int getEstimateTimeInHours() {
        return estimateTimeInHours;
    }
}
