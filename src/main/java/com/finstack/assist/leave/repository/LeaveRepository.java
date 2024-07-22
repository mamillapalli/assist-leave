package com.finstack.assist.leave.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finstack.assist.leave.entity.Leave;
import com.finstack.assist.leave.entity.StatusEnum;
import com.finstack.assist.leave.entity.TransactionStatusEnum;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    List<Leave> findByResourceId(String id);

    List<Leave> findByResourceIdOrTransactionStatus(String id,TransactionStatusEnum transactionStatusEnum);

    List<Leave> findByResourceIdAndTransactionStatus(String id,TransactionStatusEnum transactionStatus);
    List<Leave> findByResourceIdAndStatus(String id,StatusEnum status);


    List<Leave> findByTransactionStatus(TransactionStatusEnum transactionStatus);

    Optional<List<Leave>> findByApproverId(String id);

    Optional<List<Leave>> findByApproverIdAndStatus(String id, StatusEnum status);

    List<Leave> findByTransactionStatusAndResourceIdNot(TransactionStatusEnum status,String id);

    @Query("select l from Leave l where l.resourceId = :resourceId or (l.approverId = :approverId and l.status = :status)")
    List<Leave> findByResourceIdAndApproverIdOrStatus(@Param("resourceId") String resourceId,
                                                      @Param("approverId")String approverId,
                                                      @Param("status")StatusEnum status);
    
    @Query("SELECT l FROM Leave l WHERE " +
            "(:status IS NULL OR l.status = :status) AND " +
            "(:resourceId IS NULL OR l.resourceId = :resourceId) AND " +
            "(:approverId IS NULL OR l.approverId = :approverId) AND " +
            "(cast(:startDate as date) IS NULL OR l.startDate >= :startDate) AND "+
            "(cast(:endDate as date) IS NULL OR l.endDate <= :endDate)")
     List<Leave> getAllLeaves(@Param("status") StatusEnum status,
                              @Param("resourceId") String resourceId,
                              @Param("approverId") String approverId,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);
    
    @Query("SELECT l FROM Leave l WHERE " +
            "(:status IS NULL OR l.status = :status) AND " +
            "((:approverId IS NULL OR l.approverId = :approverId) OR " +
            "(:resourceId IS NULL OR l.resourceId = :resourceId)) AND " +
            "(cast(:startDate as date) IS NULL OR l.startDate >= :startDate) AND " +
            "(cast(:endDate as date) IS NULL OR l.endDate <= :endDate)")
     List<Leave> getAllLeavesOfApprover(@Param("status") StatusEnum status,
                              @Param("resourceId") String resourceId,
                              @Param("approverId") String approverId,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);
    
    
    
    @Query("SELECT l FROM Leave l WHERE " +
            "(:status IS NULL OR l.status = :status) AND " +
            "(:resourceId IS NULL OR l.resourceId = :resourceId) AND " +
            "(:approverId IS NULL OR l.approverId = :approverId) AND " +
            "(cast(:startDate as date) IS NULL OR l.startDate >= :startDate) AND "+
            "(cast(:endDate as date) IS NULL OR l.endDate <= :endDate)")
     Page<Leave> getAllLeavesByPaging(@Param("status") StatusEnum status,
                              @Param("resourceId") String resourceId,
                              @Param("approverId") String approverId,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate, Pageable pageable );
    
    @Query("SELECT l FROM Leave l WHERE " +
            "(:status IS NULL OR l.status = :status) AND " +
            "((:approverId IS NULL OR l.approverId = :approverId) OR " +
            "(:resourceId IS NULL OR l.resourceId = :resourceId)) AND " +
            "(cast(:startDate as date) IS NULL OR l.startDate >= :startDate) AND " +
            "(cast(:endDate as date) IS NULL OR l.endDate <= :endDate)")
     Page<Leave> getAllLeavesOfApproverByPaging(@Param("status") StatusEnum status,
                              @Param("resourceId") String resourceId,
                              @Param("approverId") String approverId,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate, Pageable pageable);
}


