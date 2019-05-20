package Repository;

import Models.Car.Car;
import Models.Part.Part;
import MyInterceptors.MyInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
@Interceptors(MyInterceptor.class)
public class PartRepo {

    @PersistenceContext(unitName="daSvenPU")
    private EntityManager entityManager;
    private Validator validator;

    public PartRepo(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Transactional(REQUIRED)
    public Part create(Part part) {

        entityManager.persist(part);

        return part;
    }

    public List<Part> findAll() {
        return entityManager.createQuery("SELECT p FROM Part p")
                .getResultList();
    }

    public Part find(Long id){
        return entityManager.find(Part.class, id);
    }

    public void update(Part part) {
        entityManager.merge(part);
    }

    public void delete(Long id){
        Part deletePart = entityManager.find(Part.class, id);

        entityManager.remove(deletePart);
    }

}
