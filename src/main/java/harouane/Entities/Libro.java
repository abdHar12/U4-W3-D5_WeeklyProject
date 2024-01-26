package harouane.Entities;

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



    /*ppublic static void addAllBooks(Libro book){
       allBooks.add(book);
   }
  ublic static List<Libro> findElementsByAuthor(String author) throws AuthorNotFound {

   }*/
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
