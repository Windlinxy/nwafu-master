package pers.nwafumaster.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-28 16:03
 **/
@Data
@Component
public class FileConfig {
    @Value("file-service.image-url")
    private String imageUrl;
}
