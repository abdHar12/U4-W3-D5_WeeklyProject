package harouane.Entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@DiscriminatorColumn(name = "tipo_bibliografia")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Bibliografia {
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

    /*@OneToMany(mappedBy = "catalog")
    Set<Prestito> loans;*/
    public Bibliografia(String titolo, Integer yearOfPubblication, Integer numOfPage) {
        this.titolo = titolo;
        this.yearOfPubblication =yearOfPubblication;
        this.numOfPage = numOfPage;
    }

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
    public static List<Bibliografia> findElementsByDate(Integer year) throws DateNotFound {

    };*/

}
