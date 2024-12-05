package com.picktartup.startup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SetCampaignIdRequest {
    private Long startupId;
    private Integer campaignId;
}