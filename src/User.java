import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String imie;
    private String nazwisko;
    private List<Ksiazka> wypozyczoneKsiazki;
    private List<Ksiazka> zwroconeKsiazki;
    private static int  number=100;
    private static final String FILE_PATH = "user_number.txt";


    public User( String imie, String nazwisko) {
        this.id = getNextId();
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wypozyczoneKsiazki = new ArrayList<>();
        this.zwroconeKsiazki = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public List<Ksiazka> getWypozyczoneKsiazki() {
        return wypozyczoneKsiazki;
    }

    public List<Ksiazka> getZwroconeKsiazki() {
        return zwroconeKsiazki;
    }

    public void dodajWypozyczonaKsiazke(Ksiazka ksiazka) {
        wypozyczoneKsiazki.add(ksiazka);
    }

    public void dodajZwroconaKsiazke(Ksiazka ksiazka) {
        zwroconeKsiazki.add(ksiazka);
    }

    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", imie='" + imie + "', nazwisko='" + nazwisko + "'}";
    }
    /**
     * Zwraca kolejny dostępny identyfikator użytkownika.
     * Zapisuje wartość number do pliku, aby była przechowywana między uruchomieniami.
     * @return następny identyfikator
     */
    private static int getNextId() {
        loadNumber();
        int currentId = number;
        number++;
        saveNumber();
        return currentId;
    }

    /**
     * Wczytuje ostatni zapisany numer identyfikatora użytkownika z pliku.
     */
    private static void loadNumber() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = br.readLine();
            if (line != null) {
                number = Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            number = 100;
        }
    }

    /**
     * Zapisuje aktualny numer identyfikatora użytkownika do pliku.
     */
    private static void saveNumber() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write(String.valueOf(number));
        } catch (IOException e) {
            System.err.println("Błąd zapisu numeru identyfikatora do pliku: " + e.getMessage());
        }
    }
}
