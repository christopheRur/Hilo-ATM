package com.codelab.com.HILO_ATM.Repository;

import com.codelab.com.HILO_ATM.Model.AtmHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<AtmHistory,Long> {
}
