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

package com.iexec.common.chain.eip712.entity;

import com.iexec.common.chain.eip712.EIP712Domain;
import com.iexec.common.chain.eip712.EIP712Entity;
import com.iexec.common.chain.eip712.TypeParam;
import com.iexec.common.contract.generated.IexecLibOrders_v5;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Getter
public class EIP712DatasetOrder extends EIP712Entity<IexecLibOrders_v5.DatasetOrder> {

    public EIP712DatasetOrder(EIP712Domain domain, IexecLibOrders_v5.DatasetOrder datasetOrder) {
        super(domain, datasetOrder);
    }

    @Override
    public String getPrimaryType() {
        return "DatasetOrder";
    }

    @Override
    public List<TypeParam> getMessageTypeParams() {
        return Arrays.asList(
                new TypeParam("dataset", "address"),
                new TypeParam("datasetprice", "uint256"),
                new TypeParam("volume", "uint256"),
                new TypeParam("tag", "bytes32"),
                new TypeParam("apprestrict", "address"),
                new TypeParam("workerpoolrestrict", "address"),
                new TypeParam("requesterrestrict", "address"),
                new TypeParam("salt", "bytes32"));
    }

    @Override
    public String getMessageHash() {
        return super.hashMessageValues(
                getMessage().dataset,
                getMessage().datasetprice,
                getMessage().volume,
                getMessage().tag,
                getMessage().apprestrict,
                getMessage().workerpoolrestrict,
                getMessage().requesterrestrict,
                getMessage().salt
        );
    }

}


