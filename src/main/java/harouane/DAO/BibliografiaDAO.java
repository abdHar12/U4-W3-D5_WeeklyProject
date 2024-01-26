package harouane.DAO;

import harouane.Entities.Bibliografia;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class BibliografiaDAO {

    private final EntityManager em;

    public BibliografiaDAO(EntityManager em) {
        this.em = em;
    }

    public void saveNewBookOrMagazine(Bibliografia bibliografia){
        EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        em.persist(bibliografia);
        transaction.commit();
        System.out.println("L'elemento " + bibliografia.getTitolo() +" è stato salvato");
    }

}
