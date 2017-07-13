package com.chris.tatusafety;

/**
 * Created by Josephine Menge on 05/07/2017.
 */

public class Stage {
    String bus;
    String variousStages;
    String choices;

    public Stage(String bus, String variousStages, String choices) {
        this.bus = bus;
        this.variousStages = variousStages;
        this.choices = choices;
    }
    public Stage() {

    }

    public String getBus() {
        return bus;
    }

    public String getVariousStages() {
        return variousStages;
    }

    public String getChoices() {
        return choices;
    }
}
