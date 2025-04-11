package com.camunda.training.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JsonSerializationDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Map<String, Object> variables = new HashMap<>();

        String text = "{\n" +
                "  \"myJsonVar\": {\n" +
                "    \"value\": \"{\\\"name\\\":\\\"Alice\\\",\\\"age\\\":30,\\\"roles\\\":[\\\"admin\\\",\\\"user\\\"]}\",\n" +
                "    \"type\": \"Json\",\n" +
                "    \"valueInfo\": {\n" +
                "      \"serializationDataFormat\": \"application/json\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        JsonValue jsonValue = SpinValues.jsonValue(text).create();
        variables.put("ExampleJson", jsonValue);

        ObjectValue realJsonValue = Variables.serializedObjectValue(text)
                .serializationDataFormat("application/json")
                .create();

        variables.put("realJson", jsonValue);

        execution.setVariables(variables);

    }
}
