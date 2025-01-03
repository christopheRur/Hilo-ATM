package com.codelab.com.HILO_ATM.Messages;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import static com.codelab.com.HILO_ATM.Constant.Constants.BAD_REQUEST;
import static com.codelab.com.HILO_ATM.Constant.Constants.OK;

public class SuccessErrorMsg {

    public static JsonObject successMessage(String message) {

        JsonObject msg = new JsonObject();
        msg.addProperty("Status",OK );
        msg.addProperty("message", message);

        return msg;
    }

    public static JsonObject errorMessage(String message) {

        JsonObject msg = new JsonObject();
        msg.addProperty("Status",BAD_REQUEST );
        msg.addProperty("message", message);

        return msg;
    }
}
