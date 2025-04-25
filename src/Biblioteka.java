import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 * Klasa zarządzająca zasobami biblioteki, takimi jak książki,
 * użytkownicy i wypożyczenia.
 */
public class Biblioteka implements Serializable {
    private List<Ksiazka> ksiazki;
    private List<User> uzytkownicy;
    private List<Wypozyczenie> wypozyczenia;
    /**
     * Tworzy nową instancję biblioteki z pustymi listami książek,
     * użytkowników i wypożyczeń.
     */
    public Biblioteka() {
        ksiazki = new ArrayList<>();
        uzytkownicy = new ArrayList<>();
        wypozyczenia = new ArrayList<>();
    }
    /**
     * Dodaje książkę do biblioteki.
     * @param ksiazka książka do dodania
     */
    public void dodajKsiazke(Ksiazka ksiazka) {
        ksiazki.add(ksiazka);
    }
    /**
     * Dodaje użytkownika do biblioteki.
     * @param uzytkownik użytkownik do dodania
     */
    public void dodajUzytkownika(User uzytkownik) {
        uzytkownicy.add(uzytkownik);
    }

    /**
     * Wypożycza książkę użytkownikowi, jeśli nie została jeszcze wypożyczona.
     * @param ksiazka książka do wypożyczenia
     * @param uzytkownik użytkownik wypożyczający książkę
     * @param dataWypozyczenia data wypożyczenia
     * @throws IllegalStateException jeśli książka jest już wypożyczona
     */
    public void wypozyczKsiazke(Ksiazka ksiazka, User uzytkownik, LocalDate dataWypozyczenia) {
        boolean juzWypozyczona = wypozyczenia.stream()
                .anyMatch(w -> w.getKsiazka().equals(ksiazka) && !w.isZwrocona());

        if (juzWypozyczona) {
            throw new IllegalStateException("Ta książka jest już wypożyczona.");
        }

        Wypozyczenie pozyczka = new Wypozyczenie(ksiazka, uzytkownik, dataWypozyczenia);
        wypozyczenia.add(pozyczka);
    }


    /**
     * Zwraca książkę.
     * @param pozyczka wypożyczenie do oznaczenia jako zwrócone
     * @param dataZwrotu data zwrotu
     */

    public void zwrocKsiazke(Wypozyczenie pozyczka, LocalDate dataZwrotu) {
        pozyczka.zwrocKsiazke(dataZwrotu);
    }
    /**
     * Zwraca listę wszystkich książek w bibliotece.
     * @return lista książek
     */
    public List<Ksiazka> getKsiazki() {
        return ksiazki;
    }

    /**
     * Zwraca listę wszystkich użytkowników biblioteki.
     * @return lista użytkowników
     */
    public List<User> getUzytkownicy() {
        return uzytkownicy;
    }

    /**
     * Zwraca listę wszystkich wypożyczeń w bibliotece.
     * @return lista wypożyczeń
     */
    public List<Wypozyczenie> getWypozyczenia() {
        return wypozyczenia;
    }

    /**
     * Zwraca listę książek wypożyczonych przez danego użytkownika.
     * @param uzytkownik użytkownik
     * @return lista wypożyczonych książek
     */
    public List<Ksiazka> getKsiazkiWypozyczonePrzez(User uzytkownik) {
        return wypozyczenia.stream()
                .filter(w -> w.getUzytkownik().equals(uzytkownik) && !w.isZwrocona())
                .map(Wypozyczenie::getKsiazka)
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę książek zwróconych przez danego użytkownika.
     * @param uzytkownik użytkownik
     * @return lista zwróconych książek
     */
    public List<Ksiazka> getKsiazkiZwroconePrzez(User uzytkownik) {
        return wypozyczenia.stream()
                .filter(w -> w.getUzytkownik().equals(uzytkownik) && w.isZwrocona())
                .map(Wypozyczenie::getKsiazka)
                .collect(Collectors.toList());
    }

    /**
     * Zapisuje stan biblioteki do pliku.
     * @param plik ścieżka do pliku
     * @throws IOException w przypadku błędu zapisu
     */
    public void zapiszDoPliku(String plik) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(plik))) {
            out.writeObject(this);
        }
    }

    /**
     * Wczytuje stan biblioteki z pliku.
     * @param plik ścieżka do pliku
     * @return obiekt Biblioteka wczytany z pliku
     * @throws IOException w przypadku błędu odczytu
     * @throws ClassNotFoundException jeśli klasa nie została znaleziona
     */
    public static Biblioteka wczytajZPliku(String plik) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(plik))) {
            return (Biblioteka) in.readObject();
        }
    }
}

