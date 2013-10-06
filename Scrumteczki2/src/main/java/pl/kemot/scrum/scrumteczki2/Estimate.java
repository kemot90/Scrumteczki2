package pl.kemot.scrum.scrumteczki2;

/**
 * Created by Tomek on 05.10.13.
 */
public class Estimate implements Comparable<Estimate> {
    /** Etykieta estymaty. */
    private final String label;
    /** Estymowany czas trwania w godzinach. */
    private final Integer estimateTimeInMinutes;
    /** Stała określająca liczbę godzin w dobie. */
    private static final int HOURS_IN_DAY = 24;
    /** Stała określająca ilość minut w godzinie */
    private static final int MINUTES_IN_HOUR = 60;

    private Estimate(String label, Integer estimateTimeInMinutes) {
        this.label = label;
        this.estimateTimeInMinutes = estimateTimeInMinutes;
    }

    /**
     * Tworzy instancje estymaty podanej w godzinach.
     * @param hours Estymowany czas w godzinach.
     * @return Estymata w godzinach.
     */
    public static Estimate createInstanceByHours(Float hours) {
        Integer durationTimeInMinutes = Float.floatToIntBits(hours * Float.intBitsToFloat(MINUTES_IN_HOUR));
        String label = StringUtils.readableFormat(hours);
        return new Estimate(label, durationTimeInMinutes);
    }

    /**
     * Tworzy instancje estymaty podanej w dniach.
     * @param days Estymowany czas w dniach.
     * @return Estymata w dniach.
     */
    public static Estimate createInstanceByDays(short days) {
        String label = String.valueOf(days) + "d";
        Integer durationTimeInMinutes = days * HOURS_IN_DAY * MINUTES_IN_HOUR;
        return new Estimate(label, durationTimeInMinutes);
    }

    /**
     * @return Etykieta estymaty.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return Wartość estymaty w minutach.
     */
    public Integer getEstimateTimeInMinutes() {
        return estimateTimeInMinutes;
    }

    @Override
    public int compareTo(Estimate another) {
        return estimateTimeInMinutes.compareTo(another.estimateTimeInMinutes);
    }
}
