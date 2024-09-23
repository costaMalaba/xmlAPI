package com.cymstarlink.xmlAPI.Repositories;

import com.cymstarlink.xmlAPI.entities.ResponseCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;

@Repository
public interface ResponseCodeRepository extends JpaRepository<ResponseCodeEntity, Integer> {
    @Query("SELECT c FROM ResponseCodeEntity c WHERE c.code=?1")
    ResponseCodeEntity findByCode(String code);

    @Query("SELECT COUNT(u) FROM ResponseCodeEntity u")
    int countAll();

    @Procedure(name = "ResponseCode.codeResponse")
    String getUserDetails(@Param("count") Integer count);
}
