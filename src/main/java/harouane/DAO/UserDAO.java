package harouane.DAO;

import harouane.Entities.User;
import harouane.Exceptions.InexistentNumberCard;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class UserDAO {
    EntityManager em;
    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public void saveNewUser(User user){
        EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        em.persist(user);
        transaction.commit();
        System.out.println("L'user " + user.getName() + " "+ user.getSurname() +" è stato salvato");
    }
    public List<User> getAllUsers(){
        TypedQuery<User> getAllUsers=em.createNamedQuery("getAllUsers", User.class);
        return getAllUsers.getResultList();
    }
    public User findUser(UUID numberCard) throws InexistentNumberCard {
        TypedQuery<User> getSpecificCatalogRecord=em.createQuery("SELECT u FROM User u WHERE u.numberCard=:numberCard", User.class);
        getSpecificCatalogRecord.setParameter("numberCard", numberCard);
        User element = getSpecificCatalogRecord.getSingleResult();
        if (element==null) throw new InexistentNumberCard(numberCard);
        return element;
    }
}
