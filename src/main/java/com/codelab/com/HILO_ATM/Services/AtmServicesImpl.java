package com.codelab.com.HILO_ATM.Services;

import com.codelab.com.HILO_ATM.Messages.SuccessErrorMsg;
import com.codelab.com.HILO_ATM.Model.AtmCalc;
import com.codelab.com.HILO_ATM.Model.AtmHistory;
import com.codelab.com.HILO_ATM.Repository.AtmCalcRepository;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.codelab.com.HILO_ATM.Constant.Constants.*;

@Slf4j
@Service
public class AtmServicesImpl implements AtmServices {
	@Autowired
	private AtmCalcRepository atmCalcRepository;
	private HistoryServicesImpl historyServices;
	private AtmHistory history;


	public AtmServicesImpl(AtmCalcRepository atmCalcRepo, HistoryServicesImpl histServ) {
		this.atmCalcRepository = atmCalcRepo;
		this.historyServices = histServ;
		history = new AtmHistory();


	}

	public double getTotalDepositedSum() {
		double sumDeposit = 0;
		for (AtmCalc accountSum : atmCalcRepository.findAll()) {

			sumDeposit += accountSum.getDepositedAmount();

		}
		log.info(" TotalSum from db => {}", sumDeposit);
		return sumDeposit;
	}

	public double getTotalSavingSum() {
		double sumDeposit = 0;
		for (AtmCalc accountSum : atmCalcRepository.findAll()) {

			sumDeposit += accountSum.getTotalSavingAcc();

		}

		return sumDeposit;
	}

	public void updateHistory(AtmHistory history, double amount, String message) {
		history.setAction(message);
		history.setAmount(amount);
		historyServices.saveTransactionHistory(history);
	}

	public double getTotalSavingAccount() {
		double savingAmount = 0;
		for (AtmCalc accountSum : atmCalcRepository.findAll()) {

			savingAmount += accountSum.getTotalSavingAcc();

		}
		log.info(" SavingSum from db => {}", savingAmount);
		return savingAmount;
	}


	/**
	 * deposits funds spending accounts
	 *
	 * @param bodyFund
	 * @return JsonObject
	 */
	@Override
	public JsonObject depositFunds(AtmCalc bodyFund) {
		log.info("Deposit --->{}  -0->  {}", bodyFund.getDepositedAmount(), bodyFund.getId());
		if (bodyFund.isSpendingAccount()) {
			addToSpending(bodyFund);
			updateHistory(history, bodyFund.getDepositedAmount(), String.format(DEPOSIT, "into Spending Account"));

		} else if (bodyFund.isSavingAccount()) {
			addToSaving(bodyFund);
			updateHistory(history, bodyFund.getDepositedAmount(), String.format(DEPOSIT, "into Spending Saving Account"));
		}

		return SuccessErrorMsg.successMessage(String.format("%s %.2f", DEPOSIT, bodyFund.getDepositedAmount()));

	}

	/**
	 * Retrieve deposited amount
	 *
	 * @return ArrayList<AtmCalc>
	 */
	@Override
	public ArrayList<AtmCalc> getDepositedFunds() {
		updateHistory(history, getTotalSavingAccount(), String.format(DEPOSIT, "list of deposits"));
		return new ArrayList<>(atmCalcRepository.findAll());
	}

	private double getSpendingBalance() {
		double spendingAmount = getDepositedFunds().get(getDepositedFunds().size() - 1).getDepositedAmount();
		updateHistory(history, spendingAmount, String.format("Balance %s", "list of deposits"));
		return spendingAmount;
	}

	/**
	 * WIthdraws from spending account
	 *
	 * @param bodyFund
	 * @return JsonObject
	 */
	@Override
	public AtmCalc withdrawnFunds(AtmCalc bodyFund) {

		double withdrawnAmount = bodyFund.getWithdrawnAmount();

		double newBalance = getSpendingBalance() - withdrawnAmount;

		if (atmCalcRepository.findTotalSpendAccById(bodyFund.getId()) != null) {
			atmCalcRepository.delete(atmCalcRepository.findTotalSpendAccById(bodyFund.getId()));
			bodyFund.setTotalSpendAcc(newBalance);
			atmCalcRepository.save(bodyFund);
			updateHistory(history, bodyFund.getWithdrawnAmount(), String.format(WITHDRAW, "Withdrawn amount."));

		}


		return bodyFund;
	}

	/**
	 * Checks balance in either savings or spending account.
	 *
	 * @param bodyFund
	 * @return
	 */
	@Override
	public JsonObject checkBalances(AtmCalc bodyFund) {
		JsonObject resp = new JsonObject();
		if (bodyFund.isSavingAccount()) {
			double currentSaving = getDepositedFunds().get(getDepositedFunds().size() - 1).getTotalSavingAcc();
			resp.addProperty("SavingBalance", currentSaving);
			updateHistory(history, currentSaving, String.format("Saving %s", "Saving Balance"));

		} else if (bodyFund.isSpendingAccount()) {
			double currentSpending = getDepositedFunds().get(getDepositedFunds().size() - 1).getTotalSpendAcc();
			resp.addProperty("SpendingBalance", currentSpending);
			updateHistory(history, currentSpending, String.format("Spending %s", "Spending Balance"));
		}

		return resp;
	}


	/**
	 * @param bodyFund
	 * @return
	 */
	@Override
	public double addToSaving(AtmCalc bodyFund) {
		double currentSaving = bodyFund.getDepositedAmount() + getTotalSavingSum();
		bodyFund.setTotalSavingAcc(currentSaving);
		atmCalcRepository.save(bodyFund);

		return currentSaving;

	}

	/**
	 * Will add the amount to spending account.
	 *
	 * @param bodyFund
	 * @return Double
	 */
	@Override
	public double addToSpending(AtmCalc bodyFund) {
		double currentBalance = bodyFund.getDepositedAmount() + getTotalDepositedSum();
		bodyFund.setTotalSpendAcc(currentBalance);
		atmCalcRepository.save(bodyFund);
		log.info("Updated funds ---> {}", bodyFund);
		return currentBalance;
	}


}
