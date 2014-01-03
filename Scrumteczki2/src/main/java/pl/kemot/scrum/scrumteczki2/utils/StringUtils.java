package pl.kemot.scrum.scrumteczki2.utils;

/**
 * Created by Tomek on 06.10.13.
 */
public class StringUtils {
    /**
     * Zamienia typ zmiennoprzecinkowy na czytelny, czysty format w postaci ciągu znaków.
     * @param value wartość zmiennoprzecinkowa.
     * @return Liczbę w czytelnym formacie w postaci ciągu znaków. Np.:
     * 123,456000 zwróci 123,456
     * 123,000000 zwróci 123
     */
    public static String readableFormat(double value)
    {
        if(value == (int) value)
            return String.format("%d", (int) value);
        else
            return String.format("%s", value);
    }

    /**
     * Dopełni zerami wartości mniejsze od 10. Np. 0 -> 00, 1 -> 01, 5 -> 05, 11 -> 11.
     * @param value wartość do wyświetlenia z dopełnieniem zerami
     * @return łańcuch znaków reprezentujący wartość dopełnioną zerami
     */
    public static String zerofill(short value, int zeros) {
        return String.format("%0" + zeros +"d", value);
    }
    /**
     * Dopełni zerami wartości mniejsze od 10. Np. 0 -> 00, 1 -> 01, 5 -> 05, 11 -> 11.
     * @param value wartość do wyświetlenia z dopełnieniem zerami
     * @return łańcuch znaków reprezentujący wartość dopełnioną zerami
     */
    public static String zerofill(int value, int zeros) {
        return String.format("%0" + zeros + "d", value);
    }
}
