package com.picktartup.startup.repository;

import com.picktartup.startup.entity.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartupServiceRepository extends JpaRepository<Startup, Long> {

    // 진행도가 높은 순으로 상위 6개 스타트업 조회
    List<Startup> findTop6ByOrderByProgressDesc();
}
