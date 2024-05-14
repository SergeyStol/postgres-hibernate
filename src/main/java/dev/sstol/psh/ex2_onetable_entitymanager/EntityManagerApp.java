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

/**
 * @author Sergey Stol
 * 2024-05-14
 */
public class EntityManagerApp {
   public static void main(String[] args) {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("tmp");

      EntityManager entityManager = emf.createEntityManager();
      EntityTransaction tx = entityManager.getTransaction();
      tx.begin();

      Worker user1 = new Worker(0, "Name1");

      entityManager.persist(user1);

      tx.commit();
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