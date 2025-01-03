package com.codelab.com.HILO_ATM.Services;

import com.codelab.com.HILO_ATM.Model.AtmHistory;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface HistoryServices {

	public Boolean saveTransactionHistory(AtmHistory history);
	public ArrayList<AtmHistory> getHistory();
	public Boolean deleteHistory();


}
