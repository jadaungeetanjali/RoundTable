package com.silive.pc.roundtable;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import com.silive.pc.roundtable.config.APIUrl;

import java.net.URISyntaxException;

/**
 * Created by PC on 4/2/2018.
 */

public class RoundTableApplication extends Application {

    public static Socket mSocket;
    {
        try {
            mSocket = IO.socket(APIUrl.BASE_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
