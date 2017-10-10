package com.example.giuseppebotta.ana;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by giuseppebotta on 03/05/17.
 */

public class StampaToast {

    public static void stampa(Context ctx, String msg){

        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}
