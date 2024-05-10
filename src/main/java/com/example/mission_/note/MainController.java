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
    public String main(Model model) {

        try {
            MainDataDto mainDataDto = this.mainService.getDefaultMainData();
            model.addAttribute("mainDataDto", mainDataDto);
        }
        catch (NoSuchElementException e) {
         e.printStackTrace();
         return "redirect:/";
        }
        return "main";
    }

}
