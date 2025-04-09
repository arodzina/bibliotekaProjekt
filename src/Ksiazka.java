public class Ksiazka {
    private int id;
    private String tytul;
    private String autor;

    public Ksiazka(int id, String tytul, String autor) {
        this.id = id;
        this.tytul = tytul;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public String getTytul() {
        return tytul;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "Ksiazka{id=" + id + ", tytul='" + tytul + "', autor='" + autor + "'}";
    }
}
