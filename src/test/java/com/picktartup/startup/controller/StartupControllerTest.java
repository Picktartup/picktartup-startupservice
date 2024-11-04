package com.picktartup.startup.controller;

import com.picktartup.startup.dto.StartupServiceRequest;
import com.picktartup.startup.service.StartupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(StartupController.class) // StartupController만 테스트하도록 설정
class StartupControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc로 컨트롤러의 요청/응답을 모의로 테스트

    @MockBean
    private StartupService startupService; // StartupService를 MockBean으로 주입

    @Test
    void testGetTop6StartupsByProgress() throws Exception {
        // Mock 데이터 준비
        StartupServiceRequest startup1 = new StartupServiceRequest(
                "두잇", "푸드/농업",
                LocalDateTime.parse("2024-11-01T23:59:59"),
                LocalDateTime.parse("2024-11-04T23:59:59"),
                25, 500, 1000
        );
        StartupServiceRequest startup2 = new StartupServiceRequest(
                "리클", "커머스",
                LocalDateTime.parse("2024-12-01T23:59:59"),
                LocalDateTime.parse("2024-12-20T23:59:59"),
                50, 750, 1500
        );

        List<StartupServiceRequest> startups = Arrays.asList(startup1, startup2);

        // 서비스 레이어에서 Mock 데이터 반환하도록 설정
        when(startupService.getTop6StartupsByProgress()).thenReturn(startups);

        // MockMvc를 사용하여 GET 요청 보내고 결과 검증
        mockMvc.perform(get("/api/v1/startups")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 상태 코드가 200인지 확인
                .andExpect(jsonPath("$.status", is(200))) // JSON 응답의 status 값이 200인지 확인
                .andExpect(jsonPath("$.message", is("스타트업 리스트 조회에 성공하였습니다."))) // 성공 메시지 확인
                .andExpect(jsonPath("$.data", hasSize(2))) // data 배열 크기가 2인지 확인
                .andExpect(jsonPath("$.data[0].name", is("두잇"))) // 첫 번째 스타트업의 이름 확인
                .andExpect(jsonPath("$.data[1].name", is("리클"))) // 두 번째 스타트업의 이름 확인
                .andExpect(jsonPath("$.data[0].contractStartDate", is("2024-11-01T23:59:59"))); // 시작 기한이 정확한지 확인
    }
}
