package dev.portfolio.oussama.service;

import dev.portfolio.oussama.model.User;
import dev.portfolio.oussama.model.Welcome;
import dev.portfolio.oussama.repository.UserRepository;
import dev.portfolio.oussama.repository.WelcomeRepository;
import dev.portfolio.oussama.util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WelcomeService {
    private final WelcomeRepository welcomeRepository;
    public WelcomeService(WelcomeRepository welcomeRepository) {
        this.welcomeRepository = welcomeRepository;

    }
    public Optional<Welcome> get(){
        Optional<Welcome> welcome = welcomeRepository.findFirstByOrderByIdAsc();
        return welcome;

    }
    public Optional<Welcome> save(String title, String tags, String description, String image, String cv) {
        // Retrieve the first record or create a new one if not present
        Optional<Welcome> welcome = welcomeRepository.findFirstByOrderByIdAsc();
        Welcome entity = welcome.orElseGet(Welcome::new);

        // Set the fields
        entity.setTitle(title);
        entity.setTags(tags);
        entity.setDescription(description);
        if (image != null && !image.isEmpty()) {
            entity.setImage(image);
        }
        if (cv != null && !cv.isEmpty()) {
            entity.setCv(cv);
        }

        // Save the entity (use the entity directly, not the Optional)
        Welcome savedEntity = welcomeRepository.save(entity);

        // Return the saved entity
        return Optional.of(savedEntity);
    }

}
