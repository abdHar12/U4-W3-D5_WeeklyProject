package harouane.Entities;

import com.github.javafaker.Faker;
import harouane.Archivio;
import harouane.DAO.UserDAO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Entity
@NamedQuery(name = "getAllUsers", query = "SELECT u FROM User u")
public class User {
    private static Faker faker=Archivio.getFaker();
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
    public static void creationUser(){
        UserDAO userDAO=new UserDAO(Archivio.em);
        userDAO.saveNewUser(randomUser.get());
    }
    static Supplier<User> randomUser=()-> new User(faker.name().firstName(), faker.name().lastName(), faker.date().birthday(12, 80).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

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
