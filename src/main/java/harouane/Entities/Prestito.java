package harouane.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;


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
    public Prestito(LocalDate dateLoan, LocalDate dateReturn, LocalDate expectedReturn) {
        this.dateLoan = dateLoan;
        this.dateReturn = dateReturn;
        this.expectedReturn = expectedReturn;
    }

}
