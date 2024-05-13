package dev.sstol.psh.example1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class StudentsApp {
   public static void main(String[] args) {
      try (SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Student.class)
        .buildSessionFactory()) {

         Session session = sessionFactory.openSession();
         Transaction transaction = session.beginTransaction();

         session.persist(new Student(0, "Name1"));
         session.persist(new Student(0, "Name2"));

         transaction.commit();
         session.close();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "students_")
class Student {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;

   String name;
}
