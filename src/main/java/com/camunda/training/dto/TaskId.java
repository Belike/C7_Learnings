package com.camunda.training.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Data
public class TaskId {

    @NotNull(message = "Id may not be null")
    public String id;

    public HashMap<String, Object> variables;
}
