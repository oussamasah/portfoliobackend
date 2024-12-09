package dev.portfolio.oussama.service;

import dev.portfolio.oussama.model.Testimonial;
import dev.portfolio.oussama.repository.TestimonialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestimonialService {
    private final TestimonialRepository repo;
    public TestimonialService(TestimonialRepository repo){
        this.repo=repo;
    }


    public Optional<Testimonial> save(String name, String email, String description , String photo){
        Testimonial t = new Testimonial();
        t.setName(name);
        t.setEmail(email);
        t.setDescription(description);

        if(!photo.isEmpty()){
            t.setPhoto(photo);
        }else{
            t.setPhoto("/uploads/testimonial/default-profile.webp");
        }
        this.repo.save(t);
        return Optional.of(t);
    }

    public Optional<List> get(){

        List list = this.repo.findAll();
        return Optional.of(list);
    }
    public Optional<List> getActive(){
        List list = this.repo.findByActive(true);
        return Optional.of(list);
    }
    public Optional<Testimonial> changeState(Long id , boolean active){
        System.out.println("d========"+id+"-------"+active);
        Optional<Testimonial> tes = this.repo.findById(id);
        if(tes.isPresent()){
            tes.get().setActive(active);
            this.repo.save(tes.get());

        }
        return Optional.of(tes.get());
    }
}
