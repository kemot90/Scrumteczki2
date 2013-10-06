package pl.kemot.scrum.scrumteczki2;

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
}
