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
    public String main(Model model, ParamHandler paramHandler) {

        try {
            MainDataDto mainDataDto = this.mainService.getDefaultMainData(paramHandler.getKeyword());
            model.addAttribute("mainDataDto", mainDataDto);
        }
        catch (NoSuchElementException e) {
         e.printStackTrace();
         return paramHandler.getRedirectUrl("/");
        }
        return "main";
    }

}
