package com.kjc.workplus.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjc.workplus.login.domain.MemberAuthority;

@Repository
public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long>{

}
