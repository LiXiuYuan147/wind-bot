package xx.wind.app.healthy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeathlyController {

    @GetMapping("")
    public String healthy() {
        return "healthy";
    }
}
