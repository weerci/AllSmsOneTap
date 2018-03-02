package com.production.kriate.allsmsonetap.drive;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveResourceClient;
import com.production.kriate.allsmsonetap.App;
import com.production.kriate.allsmsonetap.MainActivity;


/**
 * Created by dima on 22.02.2018.
 */
public class GDrive {

    //region Constructor

    private GDrive() {}
    public static GDrive Item() {
        if (App._GDrive == null) {
            App._GDrive = new GDrive();
        }
        return App._GDrive;
    }

    //endregion

    //region Properties

    public boolean _IsAuih = false;
    private GoogleSignInClient _GoogleSignInClient;
    private DriveClient _DriveClient;
    private DriveResourceClient _DriveResourceClient;

    //endregion

    //region Methods

    // Сохраняет файл базы данных на google drive
    public int Send() {
        try {
            /*final Bitmap image = mBitmapToSave;
            mDriveResourceClient
                    .createContents()
                    .continueWithTask(
                            new Continuation<DriveContents, Task<Void>>() {
                                @Override
                                public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                                    return createFileIntentSender(task.getResult(), image);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Failed to create new contents.", e);
                                }
                            });*/

            return FILE_SENDED;
        } catch (Exception e) {
            return FILE_NOT_SENDED;
        }
    }

    // Получает файл базы данных из google drive
    public int load() {
        try {

            return FILE_LOADED;
        } catch (Exception e) {
            return FILE_NOT_LADED;
        }
    }

    // Пользователь авторизуется
    public void auth(Activity activity) {
        _GoogleSignInClient = buildGoogleSignInClient();
        activity.startActivityForResult(_GoogleSignInClient.getSignInIntent(), MainActivity.REQUEST_CODE_SIGN_IN);
    }

    //endregion

    //region Helper

    // Создается Google sign-in client.
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(App.getContext(), signInOptions);
    }

    // Создается Drive clients после успешного соединения
    public void createDriverClient(Activity activity) {
        _DriveClient = Drive.getDriveClient(activity, GoogleSignIn.getLastSignedInAccount(activity));
        _DriveResourceClient =
                Drive.getDriveResourceClient(activity, GoogleSignIn.getLastSignedInAccount(activity));
    }


    //endregion

    public final int FILE_NOT_SENDED = 0;
    public final int FILE_SENDED = 1;
    public final int FILE_NOT_LADED = 2;
    public final int FILE_LOADED = 3;
}
