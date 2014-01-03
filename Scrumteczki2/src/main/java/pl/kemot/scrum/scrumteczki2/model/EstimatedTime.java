package pl.kemot.scrum.scrumteczki2.model;

import pl.kemot.scrum.scrumteczki2.utils.StringUtils;

/**
 * Created by Tomek on 22.11.13.
 */
public class EstimatedTime implements Comparable<EstimatedTime> {
    private short seconds = 0;
    private short minutes = 0;
    private short hours = 0;

    /**
     * Utworzy obiekt wyestymowanego czasu dla zadanych wartości.
     * @param hours liczba godzin
     * @param minutes liczba minut z przedziału od 0 do 59
     * @param seconds liczba sekund z przedziału od 0 do 59
     */
    public EstimatedTime(short hours, short minutes, short seconds) {
        this.hours = hours;
        setMinutes(minutes);
        setSeconds(seconds);
    }

    /**
     * Utworzy estymowany czas na podstawe łańcucha znaków [godziny]:[minuty]:[sekundy] np. 10:01:59.
     * @param estimatedTime łańcuch znaków [godziny]:[minuty]:[sekundy]
     */
    public EstimatedTime(String estimatedTime) {
        if (estimatedTime == null) {
            throw new NullPointerException("Ciag znakow wyrazajacy estymowany czas nie moze byc null!");
        }
        if (!estimatedTime.matches("\\d*:\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Estymowany czas jest w zlym formacie! Wartosc: " + estimatedTime);
        }
        String[] timeFragments = estimatedTime.split(":");
        this.hours = Short.valueOf(timeFragments[0]);
        setMinutes(Short.valueOf(timeFragments[1]));
        setSeconds(Short.valueOf(timeFragments[2]));
    }

    /**
     * @return liczba sekund dla estymowanego czasu
     */
    public short getSeconds() {
        return seconds;
    }

    /**
     * Ustawia liczbę sekund dla estymowanego czasu. Liczba ta musi być z przedziału od 0 do 59.
     * @param seconds liczba sekund
     */
    public void setSeconds(short seconds) {
        if (!isBetweenZeroAndFiftyNine(seconds)) {
            throw new IllegalArgumentException("Sekundy musza miescic sie w przedziale od 0 do 59. Wartosc: " + seconds);
        }
        this.seconds = seconds;
    }

    /**
     * @return liczba minut dla estymowanego czasu
     */
    public short getMinutes() {
        return minutes;
    }

    /**
     * Ustawi liczbę minut dla estymowanego czasu. Liczba ta musi być z przedziału od 0 do 59.
     * @param minutes liczba minut
     */
    public void setMinutes(short minutes) {
        if (!isBetweenZeroAndFiftyNine(minutes)) {
            throw new IllegalArgumentException("Minuty musza miescic sie w przedziale od 0 do 59. Wartosc: " + minutes);
        }
        this.minutes = minutes;
    }

    /**
     * @return liczba godzin dla estymowanego czasu
     */
    public short getHours() {
        return hours;
    }

    /**
     * Ustawi liczbę godzin dla estymowanego czasu.
     * @param hours liczba godzin
     */
    public void setHours(short hours) {
        this.hours = hours;
    }

    /**
     * Sprawdza czy argument jest z przedziału od 0 do 59.
     * @param value sprawdzana wartość
     * @return true jeżeli argument jest z przedziału od 0 do 59 w p.p. false
     */
    private boolean isBetweenZeroAndFiftyNine(short value) {
        if (value < 60 && value > -1) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return hours + ":" + StringUtils.zerofill(minutes, 2) + ":" + StringUtils.zerofill(seconds, 2);
    }

    /**
     * Porówna dwa obiekty estymowanego czasu.
     * @param estimatedTime obiekt estymowanego czasu, do którego zostanie porównany bieżący obiekt
     * @return 1 jeśli czas jest większy, -1 jeśli czas jest mniejszy i 0 jeśli czas jest równy w porównaniu
     * do czasu będącego argumentem wywołania.
     */
    @Override
    public int compareTo(EstimatedTime estimatedTime) {
        if (hours > estimatedTime.hours) {
            return 1;
        }
        if (hours < estimatedTime.hours) {
            return -1;
        }
        if (minutes > estimatedTime.minutes) {
            return 1;
        }
        if (minutes < estimatedTime.minutes) {
            return -1;
        }
        if (seconds > estimatedTime.seconds) {
            return 1;
        }
        if (seconds < estimatedTime.seconds) {
            return -1;
        }
        return 0;
    }
}
