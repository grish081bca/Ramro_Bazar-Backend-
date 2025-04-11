package com.grish.RamroBazar.Config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dkymmz866");
        config.put("api_key", "332144272195971");
        config.put("api_secret", "GEriKLshvIsGD09mfGkjQg9XD8o");

        return new Cloudinary(config);
    }
}
