import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class BibliotekaTest {

    /**
     * Testuje dodawanie książki do biblioteki.
     * Sprawdza, czy po dodaniu książki lista książek zawiera jedną książkę.
     */
    @Test
    public void testDodajKsiazke() {
        Biblioteka b = new Biblioteka();
        b.dodajKsiazke(new Ksiazka("Java", "Nowak"));
        assertEquals(1, b.getKsiazki().size(), "Lista książek powinna zawierać jedną książkę.");
    }

    /**
     * Testuje dodawanie użytkownika do biblioteki.
     * Sprawdza, czy po dodaniu użytkownika lista użytkowników zawiera jednego użytkownika.
     */
    @Test
    public void testDodajUzytkownika() {
        Biblioteka b = new Biblioteka();
        b.dodajUzytkownika(new User("Anna", "Kowalska"));
        assertEquals(1, b.getUzytkownicy().size(), "Lista użytkowników powinna zawierać jednego użytkownika.");
    }

    /**
     * Testuje wypożyczenie książki użytkownikowi.
     * Sprawdza, czy wypożyczenie zostało dodane do listy wypożyczeń.
     */
    @Test
    public void testWypozyczKsiazke() {
        Biblioteka b = new Biblioteka();
        Ksiazka k = new Ksiazka("Java", "Nowak");
        User u = new User("Anna", "Kowalska");
        b.dodajKsiazke(k);
        b.dodajUzytkownika(u);
        b.wypozyczKsiazke(k, u, LocalDate.now());
        assertEquals(1, b.getWypozyczenia().size(), "Powinna być jedno wypożyczenie.");
        assertEquals(u, b.getWypozyczenia().get(0).getUzytkownik(), "Użytkownik wypożyczający książkę powinien być ten sam.");
        assertEquals(k, b.getWypozyczenia().get(0).getKsiazka(), "Wypożyczona książka powinna być ta sama.");
    }

    /**
     * Testuje zwrot książki.
     * Sprawdza, czy po zwrocie książki, data zwrotu jest ustawiona, a książka jest oznaczona jako zwrócona.
     */
    @Test
    public void testZwrotKsiazki() {
        Biblioteka b = new Biblioteka();
        Ksiazka k = new Ksiazka("Java", "Nowak");
        User u = new User("Anna", "Kowalska");
        b.dodajKsiazke(k);
        b.dodajUzytkownika(u);
        b.wypozyczKsiazke(k, u, LocalDate.now());
        Wypozyczenie w = b.getWypozyczenia().get(0);
        b.zwrocKsiazke(w, LocalDate.now().plusDays(7));
        assertNotNull(w.getDataZwrotu(), "Data zwrotu powinna być ustawiona.");
        assertTrue(w.isZwrocona(), "Książka powinna być oznaczona jako zwrócona.");
    }

    /**
     * Testuje, czy książka jest dodana do listy książek w bibliotece.
     * Sprawdza, czy książka "Java" została poprawnie dodana do listy książek.
     */
    @Test
    public void testZawartoscKsiazek() {
        Biblioteka b = new Biblioteka();
        Ksiazka k = new Ksiazka("Java", "Nowak");
        b.dodajKsiazke(k);
        assertTrue(b.getKsiazki().contains(k), "Książka powinna być w liście dostępnych książek.");
    }
}
