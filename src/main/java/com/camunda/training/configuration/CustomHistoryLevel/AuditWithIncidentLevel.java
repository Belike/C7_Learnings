package com.camunda.training.configuration.CustomHistoryLevel;

import org.camunda.bpm.engine.impl.history.HistoryLevelAudit;
import org.camunda.bpm.engine.impl.history.event.HistoryEventType;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;

public class AuditWithIncidentLevel extends HistoryLevelAudit {
    public AuditWithIncidentLevel() {
    }

    public int getId() {
        return 13;
    }

    public String getName() {
        return "auditWithIncidents";
    }

    public boolean isHistoryEventProduced(HistoryEventType eventType, Object entity) {
        return super.isHistoryEventProduced(eventType, entity) || HistoryEventTypes.VARIABLE_INSTANCE_CREATE == eventType || HistoryEventTypes.VARIABLE_INSTANCE_UPDATE == eventType || HistoryEventTypes.VARIABLE_INSTANCE_MIGRATE == eventType || HistoryEventTypes.VARIABLE_INSTANCE_DELETE == eventType || HistoryEventTypes.FORM_PROPERTY_UPDATE == eventType;
    }
}
