import jpa.entities.CurrentDataEntity;
import jpa.entities.CurrentDataRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class Main {

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("my-sqlite");

    private static EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
    private static CurrentDataRepository currentDataRepository = new CurrentDataRepository(em);

    public static void main(String[] args) {

        //createNewCurrentData("output_folder", "C\\output\\folder");
        //updateNewCurrentData("output_folder", "newValue");
        List<CurrentDataEntity> entities = currentDataRepository.findAll();
        for(var entity: entities) {
            System.out.println(entity.getName() + " -> " + entity.getValue());
        }
        em.close();
        ENTITY_MANAGER_FACTORY.close();
    }

    private static void createNewCurrentData(String name, String value) {
        CurrentDataRepository currentDataRepository = new CurrentDataRepository(em);
        CurrentDataEntity currentEntity = new CurrentDataEntity(name, value );
        currentDataRepository.create(currentEntity);
    }

    private static void updateNewCurrentData(String name, String newValue) {
        CurrentDataRepository currentDataRepository = new CurrentDataRepository(em);

        Optional<CurrentDataEntity> currentEntityOpt = currentDataRepository.findOneById(name);
        if (currentEntityOpt.isPresent()) {
            em.getTransaction().begin();
            CurrentDataEntity currentEntity = currentEntityOpt.get();
            System.out.println("Current: " + currentEntity.getName() + ":" + currentEntity.getValue());
            currentEntity.setValue(newValue);
            System.out.println("Current: " + currentEntity.getName() + ":" + currentEntity.getValue());
            em.getTransaction().commit();
        }
    }

}
