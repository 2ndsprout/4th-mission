package com.example.mission_.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.RequestParam;
>>>>>>> 36971aea290851b88cb2a9c89337b4ae8bb304e2

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;


    @GetMapping("/")
    public String main(Model model, ParamHandler paramHandler) {

        try {
            MainDataDto mainDataDto = this.mainService.getDefaultMainData(paramHandler.getKeyword());
            model.addAttribute("mainDataDto", mainDataDto);
        }
        catch (NoSuchElementException e) {
         e.printStackTrace();
         return "redirect:/";
        }
        return "main";
    }

}
