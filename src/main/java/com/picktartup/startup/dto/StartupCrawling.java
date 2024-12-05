package com.picktartup.startup.dto;

import com.picktartup.startup.entity.Startup;
import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class StartupCrawling {
    private Long startupId;
    private String koreanName;    // 한글 이름
    private String englishName;   // 크롤링용 영문 URL

    private static final Map<String, String> NAME_MAPPING = Map.of(
            "두잇", "doeat",
            "리클", "recl",
            "아정네트웍스", "ajungnetworks",
            "바인드", "bind",
            "뉴닉", "newneek",
            "라프텔","laftel" ,
            "레브잇","levit"
            // 필요한 매핑 추가
    );

    public static StartupCrawling from(Startup startup) {
        return StartupCrawling.builder()
                .startupId(startup.getStartupId())
                .koreanName(startup.getName())
                .englishName(NAME_MAPPING.getOrDefault(
                        startup.getName(),
                        startup.getName().toLowerCase().replaceAll("\\s+", "")
                ))
                .build();
    }
}