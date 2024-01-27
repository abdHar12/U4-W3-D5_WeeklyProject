package harouane.Entities;

import com.github.javafaker.Faker;
import harouane.Archivio;
import harouane.DAO.BibliografiaDAO;

import javax.persistence.Entity;

@Entity
public class Libro extends Bibliografia{
    String autore;
    String genre;

    public Libro(String titolo, Integer yearOfPubblication, Integer numOfPage, String autore, String genre) {
        super(titolo, yearOfPubblication, numOfPage);
        this.autore = autore;
        this.genre = genre;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public static void creationBook(){
        Libro book;
        String author=faker.book().author();
        String title=faker.book().title();
        String genre= faker.book().genre();
        Integer date= yearSupplier.get();
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(Archivio.em);
        book = new Libro(title, date, randomNumPage.get(), author, genre);
        bibliografiaDAO.saveNewBookOrMagazine(book);
    }
    @Override
    public String toString() {
        return "Libro{" +
                "Isbn: "+isbn +
                ", autore='" + autore + '\'' +
                ", genre='" + genre + '\'' +
                ", titolo='" + titolo + '\'' +
                ", yearOfPubblication=" + yearOfPubblication +
                ", numOfPage=" + numOfPage +
                '}';
    }
}
