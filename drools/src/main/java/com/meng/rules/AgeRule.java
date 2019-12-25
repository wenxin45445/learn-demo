package com.meng.rules;

import com.meng.entity.User;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class AgeRule {

    private static KieContainer container;
    private static User user;
    private static KieSession session;
    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        container = kieServices.getKieClasspathContainer();
        session = container.newKieSession("myAgeSession");
        User user = new User();
        user.setName("mengpp");
        user.setAge(20);
        session.insert(user);
        session.fireAllRules();
        session.dispose();
    }
}
