package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubstituteReqRepository extends JpaRepository<SubstituteReq, Long>, SubstituteReqRepositoryCustom {
}
