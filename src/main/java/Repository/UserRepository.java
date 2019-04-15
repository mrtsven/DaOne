package Repository;

import Models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    public void remove(String email) {
        User user = find(email);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public void remove(User user) {
        if (user != null && user.getEmail()!=null && entityManager.contains(user)) {
            entityManager.remove(user);
        }
    }

    public User find(String email) {
        return entityManager.find(User.class, email);
    }

    public void detach(User user) {
        entityManager.detach(user);
    }

    public boolean login(String email, String password) {
        System.out.println(find(email).getPassword().equals(password));
        return find(email).getPassword().equals(password);
    }
}