/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.camunda.training.history;

import org.camunda.bpm.engine.impl.history.DefaultHistoryRemovalTimeProvider;
import org.camunda.bpm.engine.impl.history.HistoryLevelAudit;
import org.camunda.bpm.engine.impl.history.event.HistoricVariableUpdateEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEventType;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.impl.util.ParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;


public class AuditHistoryLevelWithWhitelist extends HistoryLevelAudit {

    private static final Logger log = LoggerFactory.getLogger(AuditHistoryLevelWithWhitelist.class);
    public static final String NAME = "audit-with-whitelist";
    private static List<String> variableWhitelist;
    private static String ttl;

    public AuditHistoryLevelWithWhitelist(List<String> variableWhitelist, String ttl){
        super();
        this.variableWhitelist = variableWhitelist;
        this.ttl = ttl;
    }

    @Override
    public int getId() {
        return 21;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isHistoryEventProduced(HistoryEventType historyEventType, Object entity) {
        log.debug("HistoryEvent: {} with entity {}", historyEventType, entity);

        // If the entity is null the engine tests if the history level in general
        // handles such history events.
        if (entity == null) {
            return super.isHistoryEventProduced(historyEventType, entity);
        }
        // We are only interested in variable-instance-update-details
        if (historyEventType.getEventName().equals(HistoryEventTypes.VARIABLE_INSTANCE_UPDATE_DETAIL.getEventName())) {
            return isWhitelistedVariableInstance(historyEventType, entity);
        }
        // delegate it to the super class
        return super.isHistoryEventProduced(historyEventType, entity);

    }

    protected boolean isWhitelistedVariableInstance(HistoryEventType historyEventType, Object entity) {

        log.debug("Found entity with update-detail: {}", entity);
        log.debug("variableWhitelist: {}", variableWhitelist);

        if (null == variableWhitelist || variableWhitelist.isEmpty()) {
            log.debug("No whitelisted variables found!");
            return false;
        }
        // All variable details are going to be saved when * is set.
        else if(variableWhitelist.contains("*")){
            return true;
        }else if (entity instanceof HistoricVariableUpdateEventEntity) {
            Date currentTime = ClockUtil.getCurrentTime();
            Integer timeToLiveDays = ParseUtil.parseHistoryTimeToLive(ttl);

            HistoricVariableUpdateEventEntity variableInstance = ((HistoricVariableUpdateEventEntity) entity);
            String variableName = variableInstance.getVariableName();

            Date removalTime = DefaultHistoryRemovalTimeProvider.determineRemovalTime(currentTime, timeToLiveDays);
            variableInstance.setRemovalTime(removalTime);

            log.debug("Found variableName: {}", variableName);
            log.debug("Boolean result: {}", variableWhitelist.contains(variableName));
            return variableWhitelist.contains(variableName);
        }
        return true;
    }

}
