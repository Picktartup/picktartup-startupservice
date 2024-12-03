package com.picktartup.startup.repository.jpa;

import com.picktartup.startup.entity.Startup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartupServiceRepository extends JpaRepository<Startup, Long> {

    @EntityGraph(attributePaths = {"startupDetails", "ssi"})
    @Query("SELECT DISTINCT s FROM Startup s " +
            "LEFT JOIN FETCH s.startupDetails " +
            "LEFT JOIN FETCH s.ssi " +
            "ORDER BY s.fundingProgress DESC")
    List<Startup> findTop6ByOrderByFundingProgressDesc();


}
