package com.codelab.com.HILO_ATM.Services;

import com.codelab.com.HILO_ATM.Model.AtmHistory;
import com.codelab.com.HILO_ATM.Repository.HistoryRepository;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class HistoryServicesImpl implements HistoryServices {
	@Autowired
	private HistoryRepository historyRep;

	public HistoryServicesImpl(HistoryRepository hRepo) {
		this.historyRep = hRepo;
	}

	/**
	 * @return boolean
	 */
	@Override
	public Boolean saveTransactionHistory(AtmHistory history) {

		try {
			historyRep.save(history);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public ArrayList<AtmHistory> getHistory() {

		return new ArrayList<>(historyRep.findAll());
	}

	/**
	 * @return
	 */
	@Override
	public Boolean deleteHistory() {

		historyRep.deleteAll();

		return historyRep.findAll().isEmpty();
	}
}
