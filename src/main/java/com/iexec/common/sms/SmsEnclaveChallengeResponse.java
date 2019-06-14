package com.iexec.common.sms;

import com.iexec.common.sms.SmsAbstractResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SmsEnclaveChallengeResponse extends SmsAbstractResponse {

    private EnclaveChallenge data;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class EnclaveChallenge {

        private String address;
        private String taskid;
    }
}