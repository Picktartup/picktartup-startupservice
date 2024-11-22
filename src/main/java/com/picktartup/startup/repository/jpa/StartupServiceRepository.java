package com.picktartup.startup.repository.jpa;

import com.picktartup.startup.entity.Startup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StartupServiceRepository extends JpaRepository<Startup, Long> {

    @EntityGraph(attributePaths = {"wallet", "startupDetails", "ssi"})
    @Query("SELECT s FROM Startup s ORDER BY s.fundingProgress DESC")
    List<Startup> findTop6ByOrderByFundingProgressDesc();

}
