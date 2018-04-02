package com.silive.pc.roundtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by PC on 3/23/2018.
 */

public class AlertDialogueBox {

    EditText etAlertDialogueChannelName, etAlertDialogueChannelDescription;
    private String inputChannelName, inputChannelDescription;
    private final ArrayList<String> channelList = new ArrayList<String>();

    private AlertDialogueBoxInterface dialogueBoxInterface;
    private Activity activity;

    public AlertDialogueBox(Activity activity){
        this.activity = activity;
        this.dialogueBoxInterface = (AlertDialogueBoxInterface) this.activity;
    }

    public boolean getAlertDialogueBox(){
        // inflate alert dialog xml
        LayoutInflater li = LayoutInflater.from(activity);
        View dialogView = li.inflate(R.layout.custom_dialogue_add_channel, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);
        // set title
        //alertDialogBuilder.setTitle("Add Channel");
        // set custom_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(dialogView);
        etAlertDialogueChannelName = (EditText) dialogView
                .findViewById(R.id.custom_dialogue_channel_name);
        etAlertDialogueChannelDescription = (EditText) dialogView
                .findViewById(R.id.custom_dialogue_channel_description);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add Channel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                inputChannelName = etAlertDialogueChannelName.getText().toString();
                                inputChannelDescription = etAlertDialogueChannelDescription.getText().toString();
                                channelList.add(inputChannelName);
                                channelList.add(inputChannelDescription);
                                dialogueBoxInterface.sendChannel(channelList);
                                //Toast.makeText(activity, channelList.toString(), Toast.LENGTH_SHORT).show();
                                dialog.cancel();
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
        cancelButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        Button addChannelButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        addChannelButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
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
