package dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp.model;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(nullable = false, unique = true)
    String title;

    @Column(nullable = false)
    String author;

}
