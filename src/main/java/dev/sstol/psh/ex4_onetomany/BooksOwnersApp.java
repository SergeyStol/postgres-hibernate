package dev.sstol.psh.ex4_onetomany;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

      book1.setOwner(owner1);
      book3.setOwner(owner1);
      book2.setOwner(owner2);
      book4.setOwner(owner2);

      session.persist(book1);
      session.persist(book2);
      session.persist(book3);
      session.persist(book4);

      session.persist(owner1);
      session.persist(owner2);

      tx1.commit();
      session.close();

      System.out.println("****** GET OWNERS *******");
      session = sessionFactory.openSession();
      session.createQuery("from Owner").list().forEach(System.out::println);
      session.close();

      System.out.println("****** GET BOOKS *******");
      session = sessionFactory.openSession();
      session.createQuery("from Book").list().forEach(System.out::println);
      session.close();
      sessionFactory.close();
   }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "owners_")
@Entity
class Owner {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;

   String name;

   @OneToMany(mappedBy = "owner")
   List<Book> books;

   private String getBooksAsString() {
      if (getBooks() == null) {
         return "null";
      }
      StringBuilder builder = new StringBuilder();
      builder.append("(");
      for (Book book : books) {
         builder.append(book.getTitle()).append(", ");
      }
      builder.delete(builder.length() - 2, builder.length());
      builder.append(")");
      return builder.toString();
   }

   @Override
   public String toString() {
      return """
        Owner{\
        id=%s, \
        name=%s, \
        books=%s\
        }""".formatted(getId(), getName(), getBooksAsString());
   }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "books_")
class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   private String title;

   @ManyToOne
   @JoinColumn(name = "owner_id")
   Owner owner;

   @Override
   public String toString() {
      return """
        Book{\
        id=%s \
        title=%s \
        owner=%s \
        }""".formatted(getId(), getTitle(), getOwner() == null ? "null" : getOwner().getName());
   }
}