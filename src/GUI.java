import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GUI extends JFrame {
    private Biblioteka biblioteka;
    private static final String PLIK = "biblioteka.dat";

    public GUI() {
        setTitle("Biblioteka");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        try {
            biblioteka = Biblioteka.wczytajZPliku(PLIK);
        } catch (Exception e) {
            biblioteka = new Biblioteka();
        }

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(5, 3, 10, 10));

        // Label z powitaniem
        JLabel powitanieLabel = new JLabel("Witamy w bibliotece. Oto dostępne książki:", SwingConstants.CENTER);
        powitanieLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        // ListView do wyświetlania książek
        JTextArea ksiazkiArea = new JTextArea();
        ksiazkiArea.setEditable(false);
        ksiazkiArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ksiazkiArea.setText(wyswietlDostepneKsiazki());
        JScrollPane scrollPane = new JScrollPane(ksiazkiArea);


        JButton dodajKsiazkeBtn = new JButton("Dodaj książkę");
        JButton wypozyczBtn = new JButton("Wypożycz książkę");
        JButton zwrocBtn = new JButton("Zwróć książkę");
        JButton kontoBtn = new JButton("Stan konta użytkownika");
        JButton dodajUzytkownikaBtn = new JButton("Dodaj użytkownika");
        JButton zamknijBtn = new JButton("Zamknij program");

        dodajKsiazkeBtn.setOpaque(true);
        dodajKsiazkeBtn.setContentAreaFilled(true);
        dodajKsiazkeBtn.setBackground(new Color(60, 179, 113));
        dodajKsiazkeBtn.setForeground(Color.BLACK);
        dodajKsiazkeBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        wypozyczBtn.setOpaque(true);
        wypozyczBtn.setContentAreaFilled(true);
        wypozyczBtn.setBackground(new Color(100, 149, 237));
        wypozyczBtn.setForeground(Color.BLACK);
        wypozyczBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        zwrocBtn.setOpaque(true);
        zwrocBtn.setContentAreaFilled(true);
        zwrocBtn.setBackground(new Color(255, 69, 0));
        zwrocBtn.setForeground(Color.BLACK);
        zwrocBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        kontoBtn.setOpaque(true);
        kontoBtn.setContentAreaFilled(true);
        kontoBtn.setBackground(new Color(255, 215, 0));
        kontoBtn.setForeground(Color.BLACK);
        kontoBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        dodajUzytkownikaBtn.setOpaque(true);
        dodajUzytkownikaBtn.setContentAreaFilled(true);
        dodajUzytkownikaBtn.setBackground(new Color(144, 238, 144));
        dodajUzytkownikaBtn.setForeground(Color.BLACK);
        dodajUzytkownikaBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        zamknijBtn.setOpaque(true);
        zamknijBtn.setContentAreaFilled(true);
        zamknijBtn.setBackground(new Color(100, 20, 160));
        zamknijBtn.setForeground(Color.BLACK);
        zamknijBtn.setFont(new Font("SansSerif", Font.BOLD, 14));


        dodajKsiazkeBtn.addActionListener((ActionEvent e) -> dodajKsiazke());
        wypozyczBtn.addActionListener(e -> wypozyczKsiazke());
        zwrocBtn.addActionListener(e -> zwrocKsiazke());
        kontoBtn.addActionListener(e -> pokazKontoUzytkownika());
        dodajUzytkownikaBtn.addActionListener(e -> dodajUzytkownika());
        zamknijBtn.addActionListener(e -> System.exit(0));


        JPanel przyciskiPanel = new JPanel();
        przyciskiPanel.setLayout(new GridLayout(2, 3, 10, 10));
        przyciskiPanel.add(dodajKsiazkeBtn);
        przyciskiPanel.add(wypozyczBtn);
        przyciskiPanel.add(zwrocBtn);
        przyciskiPanel.add(kontoBtn);
        przyciskiPanel.add(dodajUzytkownikaBtn);
        przyciskiPanel.add(zamknijBtn);


        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(powitanieLabel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(przyciskiPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String wyswietlDostepneKsiazki() {
        StringBuilder sb = new StringBuilder();
        for (Ksiazka ksiazka : biblioteka.getKsiazki()) {
            sb.append(ksiazka.getTytul()).append(" - ").append(ksiazka.getAutor()).append("\n");
        }
        return sb.toString();
    }

    private void dodajKsiazke() {
        String tytul = JOptionPane.showInputDialog(this, "Podaj tytuł książki");
        String autor = JOptionPane.showInputDialog(this, "Podaj autora książki");

        if (tytul != null && !tytul.trim().isEmpty() && autor != null && !autor.trim().isEmpty()) {
            biblioteka.dodajKsiazke(new Ksiazka(tytul, autor));
            JOptionPane.showMessageDialog(this, "Dodano książkę");
            zapisz();
            ((JTextArea) ((JScrollPane) getContentPane().getComponent(1)).getViewport().getView()).setText(wyswietlDostepneKsiazki());
        } else {
            JOptionPane.showMessageDialog(this, "Tytuł i autor nie mogą być puste");
        }
    }

    private void wypozyczKsiazke() {
        if (biblioteka.getKsiazki().isEmpty() || biblioteka.getUzytkownicy().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Brak książek lub użytkowników");
            return;
        }

        Object[] ksiazkiArray = biblioteka.getKsiazki().toArray();
        Object ksiazka = JOptionPane.showInputDialog(this, "Wybierz książkę:",
                "Wypożyczanie", JOptionPane.QUESTION_MESSAGE, null,
                ksiazkiArray, ksiazkiArray[0]);

        Object[] uzytkownicyArray = biblioteka.getUzytkownicy().toArray();
        Object uzytkownik = JOptionPane.showInputDialog(this, "Wybierz użytkownika:",
                "Wypożyczanie", JOptionPane.QUESTION_MESSAGE, null,
                uzytkownicyArray, uzytkownicyArray[0]);

        if (ksiazka != null && uzytkownik != null) {
            biblioteka.wypozyczKsiazke((Ksiazka) ksiazka, (User) uzytkownik, LocalDate.now());
            JOptionPane.showMessageDialog(this, "Wypożyczono książkę");
            zapisz();
        }
    }

    private void zwrocKsiazke() {
        if (biblioteka.getUzytkownicy().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Brak użytkowników");
            return;
        }

        Object[] uzytkownicyArray = biblioteka.getUzytkownicy().toArray();
        Object uzytkownik = JOptionPane.showInputDialog(this, "Wybierz użytkownika:",
                "Zwracanie książki", JOptionPane.QUESTION_MESSAGE, null,
                uzytkownicyArray, uzytkownicyArray[0]);

        if (uzytkownik != null) {
            User u = (User) uzytkownik;
            List<Ksiazka> wypozyczone = biblioteka.getKsiazkiWypozyczonePrzez(u);

            if (wypozyczone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Brak wypożyczonych książek dla tego użytkownika.");
                return;
            }

            Object[] wypozyczoneArray = wypozyczone.toArray();
            Object ksiazkaDoZwrotu = JOptionPane.showInputDialog(this, "Wybierz książkę do zwrotu:",
                    "Zwracanie książki", JOptionPane.QUESTION_MESSAGE, null,
                    wypozyczoneArray, wypozyczoneArray[0]);

            if (ksiazkaDoZwrotu != null) {
                LocalDate dataZwrotu = LocalDate.now();
                Wypozyczenie pozyczka = null;

                // Szukamy wypożyczenia, które dotyczy tej książki
                for (Wypozyczenie w : biblioteka.getWypozyczenia()) {
                    if (w.getUzytkownik().equals(u) && w.getKsiazka().equals(ksiazkaDoZwrotu) && w.getDataZwrotu() == null) {
                        pozyczka = w;
                        break;
                    }
                }

                if (pozyczka != null) {
                    biblioteka.zwrocKsiazke(pozyczka, dataZwrotu);
                    JOptionPane.showMessageDialog(this, "Zwrócono książkę");
                    zapisz();
                }
            }
        }
    }

    private void pokazKontoUzytkownika() {
        if (biblioteka.getUzytkownicy().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Brak użytkowników");
            return;
        }

        Object[] uzytkownicyArray = biblioteka.getUzytkownicy().toArray();
        Object uzytkownik = JOptionPane.showInputDialog(this, "Wybierz użytkownika:",
                "Konto użytkownika", JOptionPane.QUESTION_MESSAGE, null,
                uzytkownicyArray, uzytkownicyArray[0]);

        if (uzytkownik != null) {
            User u = (User) uzytkownik;
            List<Ksiazka> wypozyczone = biblioteka.getKsiazkiWypozyczonePrzez(u);
            List<Ksiazka> zwrocone = biblioteka.getKsiazkiZwroconePrzez(u);

            StringBuilder sb = new StringBuilder("Konto użytkownika: " + u.getImie() + "\n\n");
            sb.append("Wypożyczone książki:\n");
            for (Ksiazka k : wypozyczone) {
                sb.append("- ").append(k).append("\n");
            }

            sb.append("\nZwrócone książki:\n");
            for (Ksiazka k : zwrocone) {
                sb.append("- ").append(k).append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString(), "Moje konto", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void dodajUzytkownika() {
        String imie = JOptionPane.showInputDialog(this, "Podaj imię użytkownika");
        String nazwisko = JOptionPane.showInputDialog(this, "Podaj nazwisko użytkownika");

        if (imie != null && !imie.trim().isEmpty()&& nazwisko != null && !nazwisko.trim().isEmpty()) {
            User nowyUzytkownik = new User(imie,nazwisko); // Zakładając, że masz odpowiednią klasę User
            biblioteka.dodajUzytkownika(nowyUzytkownik);
            JOptionPane.showMessageDialog(this, "Dodano użytkownika");
            zapisz();
        } else {
            JOptionPane.showMessageDialog(this, "Imię użytkownika nie może być puste");
        }
    }

    private void zapisz() {
        try {
            biblioteka.zapiszDoPliku(PLIK);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Błąd zapisu do pliku");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
