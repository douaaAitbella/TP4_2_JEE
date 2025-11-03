package net.codejava;

import jakarta.persistence.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BookUnit");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Publisher gallimard = new Publisher();
            gallimard.setName("Gallimard");
            gallimard.setCountry("France");

            Publisher flammarion = new Publisher();
            flammarion.setName("Flammarion");
            flammarion.setCountry("France");

            Category catRoman = new Category();
            catRoman.setName("Roman");

            Category catClassique = new Category();
            catClassique.setName("Classique");


            Book book1 = new Book();
            book1.setTitle("L'Étranger");
            book1.setAuthor("Albert Camus");
            book1.setPrice(10.00);
            book1.setDiscountedPrice(8.50);
            book1.setPublisher(gallimard);
            book1.getCategories().add(catRoman);
            book1.getCategories().add(catClassique);
            catRoman.getBooks().add(book1);
            catClassique.getBooks().add(book1);

            Review r1 = new Review();
            r1.setComment("Un classique intemporel !");
            r1.setBook(book1);
            Review r2 = new Review();
            r2.setComment("Lecture marquante et philosophique.");
            r2.setBook(book1);
            book1.getReviews().add(r1);
            book1.getReviews().add(r2);

            Book book2 = new Book();
            book2.setTitle("Madame Bovary");
            book2.setAuthor("Gustave Flaubert");
            book2.setPrice(8.50);
            book2.setDiscountedPrice(7.00);
            book2.setPublisher(flammarion);
            book2.getCategories().add(catRoman);
            book2.getCategories().add(catClassique);
            catRoman.getBooks().add(book2);
            catClassique.getBooks().add(book2);

            Review r3 = new Review();
            r3.setComment("Un chef-d'œuvre de la littérature française.");
            r3.setBook(book2);
            book2.getReviews().add(r3);

            Book book3 = new Book();
            book3.setTitle("Le Petit Prince");
            book3.setAuthor("Antoine de Saint-Exupéry");
            book3.setPrice(12.00);
            book3.setDiscountedPrice(10.50);
            book3.setPublisher(gallimard);
            book3.getCategories().add(catRoman);
            catRoman.getBooks().add(book3);

            Review r4 = new Review();
            r4.setComment("Un conte poétique et philosophique.");
            r4.setBook(book3);
            book3.getReviews().add(r4);

            em.persist(gallimard);
            em.persist(flammarion);
            em.persist(catRoman);
            em.persist(catClassique);
            em.persist(book1);
            em.persist(book2);
            em.persist(book3);

            em.getTransaction().commit();

            System.out.println("\n=== Informations du livre 'L'Étranger' ===");
            Book foundBook = em.find(Book.class, book1.getId());
            System.out.println("Titre : " + foundBook.getTitle());
            System.out.println("Auteur : " + foundBook.getAuthor());
            System.out.println("Éditeur : " + foundBook.getPublisher().getName());
            System.out.println("Catégories : ");
            foundBook.getCategories().forEach(c -> System.out.println(" - " + c.getName()));
            System.out.println("Avis : ");
            foundBook.getReviews().forEach(r -> System.out.println(" - " + r.getComment()));

            em.getTransaction().begin();
            System.out.println("\n=== Test du chargement (EAGER/LAZY) ===");
            Book lazyBook = em.find(Book.class, book2.getId());
            System.out.println("Titre : " + lazyBook.getTitle());
            System.out.println("Nombre d'avis chargés : " + lazyBook.getReviews().size());
            em.getTransaction().commit();

        } finally {
            em.close();
            emf.close();
        }
    }
}
