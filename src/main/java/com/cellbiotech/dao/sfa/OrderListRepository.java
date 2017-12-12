package com.cellbiotech.dao.sfa;

import java.util.Date;
import java.util.List;

import com.cellbiotech.model.sfa.OrderList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 수주리스트 관련 JPA
 */
@Repository
public interface OrderListRepository extends JpaRepository<OrderList, String> {

    Page<OrderList> findAllByIdOrderNumContainingAndAccountNameContainingAndAccountCodeContaining(String id, String name, String code, Pageable pageable);
    Page<OrderList> findAllByIdOrderNumContainingAndAccountNameContainingAndAccountCodeContainingAndOrderDateBetween(String id, String name, String code, Date fromDate, Date toDate, Pageable pageable);

    List<OrderList> findAllByIdOrderNumContainingAndAccountNameContainingAndAccountCodeContaining(String id, String name, String code);
    List<OrderList> findAllByIdOrderNumContainingAndAccountNameContainingAndAccountCodeContainingAndOrderDateBetween(String id, String name, String code, Date fromDate, Date toDate);
}
