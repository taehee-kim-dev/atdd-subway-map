package wooteco.subway.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin-line")
    public String adminLine() {
        return "admin-line";
    }

    @GetMapping("/admin-station")
    public String adminStation() {
        return "admin-station";
    }

    @GetMapping("/admin-edge")
    public String adminEdge() {
        return "admin-edge";
    }
}
