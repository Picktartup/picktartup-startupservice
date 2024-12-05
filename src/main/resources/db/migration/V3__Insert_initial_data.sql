INSERT INTO startup (startup_id,
                     name,
                     category,
                     progress,
                     investment_start_date,
                     investment_target_deadline,
                     goal_coin,
                     current_coin,
                     funding_progress,
                     ceo_user_id,
                     logo_url,
                     industry_type,
                     campaign_id)
VALUES (1, '두잇', '푸드/농업', 25, '2024-11-01 23:59:59', '2024-11-04 23:59:59', 1000, 200, NULL, 7, NULL,'팀매칭 배달앱 ''두잇''을 운영하는 기업',9),
       (2, '리클', '커머스', 50, '2024-12-01 23:59:59', '2024-12-20 23:59:59', 1500, 300, NULL, 8, NULL,'모바일 간편 의류 수거 및 프리미엄 리세일 서비스',NULL),
       (3, '바인드', '패션', 10, '2024-12-15 23:59:59', '2024-12-20 23:59:59', 1200, 400, NULL, 35, NULL,'중년 남성 대상 패션 플랫폼 ‘애슬러’를 운영하는 기업',NULL),
       (4, '뉴닉', '콘텐츠/예술', 70, '2024-12-20 23:59:59', '2024-12-31 23:59:59', 2000, 500, NULL, 36, NULL,'MZ세대 타겟 뉴스레터 서비스',NULL),
       (5, '라프텔', '콘텐츠/예술', 90, '2024-12-31 23:59:59', '2025-01-15 23:59:59', 2500, 600, NULL, 37, NULL,'애니메이션 스트리밍 서비스',NULL),
       (6, '아정네트웍스', '통신보안/데이터', 35, '2025-01-31 23:59:59', '2025-02-10 23:59:59', 3000, 38, NULL, 6, NULL,'네트워크, 통신 및 가전제품 렌탈 서비스',NULL),
       (7, '레브잇', '커머스', 35, '2024-12-18 23:59:59', '2025-01-10 23:59:59', 6000, 4500, NULL, 15, NULL,'모바일 초저가 커머스 플랫폼 ''올웨이즈''를 운영하는 기업',NULL);

INSERT INTO startup_details (startup_id,
                             description,
                             investment_status,
                             address,
                             ceo_name,
                             page,
                             establishment_date,
                             contract_period,
                             expected_roi,
                             roi,
                             registration_num,
                             signature,
                             current_round)
VALUES
    (1, '1인 가구를 위한 무료배달앱 - 주식회사 두잇 (Doeat)은 2022년 4월 에 설립된 한국계∙스타트업 입니다. 음식/외식∙주문/배달 분야의 두잇 이 주요 제품/서비스입니다. 본사는 한국∙서울특별시 에 위치해있습니다. 현재 대표자는 이윤석 입니다.
두잇은 세상에 없던 ''모든 가게 배달비 무료''라는 혁신적인 고객 가치로, 리텐션 70%의 폭발적인 성장세를 만들어내고 있는 스타트업입니다.
모든 고객이 ''맛있는 음식을 가장 싸고 빠르게'' 먹을 수 있도록 돕습니다
배달비 없는 배달 플랫폼으로, 주변 이웃과 팀배달을 진행하여 과도한 배달비와 최소주문금액 문제를 해결하고자 합니다.', '비상장',  '서울 관악구 남부순환로 1925, 401호', '이윤석', 'https://doeat.io/', '2022-04-26', 12, 15.5,
     NULL, '123-45-67890', NULL,'Series A'),
    (2, '주식회사 리클 (Recl)은 2021년 11월 에 설립된 한국계∙스타트업 입니다. 패션∙중고거래 분야의 리클 이 주요 제품/서비스입니다. 본사는 한국∙경기도∙남양주시 에 위치해있습니다. 현재 대표자는 양수빈 입니다.
옷의 생명주기를 연장하고, 탄소 저감을 이루어 나가는 것.
불편한 일상의 한 부분을 바꿔, 지구를 위한 일을 함께해 나가는 것.
리클러 여러분과 함께 옷으로 나무를 심어 지구를 구하는 리클입니다.', '비상장', '경기 남양주시 오남읍 양지로 338-8, 2층', '양수빈', 'https://recl.co.kr/', '2021-11-12', 24,
     20.0, NULL, '234-56-78901', NULL,'Pre-A'),
    (3, '주식회사 바인드 (BIND)는 2022년 6월 에 설립된 한국계∙스타트업 입니다. 스포츠∙의류 분야의 애슬러 가 주요 제품/서비스입니다. 본사는 한국∙울산광역시 에 위치해있습니다. 현재 대표자는 김시화 입니다.
BIND는 4565 남성들의 라이프스타일을 바꾸기 위해 패션 커머스 애슬러(Athler)를 시작으로 이들의 삶을 더 편리하게, 행복하게 바꾸고 있어요. 누군가의 아빠라는 몇 십년간 책임으로 힘을 다해온 이들이, 오로지 자신을 위해 운동과 여가로 채우며 살아가는 시점에, MZ 세대와 동일한 편의를 느낄 수 있도록 모바일 세상의 풍요로움을 선사하고자 합니다.
', '비상장',  '울산 남구 대공원로99번길 20-1, 205호', '김시화', 'https://athler.kr/home', '2022-06-10', 6,
     12.5, NULL, '345-67-89012', NULL,'Series A'),
    (4, '주식회사 뉴닉 (newneek)은 2018년 7월 에 설립된 한국계∙스타트업 입니다. 콘텐츠∙인터넷미디어 분야의 뉴닉 이 주요 제품/서비스입니다. 본사는 한국∙서울특별시 에 위치해있습니다. 현재 대표자는 김소연 입니다
뉴닉은 바쁘지만 세상이 궁금한 사람들을 위한 콘텐츠 스타트업입니다. 현재 월/화/수/목/금 아침마다 약 43만 명의 구독자(뉴니커)들에게 시사 뉴스레터를 보내드리고 있어요. 우리의 시사 뉴스레터는 꼭 알아야 할 이슈만 골라, 쉽고 재밌는 대화처럼 풀어냅니다.
', '비상장', '서울 마포구 어울마당로 35, 5층', '김소연', 'https://newneek.co/', '2018-07-09', 36, 25.0,
     NULL, '456-78-90123', NULL,'Series A'),
    (5, '주식회사 라프텔 (Laftel)은 2021년 12월 에 설립된 한국계∙스타트업 입니다. 콘텐츠∙애니메이션/웹툰 분야의 라프텔 이 주요 제품/서비스입니다. 본사는 한국∙서울특별시 에 위치해있습니다. 현재 대표자는 박종원 입니다.
라프텔은 취향에 맞는 만화를 합법적으로 무료로 제공하는 서비스로 네이버북스의 108배 해당하는 사용자 평가 정보를 활용하는 만화 추천 엔진과 월 300만명에게 만화 소식을 전하는 국내 최대의 만화 정보 페이지를 무기로 빠르게 성장하고 있습니다. 만화 콘텐츠 공급을 통해 디지털 콘텐츠 커머스 시장 진입 후, 사용자 취향과 작품 세부 정보에서 도출되는 타겟 광고 시스템을 통해 소비 단가를 낮추고 편의성 개선, 불법 콘텐츠 소비 시장을 정복하고자 합니다.', '비상장','서울 영등포구 국제금융로 10, 13', '김범준', 'https://laftel.net/', '2021-12-10', 18, 18.5,
     NULL, '567-89-01234', NULL,'Series B'),
    (6, '친근하고 익숙한 제품과 서비스로 우리 생활 속에 스며든 ‘AJung’은
항상 고객의 입장에서 편의와 편리를 추구한 결과, 일체의 외부 투자 없이
창업 4년 만에 경이로운 기록을 세웠습니다.

CRM 부문 이외에도 마케팅, 미디어 부문에서 빠르게 성장하여
수 많은 인베스트들로 하여금 러브콜 을 받고 있는 유망기업으로서

탄탄한 자본력, 젊고 열정적인 동료들과 함께 쌓아온 기술과 경험,
서비스 노하우들을 바탕으로 사업영역을 확장하여, 고착화 된 플랫폼
비즈니스의 틀을 깨고 플랫폼 시장의 새로운 기준을 세우고자 합니다.', '비상장',  '부산 북구 만덕대로 117', '김민기', 'https://www.ajd.co.kr/', '2021-05-03', 24,
     22.0, NULL, '678-90-12345', NULL,'Series C'),
    (7, '''
주식회사 아정네트웍스(AJung Networks)는 2021년 5월에 설립된 한국계∙중소기업입니다. 생활∙통신 분야의 아정당이 주요 제품/서비스입니다. 본사는 한국∙부산광역시에 위치해있습니다. 현재 대표자는 김민기입니다. 유사기업은 유앤소프트.리턴제로.비씨엔엠.애니파이 등이 있습니다. 친근하고 익숙한 제품과 서비스로 우리 생활 속에 스며든 ‘AJung’은
항상 고객의 입장에서 편의와 편리를 추구한 결과, 일체의 외부 투자 없이
창업 4년 만에 경이로운 기록을 세웠습니다.', '비상장',  '서울 관악구 봉천로 456, 5층, 6층, 7층, 8층', '강재윤', 'https://team.alwayz.co/', '2021-02-17', 12,
     21.0, NULL, '678-90-12345', NULL,'Series B');


INSERT INTO ssi (ssi_id,
                 eval_date,
                 eval_description,
                 people_grade,
                 performance_grade,
                 potential_grade,
                 product_grade,
                 startup_id)
VALUES (1, '2024-11-14 10:00:00', '두잇 스타트업의 상세 평가', '양호', '우수', '보통', '보통', 1),
       (2, '2024-11-15 10:00:00', '리클 스타트업의 상세 평가', '보통', '우수', '우수', '양호', 2 ),
       (3, '2024-11-16 10:00:00', '바인드 스타트업의 상세 평가', '보통', '보통', '양호', '우수', 3),
       (4, '2024-11-17 10:00:00', '뉴닉 스타트업의 상세 평가', '우수', '우수', '양호', '보통', 4),
       (5, '2024-11-18 10:00:00', '라프텔 스타트업의 상세 평가', '우수', '우수', '우수', '우수', 5),
       (6, '2024-11-19 10:00:00', '아정네트웍스 스타트업의 상세 평가', '보통', '양호', '보통', '보통', 6),
       (7, '2024-11-19 10:00:00', '레브잇 스타트업의 상세 평가', '우수', '보통', '보통', '우수', 7);

INSERT INTO startup_monthlymetrics (monthly_id,
                                    startup_id,
                                    metric_date,
                                    monthly_revenue,
                                    monthly_operating_profit,
                                    monthly_net_profit,
                                    mau, employee_count,
                                    created_at,
                                    data_source)
VALUES
    (1, 1, '2024-01-01', 100000000, 20000000, 15000000, 5000, 20, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (2, 1, '2024-02-01', 120000000, 25000000, 18000000, 5500, 22, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (3, 1, '2024-03-01', 150000000, 30000000, 22000000, 6000, 25, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (4, 1, '2024-04-01', 140000000, 28000000, 21000000, 5800, 23, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (5, 1, '2024-05-01', 160000000, 32000000, 24000000, 6200, 26, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (6, 1, '2024-06-01', 170000000, 34000000, 25500000, 6400, 27, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (7, 1, '2024-07-01', 180000000, 36000000, 27000000, 6600, 28, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (8, 1, '2024-08-01', 190000000, 38000000, 28500000, 6800, 29, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (9, 1, '2024-09-01', 200000000, 40000000, 30000000, 7000, 30, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (10, 1, '2024-10-01', 210000000, 42000000, 31500000, 7200, 31, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (11, 1, '2024-11-01', 220000000, 44000000, 33000000, 7400, 32, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (12, 1, '2024-12-01', 230000000, 46000000, 34500000, 7600, 33, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    -- startup_id = 2
    (13, 2, '2024-01-01', 110000000, 21000000, 16000000, 5100, 21, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (14, 2, '2024-02-01', 90000000, -5000000, -8000000, 4900, 20, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (15, 2, '2024-03-01', 130000000, 26000000, 20000000, 5300, 22, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (16, 2, '2024-04-01', 85000000, -10000000, -12000000, 4800, 19, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (17, 2, '2024-05-01', 150000000, 30000000, 22000000, 5500, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (18, 2, '2024-06-01', 160000000, 32000000, 24000000, 5700, 25, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (19, 2, '2024-07-01', 180000000, 36000000, 27000000, 5900, 26, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (20, 2, '2024-08-01', 170000000, -20000000, -15000000, 5800, 26, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (21, 2, '2024-09-01', 200000000, 40000000, 30000000, 6100, 27, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (22, 2, '2024-10-01', 210000000, 42000000, 31000000, 6200, 28, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (23, 2, '2024-11-01', 220000000, 44000000, 32000000, 6300, 29, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (24, 2, '2024-12-01', 190000000, -1000000, -500000, 6000, 28, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),

    -- startup_id = 3
    (25, 3, '2024-01-01', 80000000, -2000000, -5000000, 4200, 15, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (26, 3, '2024-02-01', 95000000, 18000000, 14000000, 4500, 16, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (27, 3, '2024-03-01', 120000000, 25000000, 20000000, 5000, 17, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (28, 3, '2024-04-01', 87000000, -7000000, -10000000, 4300, 15, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (29, 3, '2024-05-01', 130000000, 28000000, 21000000, 5200, 18, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (30, 3, '2024-06-01', 140000000, 30000000, 22000000, 5400, 19, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (31, 3, '2024-07-01', 125000000, -3000000, -7000000, 5100, 18, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (32, 3, '2024-08-01', 135000000, 27000000, 21000000, 5300, 18, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (33, 3, '2024-09-01', 150000000, 32000000, 25000000, 5500, 19, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (34, 3, '2024-10-01', 140000000, 29000000, 23000000, 5400, 20, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (35, 3, '2024-11-01', 130000000, -1000000, -500000, 5200, 19, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (36, 3, '2024-12-01', 120000000, -3000000, -6000000, 5000, 18, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),

    -- startup_id = 4
    (37, 4, '2024-01-01', 100000000, 20000000, 15000000, 5100, 21, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (38, 4, '2024-02-01', 90000000, -5000000, -10000000, 4900, 20, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (39, 4, '2024-03-01', 120000000, 25000000, 18000000, 5300, 22, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (40, 4, '2024-04-01', 87000000, -10000000, -12000000, 4700, 19, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (41, 4, '2024-05-01', 125000000, 26000000, 20000000, 5400, 23, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (42, 4, '2024-06-01', 140000000, 30000000, 22000000, 5500, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (43, 4, '2024-07-01', 130000000, 28000000, 21000000, 5300, 23, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (44, 4, '2024-08-01', 145000000, 32000000, 25000000, 5600, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (45, 4, '2024-09-01', 150000000, 34000000, 26000000, 5700, 25, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (46, 4, '2024-10-01', 160000000, 35000000, 27000000, 5800, 26, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (47, 4, '2024-11-01', 155000000, 33000000, 26000000, 5700, 25, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (48, 4, '2024-12-01', 140000000, -5000000, -8000000, 5400, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),

    -- startup_id = 5
    (49, 5, '2024-01-01', 95000000, -3000000, -5000000, 4500, 18, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (50, 5, '2024-02-01', 105000000, 18000000, 14000000, 4600, 19, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (51, 5, '2024-03-01', 120000000, 25000000, 20000000, 4900, 20, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (52, 5, '2024-04-01', 85000000, -5000000, -8000000, 4200, 18, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (53, 5, '2024-05-01', 130000000, 28000000, 22000000, 5100, 21, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (54, 5, '2024-06-01', 140000000, 30000000, 24000000, 5200, 22, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (55, 5, '2024-07-01', 135000000, 28000000, 23000000, 5000, 21, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (56, 5, '2024-08-01', 145000000, 32000000, 25000000, 5300, 22, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (57, 5, '2024-09-01', 150000000, 34000000, 27000000, 5400, 23, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (58, 5, '2024-10-01', 160000000, 35000000, 28000000, 5600, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (59, 5, '2024-11-01', 140000000, -2000000, -4000000, 5200, 22, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (60, 5, '2024-12-01', 130000000, -4000000, -7000000, 5000, 21, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),

    -- startup_id = 6
    (61, 6, '2024-01-01', 150000000, 25000000, 20000000, 5500, 23, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (62, 6, '2024-02-01', 140000000, -3000000, -7000000, 5300, 22, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (63, 6, '2024-03-01', 160000000, 32000000, 27000000, 5800, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (64, 6, '2024-04-01', 155000000, 30000000, 25000000, 5700, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (65, 6, '2024-05-01', 170000000, 34000000, 28000000, 5900, 25, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (66, 6, '2024-06-01', 180000000, 36000000, 29000000, 6000, 26, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (67, 6, '2024-07-01', 190000000, 38000000, 30000000, 6200, 27, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (68, 6, '2024-08-01', 160000000, -20000000, -18000000, 5500, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (69, 6, '2024-09-01', 200000000, 40000000, 32000000, 6500, 28, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (70, 6, '2024-10-01', 210000000, 42000000, 34000000, 6600, 29, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (71, 6, '2024-11-01', 190000000, -5000000, -8000000, 6100, 27, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (72, 6, '2024-12-01', 180000000, 35000000, 28000000, 6000, 26, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),

    -- startup_id = 7
    (73, 7, '2024-01-01', 150000000, 25000000, 20000000, 5500, 23, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (74, 7, '2024-02-01', 140000000, -3000000, -7000000, 5300, 22, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (75, 7, '2024-03-01', 160000000, 32000000, 27000000, 5800, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (76, 7, '2024-04-01', 155000000, 30000000, 25000000, 5700, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (77, 7, '2024-05-01', 170000000, 34000000, 28000000, 5900, 25, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (78, 7, '2024-06-01', 180000000, 36000000, 29000000, 6000, 26, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (79, 7, '2024-07-01', 190000000, 38000000, 30000000, 6200, 27, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (80, 7, '2024-08-01', 160000000, -20000000, -18000000, 5500, 24, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (81, 7, '2024-09-01', 200000000, 40000000, 32000000, 6500, 28, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (82, 7, '2024-10-01', 210000000, 42000000, 34000000, 6600, 29, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (83, 7, '2024-11-01', 190000000, -5000000, -8000000, 6100, 27, CURRENT_TIMESTAMP, 'ADMIN_INPUT'),
    (84, 7, '2024-12-01', 180000000, 35000000, 28000000, 6000, 26, CURRENT_TIMESTAMP, 'ADMIN_INPUT');

INSERT INTO startup_annualmetrics (annual_id,
                                   startup_id,
                                   year,
                                   annual_revenue,
                                   operating_profit,
                                   net_profit,
                                   total_asset,
                                   created_at,
                                   data_source,
                                   investment_round)
VALUES
    -- startup_id = 1
    (1, 1, 2021, 1200000000, 240000000, 180000000, 500000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),
    (2, 1, 2022, 1500000000, 300000000, 220000000, 600000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),
    (3, 1, 2023, 1800000000, 360000000, 270000000, 700000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Pre-A'),

    -- startup_id = 2
    (4, 2, 2021, 1300000000, 260000000, 190000000, 520000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', NULL),
    (5, 2, 2022, 1600000000, 320000000, 240000000, 620000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),
    (6, 2, 2023, 1900000000, 380000000, 280000000, 720000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),

    -- startup_id = 3
    (7, 3, 2021, 1400000000, 280000000, 210000000, 540000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),
    (8, 3, 2022, 1700000000, 340000000, 250000000, 640000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Pre-A'),
    (9, 3, 2023, 2000000000, 400000000, 300000000, 740000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Pre-A'),

    -- startup_id = 4
    (10, 4, 2021, 1250000000, 250000000, 185000000, 505000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),
    (11, 4, 2022, 1550000000, 310000000, 225000000, 605000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),
    (12, 4, 2023, 1850000000, 370000000, 275000000, 705000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Pre-A'),

    -- startup_id = 5
    (13, 5, 2021, 1350000000, 270000000, 200000000, 530000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Series A'),
    (14, 5, 2022, 1650000000, 330000000, 250000000, 630000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Series A'),
    (15, 5, 2023, 1950000000, 390000000, 290000000, 730000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Series B'),

    -- startup_id = 6
    (16, 6, 2021, 1450000000, 290000000, 220000000, 550000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),
    (17, 6, 2022, 1750000000, 350000000, 260000000, 650000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Series A'),
    (18, 6, 2023, 2050000000, 410000000, 310000000, 750000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Series B'),

    -- startup_id = 7
    (19, 7, 2021, 1450000000, 290000000, 220000000, 550000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Seed'),
    (20, 7, 2022, 1750000000, 350000000, 260000000, 650000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Series A'),
    (21, 7, 2023, 2050000000, 410000000, 310000000, 750000000, CURRENT_TIMESTAMP, 'ANNUAL_REPORT', 'Series B');

