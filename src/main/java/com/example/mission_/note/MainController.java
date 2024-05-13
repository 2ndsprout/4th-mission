package com.example.mission_.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;


    @GetMapping("/")
    public String main(String keyword, @RequestParam(defaultValue = "false") String isSearchModal, Model model) {

        try {
            MainDataDto mainDataDto = this.mainService.getDefaultMainData(keyword);
            model.addAttribute("mainDataDto", mainDataDto);
            model.addAttribute("isSearchModal", isSearchModal);
            model.addAttribute("keyword", keyword);
        }
        catch (NoSuchElementException e) {
         e.printStackTrace();
         return "redirect:/";
        }
        return "main";
    }

}
