package harouane.DAO;

import harouane.Entities.Bibliografia;
import harouane.Exceptions.AuthorNotFound;
import harouane.Exceptions.DateNotFound;
import harouane.Exceptions.ElementsNotFound;
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

    public Bibliografia findCatalog(UUID isbn) throws InexistentIsbn{
        TypedQuery<Bibliografia> getSpecificCatalogRecord=em.createQuery("SELECT b FROM Bibliografia b WHERE b.isbn=:isbn", Bibliografia.class);
        getSpecificCatalogRecord.setParameter("isbn", isbn);
        Bibliografia element = getSpecificCatalogRecord.getSingleResult();
        if (element==null) throw new InexistentIsbn(isbn);
        return element;
    }
    public List<Bibliografia> getAllCatalog() throws ElementsNotFound {
        TypedQuery<Bibliografia> getAllCatalog=em.createNamedQuery("getAllCatalog", Bibliografia.class);
        List<Bibliografia> elements= getAllCatalog.getResultList();
        if(elements.isEmpty()) throw new ElementsNotFound();
        return elements;
    }
    public void removeElementFromCatalog(UUID isbn) throws InexistentIsbn {
        EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        Bibliografia element=findCatalog(isbn);
        if (element==null) throw new InexistentIsbn(isbn);
        em.remove(element);
        transaction.commit();
        System.out.println("Elemento rimosso");
    }

    public List<Bibliografia> findElementsByDate(Integer year) throws DateNotFound {
        TypedQuery<Bibliografia> getAllBooksByDate= em.createNamedQuery("findByYear", Bibliografia.class);
        getAllBooksByDate.setParameter("year", year);
        List<Bibliografia> elements = getAllBooksByDate.getResultList();
        if(elements.isEmpty()) throw new DateNotFound(year);
        return elements;
    }

    public List<Bibliografia> findElementsByAuthor(String author) throws AuthorNotFound {
        TypedQuery<Bibliografia> getAllBookByAuthor = em.createNamedQuery("findByAuthor", Bibliografia.class);
        getAllBookByAuthor.setParameter("author", author);
        List<Bibliografia> elements = getAllBookByAuthor.getResultList();
        if (elements.isEmpty()) throw new AuthorNotFound(author);
        return elements;
    }

    public List<Bibliografia> findByTitle(String title) throws ElementsNotFound{
        TypedQuery<Bibliografia> getAllBookByAuthor = em.createNamedQuery("findByTitle", Bibliografia.class);
        getAllBookByAuthor.setParameter("title", "%"+title+"%");
        List<Bibliografia> elements = getAllBookByAuthor.getResultList();
        if (elements.isEmpty()) throw new ElementsNotFound();
        return elements;
    }

}
