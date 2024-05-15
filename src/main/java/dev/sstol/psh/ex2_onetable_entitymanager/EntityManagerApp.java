package dev.sstol.psh.ex2_onetable_entitymanager;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Persistence;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

public class EntityManagerApp {
   public static void main(String[] args) {
      EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("tmp");

      EntityManager entityManager = emf.createEntityManager();
      EntityTransaction tx = entityManager.getTransaction();
      tx.begin();

      Worker worker1 = new Worker(0, "Name1");
      Worker worker2 = new Worker(0, "Name2");
      Worker worker3 = new Worker(0, "Name3");
      entityManager.persist(worker1);
      entityManager.persist(worker2);
      entityManager.persist(worker3);

      tx.commit();
      entityManager.close();

      entityManager = emf.createEntityManager();
      List<Worker> workers = entityManager
        .createQuery("from Worker", Worker.class).getResultList();
      workers.forEach(System.out::println);
      entityManager.close();

      emf.close();
   }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "workers")
class Worker {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   String name;
}