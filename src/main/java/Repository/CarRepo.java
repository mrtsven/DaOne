package Repository;

import Models.Car;
import MyInterceptors.MyInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
@Interceptors(MyInterceptor.class)
public class CarRepo {

    @PersistenceContext(unitName="daSvenPU")
    private EntityManager entityManager;

    @Transactional(REQUIRED)
    public void create(Car car) {
        entityManager.persist(car);
    }

    public List<Car> findAll() {
        return entityManager.createQuery("SELECT c FROM Car c")
                .getResultList();
    }

    public Car find(long id){
        return entityManager.find(Car.class, id);
    }
}
