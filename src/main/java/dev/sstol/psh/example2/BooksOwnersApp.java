package dev.sstol.psh.example2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

import java.util.List;

public class BooksOwnersApp {
   public static void main(String[] args) {
      SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Book.class)
        .addAnnotatedClass(Owner.class)
        .buildSessionFactory();

      Session session = sessionFactory.openSession();
      Transaction tx1 = session.beginTransaction();

      Book book1 = new Book(0, "Book1", null);
      Book book2 = new Book(0, "Book2", null);
      Book book3 = new Book(0, "Book3", null);
      Book book4 = new Book(0, "Book4", null);

      Owner owner1 = new Owner(0, "Owner1", List.of(book1, book3));
      Owner owner2 = new Owner(0, "Owner2", List.of(book2, book4));

      session.persist(book1);
      session.persist(book2);
      session.persist(book3);
      session.persist(book4);

      session.persist(owner1);
      session.persist(owner2);

      tx1.commit();
      session.close();
   }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "owners_")
class Owner {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;

   String name;

   @OneToMany(mappedBy = "owner")
   List<Book> books;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "books_")
class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   private String title;

   @ManyToOne
   Owner owner;
}