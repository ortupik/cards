package com.logicea.cards.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(length = 7)
    private String color;  // Assuming a format like #FFFFFF

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private CardStatus status = CardStatus.TO_DO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    // Constructors, getters, setters, and other utility methods ...

    public enum CardStatus {
        TO_DO, IN_PROGRESS, DONE
    }
}
