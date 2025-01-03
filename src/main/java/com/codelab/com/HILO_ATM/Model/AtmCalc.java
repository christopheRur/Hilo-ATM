package com.codelab.com.HILO_ATM.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AtmCalc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private double totalSavingAcc;
    private double totalSpendAcc;
    private double depositedAmount;
    private double withdrawnAmount;
    private double transferredAmount;
    private boolean savingAccount;
    private boolean spendingAccount;



}
