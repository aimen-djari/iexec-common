/*
 * Copyright 2023 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package com.iexec.common.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PublicConfigurationTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldSerializeAndDeserialize() throws JsonProcessingException {
        PublicConfiguration config = PublicConfiguration.builder().build();
        String jsonString = mapper.writeValueAsString(config);
        assertThat(jsonString).isEqualTo("{\"workerPoolAddress\":null,\"schedulerPublicAddress\":null," +
                "\"blockchainAdapterUrl\":null,\"resultRepositoryURL\":null," +
                "\"askForReplicatePeriod\":0,\"requiredWorkerVersion\":null}");
        PublicConfiguration parsedConfig = mapper.readValue(jsonString, PublicConfiguration.class);
        assertThat(parsedConfig).isEqualTo(config);
    }

}
