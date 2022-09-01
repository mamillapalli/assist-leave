package com.csme.assist.leave.repository;

import com.csme.assist.leave.entity.Leave;
import com.csme.assist.leave.entity.StatusEnum;
import com.csme.assist.leave.entity.TransactionStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    List<Leave> findByResourceId(String id);

    List<Leave> findByResourceIdOrTransactionStatus(String id,TransactionStatusEnum transactionStatusEnum);

    List<Leave> findByResourceIdAndTransactionStatus(String id,TransactionStatusEnum transactionStatus);

    List<Leave> findByTransactionStatus(TransactionStatusEnum transactionStatus);

    Optional<List<Leave>> findByApproverId(String id);

    Optional<List<Leave>> findByApproverIdAndStatus(String id, StatusEnum status);

    List<Leave> findByTransactionStatusAndResourceIdNot(TransactionStatusEnum status,String id);
}


