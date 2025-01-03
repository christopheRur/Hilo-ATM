package com.codelab.com.HILO_ATM.Utils;

import com.codelab.com.HILO_ATM.Model.AtmCalc;
import com.google.gson.JsonObject;

import java.time.LocalTime;

public class Util {
    public static AtmCalc buildAtmCalResponse(AtmCalc bodyResp){

        AtmCalc atmCalc = new AtmCalc();
        atmCalc.setDepositedAmount(bodyResp.getDepositedAmount());
        atmCalc.setId(bodyResp.getId());
        atmCalc.setWithdrawnAmount(bodyResp.getWithdrawnAmount());
        atmCalc.setTotalSavingAcc(bodyResp.getTotalSavingAcc());
        atmCalc.setTotalSpendAcc(bodyResp.getTotalSpendAcc());
        atmCalc.setTransferredAmount(bodyResp.getTransferredAmount());
        atmCalc.setSpendingAccount(bodyResp.isSpendingAccount());
        atmCalc.setSavingAccount(bodyResp.isSavingAccount());

        return atmCalc;

    }

    public static String punchTime(){
            LocalTime now = LocalTime.now();
        return now.toString();
    }
}
