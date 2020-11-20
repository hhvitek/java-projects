import entities.AppEntity;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repositories.AbstractRepository;
import repositories.AppRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;

import java.nio.file.Path;
import java.util.Map;

public class Main {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("my-sqlite");


    public static void main(final String[] args) {

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        AppRepository appRepository = new AppRepository(entityManager);

        AppEntity appEntity = new AppEntity();
        appEntity.setDefaultModFolder(Path.of("home/default folder"));
        appEntity.setChosenModFolder(Path.of("/home/user111"));
        appRepository.create(appEntity);

        System.out.println("Entity created");

        appEntity.setChosenModFolder(Path.of("/home/user222"));

        System.out.println("Entity object changed.");

        appRepository.update(appEntity);

        System.out.println("Entity update.");
    }

}