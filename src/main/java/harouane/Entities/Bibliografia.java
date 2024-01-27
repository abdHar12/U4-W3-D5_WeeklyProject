package harouane.Entities;

import com.github.javafaker.Faker;
import harouane.Archivio;

import javax.persistence.*;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Entity
@DiscriminatorColumn(name = "tipo_bibliografia")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQuery(name="getAllCatalog", query="SELECT b FROM Bibliografia b")
@NamedQuery(name="findByYear", query="SELECT b FROM Bibliografia b WHERE b.yearOfPubblication=:year")
@NamedQuery(name = "findByAuthor", query="SELECT b FROM Bibliografia b WHERE b.autore=:author")
@NamedQuery(name = "findByTitle", query="SELECT b FROM Bibliografia b WHERE LOWER(b.titolo) LIKE LOWER(:title)")
public abstract class Bibliografia {
    protected static final Faker faker= Archivio.getFaker();
    @Id
    @GeneratedValue
    @Column(nullable = false)
    protected UUID isbn;
    @Column(nullable = false)
    protected String titolo;
    @Column(name = "year_pubblication", nullable = false)
    protected Integer yearOfPubblication;

    @Column(name = "num_page", nullable = false)
    protected Integer numOfPage;

    @OneToMany(mappedBy = "catalog")
    Set<Prestito> loans;
    public Bibliografia(String titolo, Integer yearOfPubblication, Integer numOfPage) {
        this.titolo = titolo;
        this.yearOfPubblication =yearOfPubblication;
        this.numOfPage = numOfPage;
    }
    static Supplier<Integer> yearSupplier=()->{
        Random random= new Random();
        return random.nextInt(1990, 2023);
    };
    static Supplier<Integer> randomNumPage=()->{
        Random random= new Random();
        return random.nextInt(80, 600);
    };
    public UUID getIsbn() {
        return isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public Integer getYearOfPubblication() {
        return yearOfPubblication;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setYearOfPubblication(Integer yearOfPubblication) {
        this.yearOfPubblication = yearOfPubblication;
    }

    public Integer getNumOfPage() {
        return numOfPage;
    }

    public void setNumOfPage(Integer numOfPage) {
        this.numOfPage = numOfPage;
    }

/*
    public static void setAllElements(Set<Bibliografia> allElements) {
        Bibliografia.allElements = allElements;
    }

    public static Set<Bibliografia> getAllElements() {
        return allElements;
    }

    public static void addToAllElements(Bibliografia bibliografia){
        getAllElements().add(bibliografia);
    }

    public static void removeElement(Integer isbn) throws InexistentIsbn {

    }

  public static Bibliografia findElementByIsbn(Integer isbn) throws InexistentIsbn{

    };
*/

}
