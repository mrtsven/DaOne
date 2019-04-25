package Repository;

import Models.Car.Car;
import MyInterceptors.MyInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
@Interceptors(MyInterceptor.class)
public class CarRepo {

    @PersistenceContext(unitName="daSvenPU")
    private EntityManager entityManager;
    private Validator validator;

    public CarRepo(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Transactional(REQUIRED)
    public Car create(Car car) {
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        for(ConstraintViolation<Car> violation : violations){
            // Log this?
            System.out.println("VIOLATION!!!!! " + violation.getMessage());
        }
        entityManager.persist(car);
        car.setId(1l);
        return car;
    }

    public List<Car> findAll() {
        return entityManager.createQuery("SELECT c FROM Car c")
                .getResultList();
    }

    public Car find(Long id){
        return entityManager.find(Car.class, id);
    }

}
