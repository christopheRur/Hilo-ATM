package com.codelab.com.HILO_ATM.Services;

import com.codelab.com.HILO_ATM.Model.AtmUser;
import com.codelab.com.HILO_ATM.Repository.AtmUserRepository;
import com.codelab.com.HILO_ATM.Utils.Util;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AtmUserServicesImpl implements AtmUserServices {
	@Autowired
	private AtmUserRepository userRepo;


	public AtmUserServicesImpl(AtmUserRepository userRep) {
		this.userRepo = userRep;
	}

	@Override
	public Boolean accessPortal(AtmUser credentials) {

		boolean access = false;

		try {
			log.info("ACCESS PORTAL!{}", credentials.getLastName());
			credentials.setTimestamp(Util.punchTime());
			for (int i = 0; i < userRepo.findAll().size(); i++) {

				if (userRepo.findAtmUserById(credentials.getId()).getPortalPin() == credentials.getPortalPin()) {
					access = true;
					break;
				}

			}

			return access;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


	}

	@Override
	public Boolean exitPortal(AtmUser credentials) {
		boolean exit = false;
		try {
			credentials.setTimestamp(Util.punchTime());
			for (int i = 0; i < userRepo.findAll().size(); i++) {

				if (userRepo.findAtmUserById(credentials.getId()).getPortalPin() == credentials.getPortalPin()) {
					exit = true;
					break;
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return exit;
	}

	/**
	 * @param credentials
	 * @return
	 */
	@Override
	public JsonObject registerUser(AtmUser credentials) {
		JsonObject res = new JsonObject();
		res.addProperty("FirstName", credentials.getFirstName());
		res.addProperty("LastName", credentials.getLastName());
		res.addProperty("Pin", credentials.getPortalPin());
		res.addProperty("email", credentials.getEmail());
		res.addProperty("id", credentials.getId());
		try {
			userRepo.save(credentials);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return res;
	}
}
