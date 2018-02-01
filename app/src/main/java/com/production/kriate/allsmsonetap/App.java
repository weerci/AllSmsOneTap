package com.production.kriate.allsmsonetap;

import android.app.Application;
import android.app.backup.BackupManager;

import com.production.kriate.allsmsonetap.tools.Encryption;

import org.solovyev.android.checkout.Billing;

/**
 * Created by dima on 31.07.2017.
 */

public class App extends Application {
    public App() {
        instance = this;
    }

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        billing.connect();
        SmsBackupAgent ba = new SmsBackupAgent();
    }

    // Context
    public static App getContext() {
        if (instance == null)
            return instance = new App();
        else
            return instance;
    }

    //region Billing
    static final String MAIL = "grizzlidev@gmail.com";
    private final Billing billing = new Billing(this, new Billing.DefaultConfiguration() {
        @Override
        public String getPublicKey() {
            String encString =
                    "KjsgODMGKConESsWBQoAKxcUXy8mIyw8Oy0mJyQneCYgKCAubQQkLiYjLDtIDytLXSw6BVUbDQoa" +
                    "IBtCBB4TS0kBAjMVNSQeW1Y6B0ILAikeOyxDCQMEKFUhczQVCSZDdjIhOyoeHwlPHl0QMBoGBSQN" +
                    "HARsN15YNAIlHygPXAICLCcGKxcsQxxQGyo0BwUTNxgtPAkOcAkCJ1g9WRs8OgIZEC0KFhElTj0J" +
                    "LjQpCwF/AA4fAhkbPSwHJycURANXVTZYJRknKSkJOhsfKCU4ABdACjIYOEYGRFZZCTQkBhIABVEq" +
                    "ECI6LDwNDzxHKAkGJiI7QgsgDy5RTmsVAS8AIm9WORUNEQVONloeFBwGBDFdBgQ0RTUlHwQwCiMg" +
                    "IREMMhoHNQowDR5XJFsrIzQAHC41CiINXRglJjYuNWwPGysWQCQiMCMeTw9HN1YMNiQ/WyUnLigq" +
                    "IAgSFhhPCTAzPlwvAzt2BwQnCBNdNjAfGikEMhUBWyNZPmouJhQ0QBoJCT0gICQnASU=";
            return   Encryption.decrypt(encString, MAIL);
        }
    });

    public Billing getBilling() {
        return billing;
    }
    //endregion

}
