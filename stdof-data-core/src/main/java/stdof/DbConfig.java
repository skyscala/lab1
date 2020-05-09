package stdof;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import stdof.lang.util.ExternalPropUtil;

/**
 * Application Configuration class
 *
 */
@Configuration
@ComponentScan(basePackages = "stdof")
@EnableMongoRepositories
public class DbConfig extends AbstractMongoConfiguration {

    private static final Properties PROP = ExternalPropUtil.retrieveProp();

    @Override
    protected String getDatabaseName() {
        return PROP.getProperty("db.name");
    }

    @Override
    public MongoClient mongoClient() {
        MongoClientURI uri = new MongoClientURI(PROP.getProperty("db.url"));
        return new MongoClient(uri);
    }

    @Override
    protected List<String> getMappingBasePackages() {
        return Arrays.asList("stdof");
    }

    @Bean
    @Override
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
    }

    @Bean
    @Override
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());

    }

}
