package dev.sstol.psh.ex1_onetable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class StudentsApp {
   public static void main(String[] args) {
      SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Student.class)
        .buildSessionFactory();

      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();

      session.persist(new Student(0, "Name1"));
      session.persist(new Student(0, "Name2"));

      transaction.commit();
      session.close();

      session = sessionFactory.openSession();
      List<Student> students = session.createQuery("from Student", Student.class).list();
      students.forEach(System.out::println);
      session.close();

      sessionFactory.close();
   }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "students")
class Student {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;

   String name;
}
