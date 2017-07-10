package com.production.kriate.allsmsonetap;

import android.app.Application;
import android.util.Base64;
import android.util.Xml;

import com.production.kriate.allsmsonetap.tools.Encryption;

import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.RobotmediaDatabase;
import org.solovyev.android.checkout.RobotmediaInventory;

import static java.util.Arrays.asList;
import static org.solovyev.android.checkout.ProductTypes.IN_APP;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;


/**
 * ����� �������� ���������� ���������� ����������
 */
public class App extends Application {

    //region billing
    @Nonnull
    static final String MAIL = "grizzlidev@gmail.com";

    private final Billing billing = new Billing(this, new Billing.DefaultConfiguration() {
        @Override
        public String getPublicKey() {
            String encString = "KjsgODMGKConESsWBQoAKxcUXy8mIyw8Oy0mJyQneCYgKCAubQQkLiYjLDtIDytLXSw6BVUbDQoa" +
                    "IBtCBB4TS0kBAjMVNSQeW1Y6B0ILAikeOyxDCQMEKFUhczQVCSZDdjIhOyoeHwlPHl0QMBoGBSQN" +
                    "HARsN15YNAIlHygPXAICLCcGKxcsQxxQGyo0BwUTNxgtPAkOcAkCJ1g9WRs8OgIZEC0KFhElTj0J" +
                    "LjQpCwF/AA4fAhkbPSwHJycURANXVTZYJRknKSkJOhsfKCU4ABdACjIYOEYGRFZZCTQkBhIABVEq" +
                    "ECI6LDwNDzxHKAkGJiI7QgsgDy5RTmsVAS8AIm9WORUNEQVONloeFBwGBDFdBgQ0RTUlHwQwCiMg" +
                    "IREMMhoHNQowDR5XJFsrIzQAHC41CiINXRglJjYuNWwPGysWQCQiMCMeTw9HN1YMNiQ/WyUnLigq" +
                    "IAgSFhhPCTAzPlwvAzt2BwQnCBNdNjAfGikEMhUBWyNZPmouJhQ0QBoJCT0gICQnASU=";

            return Encryption.decrypt(encString, MAIL);
        }
    });

    public Billing getBilling() {
        return billing;
    }

    //endregion

    @Nonnull
    private static App instance;

    public App() {
        instance = this;
    }

    @Nonnull
    public static App get() {
        return instance;
    }
}
