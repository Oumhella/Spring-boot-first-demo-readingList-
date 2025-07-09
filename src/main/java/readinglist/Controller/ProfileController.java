package readinglist.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import readinglist.config.SecurityConfig;
import readinglist.service.ReaderRepository;
import readinglist.model.*;

import java.security.Principal;

@Controller
public class ProfileController {


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private ReaderRepository readerRepository;

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        String username = principal.getName();

        Reader reader = readerRepository.findById(username).orElse(null);

        model.addAttribute("reader", reader);

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Reader formReader,
                                @RequestParam(value = "oldpassword",required = false) String oldPassword,
                                @RequestParam(value = "newpassword",required = false) String newPassword,
                                @RequestParam(value = "confirm",required = false) String confirmPassword,
                                Model model,
                                Principal principal) {

        String username = principal.getName();
        Reader currentUser = readerRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (oldPassword == null || !passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            model.addAttribute("error", "Old password is incorrect.");
            model.addAttribute("reader", currentUser);
            return "profile";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirmation do not match.");
            model.addAttribute("reader", currentUser);
            return "profile";
        }

        currentUser.setFullname(formReader.getFullname());
        currentUser.setEmail(formReader.getEmail());
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        readerRepository.save(currentUser);

        model.addAttribute("success", "Profile updated successfully.");
        model.addAttribute("reader", currentUser);
        return "profile";
    }

}
