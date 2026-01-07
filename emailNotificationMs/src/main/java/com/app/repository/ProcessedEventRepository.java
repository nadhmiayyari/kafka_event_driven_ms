package com.app.repository;

import com.app.model.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity,Long> {

    ProcessedEventEntity findProcessedEventEntityByMessageId(String messageId);
}
