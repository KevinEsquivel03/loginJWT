package kaeh.personal.loginjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DemoController {

    @RequestMapping(value = "demo")
    public String test() {
        return "test from secured endpoint";
    }

}
