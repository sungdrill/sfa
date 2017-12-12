package com.cellbiotech.dao.sfa;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.DeliveryRequestList;

/**
 * 납품의뢰 관련 JPA
 */
@Repository
public interface DeliveryRequestListRepository extends JpaRepository<DeliveryRequestList, String> {

    Page<DeliveryRequestList> findAllByIdRequestNumContainingAndAccountNameContainingAndAccountCodeContaining(String id, String name, String code, Pageable pageable);
    Page<DeliveryRequestList> findAllByIdRequestNumContainingAndAccountNameContainingAndAccountCodeContainingAndRequestDateBetween(String id, String name, String code, Date fromDate, Date toDate, Pageable pageable);

    List<DeliveryRequestList> findAllByIdRequestNumContainingAndAccountNameContainingAndAccountCodeContaining(String id, String name, String code);
    List<DeliveryRequestList> findAllByIdRequestNumContainingAndAccountNameContainingAndAccountCodeContainingAndRequestDateBetween(String id, String name, String code, Date fromDate, Date toDate);
}
