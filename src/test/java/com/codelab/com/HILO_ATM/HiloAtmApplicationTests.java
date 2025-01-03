package com.codelab.com.HILO_ATM;

import com.codelab.com.HILO_ATM.Controller.ATMController;
import com.codelab.com.HILO_ATM.Messages.SuccessErrorMsg;
import com.codelab.com.HILO_ATM.Model.AtmCalc;
import com.codelab.com.HILO_ATM.Model.AtmHistory;
import com.codelab.com.HILO_ATM.Model.AtmUser;
import com.codelab.com.HILO_ATM.Services.AtmServicesImpl;
import com.codelab.com.HILO_ATM.Services.AtmUserServicesImpl;
import com.codelab.com.HILO_ATM.Services.HistoryServicesImpl;
import com.codelab.com.HILO_ATM.Utils.Util;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.codelab.com.HILO_ATM.Constant.Constants.DEPOSIT;
import static com.codelab.com.HILO_ATM.Constant.Constants.WITHDRAW;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class HiloAtmApplicationTests {

	@Mock
	private AtmCalc atmCalc;
	@Mock
	private AtmHistory atmHistory;
	@Mock
	private AtmUser atmUser;

	@InjectMocks
	private ATMController controller;

	@Mock
	private AtmServicesImpl atmServices;
	@Mock
	private AtmUserServicesImpl atmUserServices;
	@Mock
	private HistoryServicesImpl historyServices;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks
		atmCalc = new AtmCalc();
		atmCalc.setTotalSavingAcc(2200);
		atmCalc.setSpendingAccount(false);
		atmCalc.setWithdrawnAmount(1000);
		atmCalc.setDepositedAmount(777);

		atmUser= new AtmUser();
		atmUser.setTimestamp(Util.punchTime());
		atmUser.setPortalPin(1234);
		atmUser.setEmail("winds@yahoo.com");
		atmUser.setLastName("tester1");
		atmUser.setFirstName("tester2");

		atmHistory= new AtmHistory();
		atmHistory.setAmount(1000);
		atmHistory.setAction(WITHDRAW);
		atmHistory.setId(1L);



	}

	@Test
	public void testDeposit() {
		JsonObject expectedMessage =SuccessErrorMsg.successMessage(
				String.format("%s %.2f", DEPOSIT, atmCalc.getDepositedAmount()));

		Mockito.when(atmServices.depositFunds(atmCalc))
				.thenReturn(expectedMessage);

		ResponseEntity<?> response = controller.depositMoney(atmCalc);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedMessage, response.getBody());
		verify(atmServices, times(1)).depositFunds(atmCalc);

	}
	@Test
	public void testAccessPortal(){
		Mockito.when(atmUserServices.accessPortal(atmUser)).thenReturn(true);
		ResponseEntity<?> response = controller.accessPortal(atmUser);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody());
}

	@Test
	public void testAtmHistory(){
		Mockito.when(atmServices.withdrawnFunds(atmCalc)).thenReturn(atmCalc);
		Mockito.when(historyServices.saveTransactionHistory(atmHistory));
		ResponseEntity<?> response = controller.withdrawsComputations(atmCalc);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	void contextLoads() {
	}

}
