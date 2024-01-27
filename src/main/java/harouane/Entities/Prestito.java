package harouane.Entities;

import com.github.javafaker.Faker;
import harouane.Archivio;
import harouane.DAO.BibliografiaDAO;
import harouane.DAO.PrestititoDAO;
import harouane.DAO.UserDAO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

@Entity
@NamedQuery(name="findLoansNotReturnedByUserCard", query = "SELECT p FROM Prestito p WHERE p.dateReturn IS NULL AND :usercard=p.user.numberCard")
@NamedQuery(name = "findLoansNotReturnedAndExpired", query = "SELECT p FROM Prestito p WHERE p.dateReturn IS NULL AND CURRENT_DATE>p.expectedReturn")
public class Prestito {
    private static final Faker faker= Archivio.getFaker();
    @Id
    @GeneratedValue
    UUID id;
    @Column(name = "date_loan")
    LocalDate dateLoan;
    @Column(name = "date_return")
    LocalDate dateReturn;
    @Column(name = "expected_return")
    LocalDate expectedReturn;
    @ManyToOne
    @JoinColumn(name = "book_magazine_id")
    Bibliografia catalog;

    @ManyToOne
    @JoinColumn(name = "user_card")
    User user;
    public Prestito(LocalDate dateLoan, LocalDate expectedReturn, Bibliografia catalog, User user) {
        this.dateLoan = dateLoan;
        this.expectedReturn = expectedReturn;
        this.catalog = catalog;
        this.user = user;
    }

    @Override
    public String toString() {
        return "\nPrestito{" +
                "dateLoan=" + dateLoan +
                ", dateReturn=" + dateReturn +
                ", expectedReturn=" + expectedReturn +
                ", catalog=" + catalog +
                ", user=" + user +
                '}';
    }

    public LocalDate getDateLoan() {
        return dateLoan;
    }

    public void setDateLoan(LocalDate dateLoan) {
        this.dateLoan = dateLoan;
    }

    public LocalDate getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDate dateReturn) {
        this.dateReturn = dateReturn;
    }

    public LocalDate getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(LocalDate expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    public Bibliografia getCatalog() {
        return catalog;
    }

    public void setCatalog(Bibliografia catalog) {
        this.catalog = catalog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    static Supplier<Prestito> randomLoan=()->{
        Date date=faker.date().between(Date.from(LocalDate.parse("2023-05-01").atStartOfDay(ZoneId.systemDefault()).toInstant()),
                (Date.from(LocalDate.parse("2024-01-26").atStartOfDay(ZoneId.systemDefault()).toInstant())));
        System.out.println(date);
        Random random=new Random();
        LocalDate dateLoan=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
        LocalDate dateReturn=dateLoan.plusDays(30);
        BibliografiaDAO bibliografiaDAO= new BibliografiaDAO(Archivio.em);
        UserDAO userDAO=new UserDAO(Archivio.em);
        List<User> allUser=userDAO.getAllUsers();
        List<Bibliografia> allCatalog=bibliografiaDAO.getAllCatalog();
        if(!allUser.isEmpty() && !allCatalog.isEmpty()){
            User randomUser = allUser.get(random.nextInt(0, allUser.size()));
            Bibliografia randomCatalog = allCatalog.get(random.nextInt(0, allCatalog.size()));
            return new Prestito(dateLoan, dateReturn, randomCatalog, randomUser);
        }
        return null;
    };
    public static void creationLoan(){
        PrestititoDAO prestititoDAO= new PrestititoDAO(Archivio.em);
        Prestito prestito= randomLoan.get();
        if(prestito!=null)
            prestititoDAO.createNewLoans(prestito);
        else System.out.println("Nessun prestito creato");
    }
}
