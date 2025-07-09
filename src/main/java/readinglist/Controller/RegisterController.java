package readinglist.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import readinglist.model.Reader;
import readinglist.service.ReaderRepository;

@Controller
public class RegisterController {

    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("reader", new Reader());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Reader reader) {
        if (!reader.getPassword().equals(reader.getConfirmPassword())) {
            return "redirect:/register?error=true";
        }
        reader.setPassword(passwordEncoder.encode(reader.getPassword()));
        readerRepository.save(reader);
        return "redirect:/login";
    }

}


