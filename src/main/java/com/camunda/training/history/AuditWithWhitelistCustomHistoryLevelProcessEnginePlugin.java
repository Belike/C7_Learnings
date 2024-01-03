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


import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Component
@ConfigurationProperties(prefix = "plugin.history")
public class AuditWithWhitelistCustomHistoryLevelProcessEnginePlugin extends AuditWithWhitelistPluginConfiguration implements ProcessEnginePlugin {

    private static final Logger log = LoggerFactory.getLogger(AuditWithWhitelistCustomHistoryLevelProcessEnginePlugin.class);

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        validateWhitelistVariables();

        List<HistoryLevel> customHistoryLevels = processEngineConfiguration.getCustomHistoryLevels();
        AuditHistoryLevelWithWhitelist auditHistoryLevelWithWhitelist = new AuditHistoryLevelWithWhitelist(getVariableWhitelist(), getTtl());
        if (customHistoryLevels == null) {
            customHistoryLevels = new ArrayList<HistoryLevel>();
            processEngineConfiguration.setCustomHistoryLevels(customHistoryLevels);
        }
        customHistoryLevels.add(auditHistoryLevelWithWhitelist);
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {
    }

    private void validateWhitelistVariables() {
        if (getVariableWhitelist().isEmpty() || getVariableWhitelist() == null) {
            log.warn("Whitelist for Variables is empty - No details will be pushed");
            log.warn("Please include variableWhitelist.yml");
        }else {
            log.info("Whitelist found with {} entries", getVariableWhitelist().size());
        }
    }
}
