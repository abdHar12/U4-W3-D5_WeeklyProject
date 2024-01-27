package harouane.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NamedQuery(name="findLoansNotReturnedByUserCard", query = "SELECT p FROM Prestito p WHERE p.dateReturn IS NULL AND :usercard=p.user.numberCard")
@NamedQuery(name = "findLoansNotReturnedAndExpired", query = "SELECT p FROM Prestito p WHERE p.dateReturn IS NULL AND CURRENT_DATE>p.expectedReturn")
public class Prestito {
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
}
