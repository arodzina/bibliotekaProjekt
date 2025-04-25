import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Tworzenie biblioteki
        Biblioteka biblioteka = new Biblioteka();

        // Dodawanie książek
        Ksiazka ksiazka1 = new Ksiazka( "Java od podstaw", "Jan Kowalski");
        Ksiazka ksiazka2 = new Ksiazka( "Algorytmy w Javie", "Anna Nowak");

        biblioteka.dodajKsiazke(ksiazka1);
        biblioteka.dodajKsiazke(ksiazka2);

        // Dodawanie użytkowników
        User uzytkownik1 = new User("Tomasz", "Zieliński");
        User uzytkownik2 = new User( "Maria", "Wiśniewska");

        biblioteka.dodajUzytkownika(uzytkownik1);
        biblioteka.dodajUzytkownika(uzytkownik2);

        // Wypożyczenie książki
        biblioteka.wypozyczKsiazke(ksiazka1, uzytkownik1, LocalDate.now());

        // Zwrócenie książki
        Wypozyczenie wypozyczenie = biblioteka.getWypozyczenia().get(0);
        biblioteka.zwrocKsiazke(wypozyczenie, LocalDate.now().plusDays(14));

        // Wyświetlenie danych
        System.out.println("📚 Lista książek:");
        for (Ksiazka k : biblioteka.getKsiazki()) {
            System.out.println(" - " + k);
        }

        System.out.println("\n👤 Lista użytkowników:");
        for (User u : biblioteka.getUzytkownicy()) {
            System.out.println(" - " + u);
        }

        System.out.println("\n📖 Lista wypożyczeń:");
        for (Wypozyczenie w : biblioteka.getWypozyczenia()) {
            System.out.println(" - " + w);
        }
        try {
            biblioteka.zapiszDoPliku("biblioteka.dat");
            System.out.println("Zapisano");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Biblioteka biblioteka2=Biblioteka.wczytajZPliku("biblioteka.dat");
        System.out.println(biblioteka2.getKsiazki());
        System.out.println(biblioteka2.getUzytkownicy());


    }
}
