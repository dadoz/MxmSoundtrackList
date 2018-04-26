package com.application.mxm.soundtracks.data.exceptions;


import com.application.mxm.soundtracks.R;

public class NetworkException extends RuntimeException {


    private NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public static NetworkException noNetwork(Throwable cause) {
        return new NetworkException("No network exception", cause);
    }

    public int getErrorString() {
        return R.string.error_network_no_network;
    }

}