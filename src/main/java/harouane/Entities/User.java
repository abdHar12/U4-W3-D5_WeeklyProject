package harouane.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@NamedQuery(name = "getAllUsers", query = "SELECT u FROM User u")
public class User {
    @Id
    @GeneratedValue
    UUID numberCard;
    String name;
    String surname;
    @Column(name = "date_birthday")
    LocalDate dateBirthday;

    @OneToMany(mappedBy = "user")
    Set<Prestito> loans;

    public User(String name, String surname, LocalDate dateBirthday) {
        this.name = name;
        this.surname = surname;
        this.dateBirthday = dateBirthday;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateBirthday() {
        return dateBirthday;
    }

    public UUID getNumberCard() {
        return numberCard;
    }


    public Set<Prestito> getLoans() {
        return loans;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateBirthday(LocalDate dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    public void setLoans(Set<Prestito> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "User{" +
                "numberCard=" + numberCard +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateBirthday=" + dateBirthday +
                '}';
    }
}
