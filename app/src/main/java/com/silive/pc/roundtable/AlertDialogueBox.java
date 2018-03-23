package com.silive.pc.roundtable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by PC on 3/23/2018.
 */

public class AlertDialogueBox {
    public boolean getAlertDialogueBox(Context context){
        // inflate alert dialog xml
        LayoutInflater li = LayoutInflater.from(context);
        View dialogView = li.inflate(R.layout.custom_dialogue_add_channel, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // set title
        //alertDialogBuilder.setTitle("Add Channel");
        // set custom_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(dialogView);
        final EditText alertDialogueChannelName = (EditText) dialogView
                .findViewById(R.id.custom_dialogue_channel_name);
        final EditText alertDialogueChannelDescription = (EditText) dialogView
                .findViewById(R.id.custom_dialogue_channel_description);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add Channel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                //perform something
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
        Button cancelButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        cancelButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        Button addChannelButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        addChannelButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20,16,60,8);
        cancelButton.setLayoutParams(params);
        params.setMargins(60,16,20,8);
        addChannelButton.setLayoutParams(params);
        return true;
    }
}
