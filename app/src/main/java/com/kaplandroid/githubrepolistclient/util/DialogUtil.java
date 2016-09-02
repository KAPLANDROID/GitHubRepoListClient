package com.kaplandroid.githubrepolistclient.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.kaplandroid.githubrepolistclient.R;
import com.kaplandroid.githubrepolistclient.ui.MainActivity;

/**
 * Created by KAPLANDROID on 2.09.2016.
 */
public class DialogUtil {

    /**
     * To show Toast Message on screen
     *
     * @param ctx
     * @param message
     */
    public static void showToastMessage(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    /**
     * To show dialog message on screen
     *
     * @param ctx
     * @param message
     */
    public static void showDialogMessage(Context ctx, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }

        });
        alertDialogBuilder.create().show();
    }


}
