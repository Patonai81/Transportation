package hu.webuni.transportation.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.TreeMap;


@Component
@ConfigurationProperties(prefix = "webuni")
public class DelayConstantProperty {

    private Delay delay = new Delay();

    public Delay getDelay() {
        return delay;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

    public static class Delay {

        private TreeMap<Integer, Double> limits;

        public TreeMap<Integer, Double> getLimits() {
            return limits;
        }

        public void setLimits(TreeMap<Integer, Double> limits) {
            this.limits = limits;
        }
    }
}
