package Authetication;

import Models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User find(String email) {
        return entityManager.find(User.class, email);
    }

    public void detach(User user) {
        entityManager.detach(user);
    }
}
