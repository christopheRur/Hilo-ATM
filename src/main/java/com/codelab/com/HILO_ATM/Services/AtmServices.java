package com.codelab.com.HILO_ATM.Services;

import com.codelab.com.HILO_ATM.Model.AtmCalc;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public interface AtmServices {
	public JsonObject depositFunds(AtmCalc bodyFund);

	public ArrayList<AtmCalc> getDepositedFunds();

	public AtmCalc withdrawnFunds(AtmCalc bodyFund);

	public JsonObject checkBalances(AtmCalc bodyFund);

	public double addToSaving(AtmCalc bodyFund);

	public double addToSpending(AtmCalc bodyFund);



}
