package readinglist;
import readinglist.model.Reader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import readinglist.service.ReaderRepository;

@SpringBootApplication
public class ReadingListApplication {

//    private String user;

//    @Bean
//    CommandLineRunner init(ReaderRepository repo) {
//        return args -> {
//            if (!repo.existsById(user)) {
//                Reader reader = new Reader();
//                reader.setUsername(user);
//                reader.setPassword("{noop}password");
//                reader.setFullname("Test User");
//                repo.save(reader);
//            }
//        };
//    }
    public static void main(String[] args) {
        SpringApplication.run(ReadingListApplication.class, args);
    }
}