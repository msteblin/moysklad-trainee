package io.github.msteblin.Marketplace;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalController {
    @GetMapping("/instance")
    public String getInstance() {
        return System.getenv().getOrDefault("HOSTNAME", "unknown");
    }
}
