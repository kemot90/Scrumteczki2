package pl.kemot.scrum.scrumteczki2;

/**
 * Created by Tomek on 05.10.13.
 */
public final class Estimate implements Comparable<Estimate> {
    /** Etykieta estymaty. */
    private final String label;
    /** Estymowany czas trwania w godzinach. */
    private final Integer estimateTimeInMinutes;
    /** Stała określająca liczbę godzin w dobie. */
    private static final int HOURS_IN_DAY = 24;
    /** Stała określająca ilość minut w godzinie. */
    private static final int MINUTES_IN_HOUR = 60;
    /** Znak nieskończoności w Unicode. */
    private static final String INFINITY = "\u221E";

    /**
     * Tworzy estymatę o określonej etykiecie i czasie w minutach.
     * @param label Estykieta estymaty.
     * @param estimateTimeInMinutes Estymowany czas w minutach.
     */
    private Estimate(final String label, final Integer estimateTimeInMinutes) {
        this.label = label;
        this.estimateTimeInMinutes = estimateTimeInMinutes;
    }

    /**
     * Tworzy instancje estymaty podanej w godzinach.
     * @param hours Estymowany czas w godzinach (w tym i niepełnych godzinach np. 0.5f oznacza pół godziny).
     * @return Estymata w godzinach.
     */
    public static Estimate createInstanceByHours(Float hours) {
        validate(hours);
        Integer durationTimeInMinutes = Float.floatToIntBits(hours * Float.intBitsToFloat(MINUTES_IN_HOUR));
        String label = StringUtils.readableFormat(hours);
        return new Estimate(label, durationTimeInMinutes);
    }

    /**
     * Tworzy instancje estymaty podanej w godzinach.
     * @param hours Estymowany czas w pełnych godzinach.
     * @return Estymata w godzinach.
     */
    public static Estimate createInstanceByHours(int hours) {
        Integer durationTimeInMinutes = hours * MINUTES_IN_HOUR;
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
     * Stworzy instancję estymaty blokera o nieskończenie długim czasie trwania.
     * @return Estymata blokera o nieskończenie długim czasie trwania.
     */
    public static Estimate createInstanceInfinity() {
        String label = ScrumteczkiApp.getAppContext().getString(R.string.infinity);
        Integer durationTimeInMinutes = Integer.MAX_VALUE;
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

    @Override
    public String toString() {
        return label;
    }

    private static void validate(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument metody nie może być null!");
        }
    }
}
