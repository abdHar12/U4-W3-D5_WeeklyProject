package harouane;

import com.github.javafaker.Faker;
import harouane.Entities.Choises;
import harouane.Entities.Libro;
import harouane.Entities.Prestito;
import harouane.Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Archivio {
    private static final Faker faker= new Faker();
    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("catalogo_bibliografico");
    public static EntityManager em =  emf.createEntityManager();
    public static Faker getFaker() {
        return faker;
    }
    public static void main(String[] args) {
        User.creationUser();
        Libro.creationBook();
        Prestito.creationLoan();
        Choises.initialChoice();
        em.close();
        emf.close();
    }
}
