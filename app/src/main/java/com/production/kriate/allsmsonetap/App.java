package com.production.kriate.allsmsonetap;

import android.app.Application;
import android.util.Base64;

import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.Products;
import org.solovyev.android.checkout.RobotmediaDatabase;
import org.solovyev.android.checkout.RobotmediaInventory;

import static java.util.Arrays.asList;
import static org.solovyev.android.checkout.ProductTypes.IN_APP;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;


/**
 *  ласс содержит глобальные переменные приложени€
 */
public class App extends Application {
    @Nonnull
    static final String MAIL = "grizzlidev@gmail.com";
    @Nonnull
    private static final Products products = Products.create().add(IN_APP, asList("all_sms_by_one_type_unlimited_category"));

    @Nonnull
    private final Billing billing = new Billing(this, new Billing.DefaultConfiguration() {
        @Nonnull
        @Override
        public String getPublicKey() {
            String s = "KjsgODMGKConESsWBQoAKxcUXy8mIyw8Oy0mJyQneCYgKCAubQQkLiYjLDtIDytLXSw6BVUbDQoa"+
                "IBtCBB4TS0kBAjMVNSQeW1Y6B0ILAikeOyxDCQMEKFUhczQVCSZDdjIhOyoeHwlPHl0QMBoGBSQN"+
                "HARsN15YNAIlHygPXAICLCcGKxcsQxxQGyo0BwUTNxgtPAkOcAkCJ1g9WRs8OgIZEC0KFhElTj0J"+
                "LjQpCwF/AA4fAhkbPSwHJycURANXVTZYJRknKSkJOhsfKCU4ABdACjIYOEYGRFZZCTQkBhIABVEq"+
                "ECI6LDwNDzxHKAkGJiI7QgsgDy5RTmsVAS8AIm9WORUNEQVONloeFBwGBDFdBgQ0RTUlHwQwCiMg"+
                "IREMMhoHNQowDR5XJFsrIzQAHC41CiINXRglJjYuNWwPGysWQCQiMCMeTw9HN1YMNiQ/WyUnLigq"+
                "IAgSFhhPCTAzPlwvAzt2BwQnCBNdNjAfGikEMhUBWyNZPmouJhQ0QBoJCT0gICQnASU=";
            return  fromX(s, MAIL);
        }

        @Nonnull
        @Override
        public Inventory getFallbackInventory(@Nonnull Checkout checkout, @Nonnull Executor onLoadExecutor) {
            if (RobotmediaDatabase.exists(billing.getContext())) {
                return new RobotmediaInventory(checkout, onLoadExecutor);
            } else {
                return null;
            }
        }

    });
    @Nonnull
    static String fromX(String message, String salt) {
        return x(new String(Base64.decode(message, 0)), salt);
    }
    @Nonnull
    static String toX(@Nonnull String message, @Nonnull String salt) {
        return new String(Base64.encode(x(message, salt).getBytes(), 0));
    }
    @Nonnull
    static String x(String message, String salt) {
        final char[] m = message.toCharArray();
        final char[] s = salt.toCharArray();

        final int ml = m.length;
        final int sl = s.length;
        final char[] result = new char[ml];

        for (int i = 0; i < ml; i++) {
            result[i] = (char) (m[i] ^ s[i % sl]);
        }
        return new String(result);
    }

    @Nonnull
    private final Checkout checkout = Checkout.forApplication(billing, products);
    @Nonnull
    private static App instance;

    public App() {
        instance = this;
    }
    @Nonnull
    public static App get() {
        return instance;
    }
    @Nonnull
    public Checkout getCheckout() {
        return checkout;
    }

}
