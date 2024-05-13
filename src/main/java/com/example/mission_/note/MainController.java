package com.example.mission_.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;


    @GetMapping("/")
    public String main(String keyword, String isSearchModal, Model model) {

        try {
            MainDataDto mainDataDto = this.mainService.getDefaultMainData(keyword);
            model.addAttribute("mainDataDto", mainDataDto);
            model.addAttribute("isSearchModal", isSearchModal);
        }
        catch (NoSuchElementException e) {
         e.printStackTrace();
         return "redirect:/";
        }
        return "main";
    }

}
