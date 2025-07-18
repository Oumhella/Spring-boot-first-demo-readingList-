package readinglist.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import readinglist.model.Book;
import readinglist.service.ReadingListRepository;
import org.springframework.security.core.Authentication;

import java.util.List;


@Controller
@RequestMapping("/")
public class ReadingListController {
    private ReadingListRepository readingListRepository;
    @Autowired
    public ReadingListController(
            ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeRedirect(Authentication authentication) {
        String username = authentication.getName();
        return "redirect:/" + username;
    }

    @RequestMapping(value="/{reader}", method=RequestMethod.GET)
    public String readersBooks(
            @PathVariable("reader") String reader,
            Model model) {
        List<Book> readingList =
                readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        model.addAttribute("book", new Book());
        model.addAttribute("reader", reader);
        return "readingList";
    }
    @RequestMapping(value="/{reader}", method=RequestMethod.POST)
    public String addToReadingList(
            @PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/{reader}";
    }
}
