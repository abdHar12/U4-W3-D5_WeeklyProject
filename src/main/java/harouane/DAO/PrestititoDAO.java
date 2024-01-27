package harouane.DAO;

import harouane.Entities.Prestito;
import harouane.Exceptions.ElementsNotFound;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

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

    public List<Prestito> findLoansNotReturnedByUserCard(UUID numberCard) throws ElementsNotFound {
        TypedQuery<Prestito> getLoans = em.createNamedQuery("findLoansNotReturnedByUserCard", Prestito.class);
        getLoans.setParameter("usercard", numberCard);
        List<Prestito> elements = getLoans.getResultList();
        if (elements.isEmpty()) throw new ElementsNotFound();
        return elements;
    }

    public List<Prestito> findLoansNotReturnedAndExpired() throws ElementsNotFound{
        TypedQuery<Prestito> getLoans = em.createNamedQuery("findLoansNotReturnedAndExpired", Prestito.class);
        List<Prestito> elements = getLoans.getResultList();
        if (elements.isEmpty()) throw new ElementsNotFound();
        return elements;
    }


}
