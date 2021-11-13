package com.example.learndrool.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class DroolConfig {
    private final KieServices kieServices = KieServices.Factory.get();
    private final String RULE_PATH = "rules/";

    private KieFileSystem getKieFileSystem() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        List<String> rules = Stream.of("customer_discount.xlsx", "employee_tax.xlsx")
                .map(s -> RULE_PATH + s).collect(Collectors.toList());
        for (String rule : rules) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
        }
        return kieFileSystem;
    }

    @Bean
    public KieContainer getKieContainer() {
        getKieRepository();
        KieBuilder kieBuilder = kieServices.newKieBuilder(getKieFileSystem());
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    private void getKieRepository() {
        KieRepository repository = kieServices.getRepository();
        repository.addKieModule(repository::getDefaultReleaseId);
    }

    @Bean
    public KieSession kieSession() {
        getKieRepository();
        KieBuilder kieBuilder = kieServices.newKieBuilder(getKieFileSystem());
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        return kieContainer.newKieSession();
    }
}
