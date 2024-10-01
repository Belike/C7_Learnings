package com.camunda.training.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashMap;

@Data
public class TaskId {

    @NotNull(message = "Id may not be null")
    public String id;

    public HashMap<String, Object> variables;
}
