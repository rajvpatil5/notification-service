package com.notification.common.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipient {

    private String email;

    private String phoneNumber;

    private String deviceToken;

}