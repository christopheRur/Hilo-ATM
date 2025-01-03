package com.codelab.com.HILO_ATM.Services;

import com.codelab.com.HILO_ATM.Model.AtmUser;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Service
public interface AtmUserServices {

    public Boolean accessPortal(AtmUser credentials);
    public Boolean exitPortal(AtmUser credentials);
    public JsonObject registerUser(AtmUser credentials);

}
