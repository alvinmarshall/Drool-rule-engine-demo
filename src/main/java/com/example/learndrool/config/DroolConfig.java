package com.example.learndrool.config;

import org.drools.decisiontable.DecisionTableProviderImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
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
        List<String> rules = Stream.of("customer_discount.xlsx")
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
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//        kieFileSystem.write(ResourceFactory.newClassPathResource(RULE_PATH + "order.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULE_PATH + "customer_discount.xlsx"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        return kieContainer.newKieSession();
    }

    public KieSession kieSession(Resource dt) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(dt);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieRepository repository = kieServices.getRepository();
        ReleaseId defaultReleaseId = repository.getDefaultReleaseId();
        KieContainer kieContainer = kieServices.newKieContainer(defaultReleaseId);
        return kieContainer.newKieSession();
    }

    public String getDrlFromExcel(String excelFile) {
        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);

        Resource dt = ResourceFactory.newClassPathResource(excelFile, getClass());

        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();

        return decisionTableProvider.loadFromResource(dt, null);
    }
}
