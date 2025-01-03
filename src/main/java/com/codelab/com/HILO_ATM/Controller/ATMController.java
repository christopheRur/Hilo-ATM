package com.codelab.com.HILO_ATM.Controller;


import com.codelab.com.HILO_ATM.Messages.SuccessErrorMsg;
import com.codelab.com.HILO_ATM.Model.AtmCalc;
import com.codelab.com.HILO_ATM.Model.AtmHistory;
import com.codelab.com.HILO_ATM.Model.AtmUser;
import com.codelab.com.HILO_ATM.Services.AtmServicesImpl;
import com.codelab.com.HILO_ATM.Services.AtmUserServicesImpl;
import com.codelab.com.HILO_ATM.Services.HistoryServicesImpl;
import com.codelab.com.HILO_ATM.Utils.Util;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
public class ATMController {

	private AtmServicesImpl atmServices;
	private AtmUserServicesImpl atmUser;
	private HistoryServicesImpl atmHistory;

	public ATMController(AtmServicesImpl atmServ, AtmUserServicesImpl atmServUser, HistoryServicesImpl hist) {
		this.atmServices = atmServ;
		this.atmUser = atmServUser;
		this.atmHistory = hist;

	}

	@PostMapping("/access_portal")
	public ResponseEntity<?> accessPortal(@RequestBody AtmUser bodyRequest) {

		try {
			if (bodyRequest.toString().isEmpty()) {

				return ResponseEntity.badRequest().body("No data Found!");

			} else {
				;
				return new ResponseEntity<>(atmUser.accessPortal(bodyRequest), HttpStatus.OK);
			}

		} catch (Exception e) {

			log.error("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body("Error occurred, unable to login user..");
		}
	}
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody AtmUser bodyRequest) {

		try {
			if (bodyRequest.toString().isEmpty()) {

				return ResponseEntity.badRequest().body("No data Found!");

			} else {
				return new ResponseEntity<>(atmUser.registerUser(bodyRequest), HttpStatus.OK);
			}

		} catch (Exception e) {

			log.error("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body("Error occurred, unable to login user..");
		}
	}

	@PostMapping("/exit_portal")
	public ResponseEntity<?> exitPortal(@RequestBody AtmUser bodyRequest) {

		try {
			if (bodyRequest.toString().isEmpty()) {

				return ResponseEntity.badRequest().body("No data Found!");

			} else {

				return new ResponseEntity<>(atmUser.exitPortal(bodyRequest), HttpStatus.OK);
			}

		} catch (Exception e) {

			log.error("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body("Error occurred, unable to login user..");
		}
	}

	@PostMapping("/withdraws")
	public ResponseEntity<?> withdrawsComputations(@RequestBody AtmCalc bodyRequest) {

		try {
			if (bodyRequest.toString().isEmpty()) {

				return ResponseEntity.badRequest().body("No data Found!");

			} else {

				return new ResponseEntity<>(atmServices.withdrawnFunds(bodyRequest), HttpStatus.OK);
			}

		} catch (Exception e) {

			log.error("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body("Error occurred, unable to login user..");
		}
	}


	@PostMapping("/deposits")
	public ResponseEntity<?> depositMoney(@RequestBody AtmCalc bodyRequest) {

		try {
			if (bodyRequest.toString().isEmpty()) {

				return ResponseEntity.badRequest().body("No data Found!");

			} else {

				log.info("Got Through: {}", "SUCCESS");

				return new ResponseEntity<>(atmServices.depositFunds(Util.buildAtmCalResponse(bodyRequest)), HttpStatus.OK);
			}

		} catch (Exception e) {

			log.error("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body(SuccessErrorMsg.errorMessage(e.getLocalizedMessage()));
		}
	}

	@PostMapping("/balances")
	public ResponseEntity<?> checkBalances(@RequestBody AtmCalc bodyRequest) {

		try {
			if (bodyRequest.toString().isEmpty()) {

				return ResponseEntity.badRequest().body("No data Found!");

			} else {


				return new ResponseEntity<>(atmServices.checkBalances(bodyRequest), HttpStatus.OK);
			}

		} catch (Exception e) {

			log.error("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body(SuccessErrorMsg.errorMessage("Error occurred, unable to login user.."));
		}
	}


	@GetMapping("/getDeposits")
	public ResponseEntity<?> getBalances(@RequestBody AtmCalc bodyRequest) {
		try {

			return new ResponseEntity<>(atmServices.getDepositedFunds(), HttpStatus.OK);

		} catch (Exception e) {

			log.info("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body("Error occurred, unable to FETCH data.");
		}
	}

	@GetMapping("/history")
	public ResponseEntity<?> getHistory() {
		try {

			return new ResponseEntity<>(atmHistory.getHistory(), HttpStatus.OK);

		} catch (Exception e) {

			log.info("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body("Error occurred, unable to FETCH data.");
		}
	}

	@DeleteMapping("/delete_hist")
	public ResponseEntity<?> eraseHistory() {
		try {

			return new ResponseEntity<>(atmHistory.deleteHistory(), HttpStatus.OK);

		} catch (Exception e) {

			log.info("==>{}", e.getLocalizedMessage());

			return ResponseEntity.badRequest().body("Error occurred, unable to FETCH data.");
		}
	}
}
