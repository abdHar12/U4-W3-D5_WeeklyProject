package harouane.DAO;

import harouane.Entities.Prestito;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class PrestititoDAO {
    EntityManager em;

    public PrestititoDAO(EntityManager em) {
        this.em = em;
    }

    public void createNewLoans(Prestito prestito){
        EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        em.persist(prestito);
        transaction.commit();
        System.out.println("Il prestito è stato creato.");
    }
}
