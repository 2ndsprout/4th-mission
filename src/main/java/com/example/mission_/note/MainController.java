package com.example.mission_.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String main(Model model) {

        MainDataDto mainDataDto = this.mainService.getDefaultMainData();

        model.addAttribute("mainDataDto", mainDataDto);

        return "main";
    }

}
