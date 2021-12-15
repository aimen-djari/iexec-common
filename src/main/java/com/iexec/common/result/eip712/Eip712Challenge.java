/*
 * Copyright 2020 IEXEC BLOCKCHAIN TECH
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

package com.iexec.common.result.eip712;

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.TypeParam;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @since 5.9.1
 * @deprecated Such {@link Eip712Challenge} class should extend
 * {@link com.iexec.common.chain.eip712.EIP712Entity}.
 * Upgrade (and move) this class to get this behaviour (PR TODO)
 * or build your own implementation.
 */
@Deprecated(forRemoval = true)
@Data
@Getter
@NoArgsConstructor
public class Eip712Challenge {

    private static final String DOMAIN_NAME = "iExec Result Repository";
    private static final String DOMAIN_VERSION = "1";
    private static final String PRIMARY_TYPE = "Challenge";

    private Types types;
    private EIP712Domain domain;
    private String primaryType;
    private Message message;

    public Eip712Challenge(String challenge, long chainId) {
        this(challenge, chainId, DOMAIN_NAME, DOMAIN_VERSION, PRIMARY_TYPE);
    }

    public Eip712Challenge(String challenge, long chainId, String domainName) {
        this(challenge, chainId, domainName, DOMAIN_VERSION, PRIMARY_TYPE);
    }

    public Eip712Challenge(String challenge, long chainId, String domainName, String domainVersion, String primaryType) {
        List<TypeParam> domainTypeParams = Arrays.asList(
                new TypeParam("name", "string"),
                new TypeParam("version", "string"),
                new TypeParam("chainId", "uint256")
        );

        List<TypeParam> messageTypeParams = Arrays.asList(
                new TypeParam("challenge", "string")
        );

        Types types = new Types(domainTypeParams, messageTypeParams);

        EIP712Domain domain = new EIP712Domain(domainName, domainVersion, chainId, null);

        Message message = Message.builder()
                .challenge(challenge)
                .build();

        this.types = types;
        this.domain = domain;
        this.message = message;
        this.primaryType = primaryType;
    }

}


