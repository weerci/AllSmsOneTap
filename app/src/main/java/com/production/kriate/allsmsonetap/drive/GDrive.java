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

    private static GDrive _GDrive;
    private Activity _Activity;

    private GDrive(Activity activity) {
        _Activity = activity;
    }
    public static GDrive Item(Activity activity){
        if (_GDrive == null) {
            _GDrive = new GDrive(activity);
        }
        return  _GDrive;
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
    public int Send(){
        try {

            return FILE_SENDED;
        }
        catch (Exception e)
        {
            return FILE_NOT_SENDED;
        }
    }

    // Получает файл базы данных из google drive
    public int load(){
        try {

            return FILE_LOADED;
        }
        catch (Exception e)
        {
            return FILE_NOT_LADED;
        }
    }

    // Пользователь авторизуется
    public void auth(){
        _GoogleSignInClient = buildGoogleSignInClient();
        _Activity.startActivityForResult(_GoogleSignInClient.getSignInIntent(), MainActivity.REQUEST_CODE_SIGN_IN);
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
    public void createDriverClient()
    {
        _DriveClient = Drive.getDriveClient(_Activity, GoogleSignIn.getLastSignedInAccount(_Activity));
        _DriveResourceClient =
                Drive.getDriveResourceClient(_Activity, GoogleSignIn.getLastSignedInAccount(_Activity));
    }


    //endregion

    public final int FILE_NOT_SENDED = 0;
    public final int FILE_SENDED = 1;
    public final int FILE_NOT_LADED = 2;
    public final int FILE_LOADED = 3;
}
