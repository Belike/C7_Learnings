package com.camunda.training.history;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AuditWithWhitelistPluginConfiguration {

    @Setter @Getter
    private String ttl;

    @Setter @Getter
    private List<String> variableWhitelist = new ArrayList<>();

}
