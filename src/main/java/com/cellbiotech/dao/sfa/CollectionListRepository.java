package com.cellbiotech.dao.sfa;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cellbiotech.model.sfa.CollectionList;

/**
 * 수금리스트 관련 JPA
 */
@Repository
public interface CollectionListRepository extends JpaRepository<CollectionList, String> {

    Page<CollectionList> findAllByCollectionNumContainingAndCollectionPlaceNameContainingAndCollectionPlaceCodeContaining(String id, String name, String code, Pageable pageable);
    Page<CollectionList> findAllByCollectionNumContainingAndCollectionPlaceNameContainingAndCollectionPlaceCodeContainingAndCollectionDateBetween(String id, String name, String code, Date fromDate, Date toDate, Pageable pageable);

    List<CollectionList> findAllByCollectionNumContainingAndCollectionPlaceNameContainingAndCollectionPlaceCodeContaining(String id, String name, String code);
    List<CollectionList> findAllByCollectionNumContainingAndCollectionPlaceNameContainingAndCollectionPlaceCodeContainingAndCollectionDateBetween(String id, String name, String code, Date fromDate, Date toDate);

}
