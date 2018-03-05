package com.production.kriate.allsmsonetap.drive;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveResourceClient;

/**
 * Created by dima on 05.03.2018.
 */

public class GDrive extends Activity implements IGDrive {
    private static final int REQUEST_CODE_SIGN_IN = 0;

    private GoogleSignInClient _Client = getGoogleSignInClient();
    private GoogleSignInAccount _Account = GoogleSignIn.getLastSignedInAccount(this);
    private DriveClient _Drive;
    private DriveResourceClient _Resource;

    @Override
    public boolean auth() {
        try {
            if (_Account != null)
                buildDrive();
            else
                startActivityForResult(_Client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                buildDrive();
            }
        }
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean load() {
        return false;
    }

    @Override
    public boolean reSing() {
        _Client.signOut();
        return auth();
    }

    //region Helper

    private GoogleSignInClient getGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    private void buildDrive() {
        _Drive = Drive.getDriveClient(this, _Account);
        _Resource = Drive.getDriveResourceClient(this, _Account);
    }

    //endregion

}
