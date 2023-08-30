package com.logicea.cards.seeder;

import com.logicea.cards.model.CardEntity;
import com.logicea.cards.model.UserEntity;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if the database is empty. If it is, seed it.
        if (userRepository.count() == 0) {
            seedUsers();
        }

        if (cardRepository.count() == 0) {
            seedCards();
        }
    }

    private void seedUsers() {
        UserEntity admin = new UserEntity();
        admin.setEmail("admin@logicea.com");
        admin.setRole(UserEntity.UserRole.ADMIN);
        admin.setPassword(passwordEncoder.encode("admin_password"));
        userRepository.save(admin);

        UserEntity member = new UserEntity();
        member.setEmail("member@logicea.com");
        member.setRole(UserEntity.UserRole.MEMBER);
        member.setPassword(passwordEncoder.encode("member_password"));
        userRepository.save(member);
    }

    private void seedCards() {
        UserEntity member = userRepository.findByEmail("member@logicea.com");
        
        CardEntity card1 = new CardEntity();
        card1.setName("Sample Card 1");
        card1.setDescription("This is a sample card for seeding.");
        card1.setUser(member);
        cardRepository.save(card1);

        CardEntity card2 = new CardEntity();
        card2.setName("Sample Card 2");
        card2.setDescription("Another sample card for seeding.");
        card2.setUser(member);
        cardRepository.save(card2);
    }
}
