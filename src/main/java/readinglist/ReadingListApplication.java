package readinglist;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReadingListApplication {
    @Bean
    CommandLineRunner init(ReaderRepository repo) {
        return args -> {
            if (!repo.existsById("user")) {
                Reader reader = new Reader();
                reader.setUsername("user");
                reader.setPassword("{noop}password");
                reader.setFullname("Test User");
                repo.save(reader);
            }
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(ReadingListApplication.class, args);
    }
}