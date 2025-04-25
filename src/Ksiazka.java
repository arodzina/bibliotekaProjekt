import java.io.*;

/**
 * Klasa reprezentująca książkę w bibliotece.
 * Każda książka ma unikalny identyfikator, tytuł i autora.
 */
public class Ksiazka implements Serializable {
    private int id;
    private String tytul;
    private String autor;
    private static int number = 0;

    private static final String FILE_PATH = "number.txt";

    /**
     * Tworzy nową książkę z podanym tytułem i autorem.
     * Każda książka otrzymuje unikalny identyfikator, który jest automatycznie inkrementowany.
     * @param tytul tytuł książki
     * @param autor autor książki
     */
    public Ksiazka(String tytul, String autor) {
        this.id = getNextId();
        this.tytul = tytul;
        this.autor = autor;
    }

    /**
     * Zwraca identyfikator książki.
     * @return identyfikator książki
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca tytuł książki.
     * @return tytuł książki
     */
    public String getTytul() {
        return tytul;
    }

    /**
     * Zwraca autora książki.
     * @return autor książki
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Zwraca reprezentację tekstową książki w formacie:
     * "Ksiazka{id=ID, tytul='Tytuł', autor='Autor'}"
     * @return reprezentacja książki jako ciąg znaków
     */
    @Override
    public String toString() {
        return "Ksiazka{id=" + id + ", tytul='" + tytul + "', autor='" + autor + "'}";
    }
    /**
     * Zwraca kolejny dostępny identyfikator.
     * Zapisuje wartość number do pliku, aby była przechowywana między uruchomieniami.
     * @return następny identyfikator
     */
    private static int getNextId() {
        loadNumber();
        int currentId = number;
        number++;
        saveNumber(); // Zapisz zmieniony number do pliku
        return currentId;
    }

    /**
     * Wczytuje ostatni zapisany numer identyfikatora z pliku.
     */
    private static void loadNumber() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = br.readLine();
            if (line != null) {
                number = Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            // Jeśli plik nie istnieje lub jest pusty, ustaw number na 0
            number = 0;
        }
    }

    /**
     * Zapisuje aktualny numer identyfikatora do pliku.
     */
    private static void saveNumber() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write(String.valueOf(number));
        } catch (IOException e) {
            System.err.println("Błąd zapisu numeru identyfikatora do pliku: " + e.getMessage());
        }
    }
}

