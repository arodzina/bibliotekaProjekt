import java.io.Serializable;
import java.time.LocalDate;

/**
 * Klasa reprezentująca wypożyczenie książki przez użytkownika w bibliotece.
 * Zawiera informacje o książce, użytkowniku, dacie wypożyczenia, dacie zwrotu oraz statusie zwrotu.
 */
public class Wypozyczenie implements Serializable {
    private Ksiazka ksiazka;
    private User uzytkownik;
    private LocalDate dataWypozyczenia;
    private LocalDate dataZwrotu;
    private boolean zwrocone;

    /**
     * Tworzy nowe wypożyczenie książki przez użytkownika.
     * @param ksiazka książka wypożyczana
     * @param uzytkownik użytkownik wypożyczający książkę
     * @param dataWypozyczenia data wypożyczenia książki
     */
    public Wypozyczenie(Ksiazka ksiazka, User uzytkownik, LocalDate dataWypozyczenia) {
        this.ksiazka = ksiazka;
        this.uzytkownik = uzytkownik;
        this.dataWypozyczenia = dataWypozyczenia;
        this.zwrocone = false;
    }

    /**
     * Zwraca książkę wypożyczoną przez użytkownika.
     * Ustawia datę zwrotu oraz oznacza książkę jako zwróconą.
     * @param dataZwrotu data zwrotu książki
     */
    public void zwrocKsiazke(LocalDate dataZwrotu) {
        this.dataZwrotu = dataZwrotu;
        this.zwrocone = true;
    }

    /**
     * Zwraca książkę, która została wypożyczona.
     * @return książka wypożyczona
     */
    public Ksiazka getKsiazka() {
        return ksiazka;
    }

    /**
     * Zwraca użytkownika, który wypożyczył książkę.
     * @return użytkownik wypożyczający książkę
     */
    public User getUzytkownik() {
        return uzytkownik;
    }

    /**
     * Zwraca datę wypożyczenia książki.
     * @return data wypożyczenia książki
     */
    public LocalDate getDataWypozyczenia() {
        return dataWypozyczenia;
    }

    /**
     * Zwraca datę zwrotu książki. Jeśli książka nie została jeszcze zwrócona, wartość jest null.
     * @return data zwrotu książki
     */
    public LocalDate getDataZwrotu() {
        return dataZwrotu;
    }

    /**
     * Sprawdza, czy książka została już zwrócona.
     * @return true, jeśli książka została zwrócona, w przeciwnym razie false
     */
    public boolean isZwrocona() {
        return zwrocone;
    }
}
