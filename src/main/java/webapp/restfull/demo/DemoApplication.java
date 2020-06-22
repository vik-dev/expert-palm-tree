package webapp.restfull.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import webapp.restfull.demo.accessingdatajpa.models.Role;
import webapp.restfull.demo.accessingdatajpa.models.User;
import webapp.restfull.demo.accessingdatajpa.repository.RoleRepository;
import webapp.restfull.demo.accessingdatajpa.repository.UserRepository;

@SpringBootApplication
public class DemoApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, RoleRepository roleRepository) {
        return (args) -> {

            userRepository.save(new User("Jacky", "Danniels", "1231E"));
            userRepository.save(new User("Jack", "Bauer", "123I"));
            userRepository.save(new User("Chloe", "O'Brian", "123qweR"));
            userRepository.save(new User("Kim", "Bauer", "qwe123qweD"));
            userRepository.save(new User("David", "Palmer", "123123As"));
            userRepository.save(new User("Michelle", "Dessler", "123aSdasd"));


            roleRepository.save(new Role(1, "Администратор"));
            roleRepository.save(new Role(2, "Модератор"));
            roleRepository.save(new Role(3, "Пользователь"));
            roleRepository.save(new Role(4, "Программист"));
            roleRepository.save(new Role(5, "Директор"));

        };
    }

}
