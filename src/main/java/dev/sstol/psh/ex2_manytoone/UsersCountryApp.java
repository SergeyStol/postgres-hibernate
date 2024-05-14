package dev.sstol.psh.ex2_manytoone;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

public class UsersCountryApp {
   public static void main(String[] args) {
      SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(User.class)
        .addAnnotatedClass(Country.class)
        .buildSessionFactory();

      Session session = sessionFactory.openSession();
      Transaction tx1 = session.beginTransaction();

      Country country1 = new Country(0, "Country1");
      Country country2 = new Country(0, "Country2");
      Country country3 = new Country(0, "Country3");

      User user1 = new User(0, "Name1", country3);
      User user2 = new User(0, "Name2", country2);
      User user3 = new User(0, "Name3", country1);

      session.persist(country1);
      session.persist(country2);
      session.persist(country3);

      session.persist(user1);
      session.persist(user2);
      session.persist(user3);

      tx1.commit();
      session.close();


      session = sessionFactory.openSession();
      List<User> users = session.createQuery("from User", User.class).getResultList();
      users.forEach(System.out::println);
      session.close();

      sessionFactory.close();
   }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   String name;

   @ManyToOne
   private Country country;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "countries")
class Country {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   String title;
}