package com.iexec.common.sms.secret;

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
public class SmsSecretResponse extends SmsAbstractResponse {

    private SmsSecretResponseData data;
}