package dev.pack;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringDemoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoAppApplication.class, args);
    }

    //Inject
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
