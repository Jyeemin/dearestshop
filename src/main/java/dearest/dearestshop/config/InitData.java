package dearest.dearestshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final InitDataService initDataService;

    @Override
    public void run(String... args) throws Exception {
        initDataService.init();
    }
}
