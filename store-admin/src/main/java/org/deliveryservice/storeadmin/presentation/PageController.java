package org.deliveryservice.storeadmin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @RequestMapping(path = {"", "/main"})
    public String main() {
        return "main";
    }
}
