package harouane.DAO;

import harouane.Entities.Bibliografia;
import harouane.Exceptions.DateNotFound;
import harouane.Exceptions.InexistentIsbn;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class BibliografiaDAO {

    private static EntityManager em;

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
    Supplier<Bibliografia> presentBibliografiaSupplier=()->{
        BibliografiaDAO bibliografiaDAO= new BibliografiaDAO(em);
        List<Bibliografia> bibliografiaList=bibliografiaDAO.getAllCatalog();
        Random random= new Random();
        Bibliografia bibliografia=bibliografiaList.get(random.nextInt(0, bibliografiaList.size()));
        return bibliografia;
    };

    public Bibliografia findCatalog(UUID isbn){
        TypedQuery<Bibliografia> getSpecificCatalogRecord=em.createQuery("SELECT b FROM Bibliografia b WHERE b.isbn=:isbn", Bibliografia.class);
        getSpecificCatalogRecord.setParameter("isbn", isbn);
        return getSpecificCatalogRecord.getSingleResult();
    }
    public List<Bibliografia> getAllCatalog(){
        TypedQuery<Bibliografia> getAllCatalog=em.createNamedQuery("getAllCatalog", Bibliografia.class);
        return getAllCatalog.getResultList();
    }
    public void removeElementFromCatalog(UUID isbn) throws InexistentIsbn {
        EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        em.remove(findCatalog(isbn));
        transaction.commit();
        System.out.println("Elemento rimosso");
    }

    public List<Bibliografia> findElementsByDate(Integer year) throws DateNotFound {
        TypedQuery<Bibliografia> getAllBooksByDate= em.createNamedQuery("findByYear", Bibliografia.class);
        getAllBooksByDate.setParameter("year", year);
        return getAllBooksByDate.getResultList();
    }
}
