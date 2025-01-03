package com.codelab.com.HILO_ATM.Repository;

import com.codelab.com.HILO_ATM.Model.AtmCalc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtmCalcRepository  extends JpaRepository<AtmCalc,Long> {

    AtmCalc findTotalSpendAccById(Long id);

}
