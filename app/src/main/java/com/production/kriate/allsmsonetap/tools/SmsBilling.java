package com.production.kriate.allsmsonetap.tools;

import android.app.Activity;

import com.production.kriate.allsmsonetap.App;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.BillingRequests;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.RequestListener;
import org.solovyev.android.checkout.Sku;

/**
 * Created by dima on 31.07.2017.
 */

public class SmsBilling {

    //region Constructor/Destructor

    private static SmsBilling _smsBilling;

    public static SmsBilling Item(Activity activity) {
        if (_smsBilling == null) {
            _smsBilling = new SmsBilling(activity);
        }
        return _smsBilling;
    }

    private SmsBilling(Activity activity) {
        mCheckout = Checkout.forActivity(activity, App.getContext().getBilling());
        mCheckout.start();
        reloadInventory();
    }

    @Override
    protected void finalize() throws Throwable {
        mCheckout.stop();
        super.finalize();
    }

    //endregion

    //region Fields

    private final String CATEGORY = "all_sms_by_one_type_unlimited_category";

    private ActivityCheckout mCheckout;
    public ActivityCheckout getCheckout() {return  mCheckout; }

    private Inventory.Product mProduct;

    private static boolean isPurchased = true;
    public static boolean getIsPurchased() {
        return isPurchased;
    }
    public static void setIsPurchased(boolean isPurchased) {
        SmsBilling.isPurchased = isPurchased;
    }

    //endregion

    //region Helper

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(Inventory.Products products) {
            mProduct = products.get(ProductTypes.IN_APP);
            if (mProduct.supported) {
                SmsBilling.setIsPurchased(mProduct.isPurchased(CATEGORY));
            }
        }
    }

    private void consume(final Purchase purchase) {
        mCheckout.whenReady(new Checkout.EmptyListener() {
            @Override
            public void onReady(BillingRequests requests) {
                requests.consume(purchase.token, makeRequestListener());
            }
        });
    }

    private <T> RequestListener<T> makeRequestListener() {
        return new RequestListener<T>() {
            @Override
            public void onSuccess(T result) {
                reloadInventory();
            }

            @Override
            public void onError(int response, Exception e) {
                reloadInventory();
            }
        };
    }

    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(Purchase purchase) {
            super.onSuccess(purchase);
            reloadInventory();
        }

        @Override
        public void onError(int response, Exception e) {
            super.onError(response, e);
            reloadInventory();
        }
    }

    //endregion

    // Получает данные о покупках
    public void reloadInventory() {
        final Inventory.Request request = Inventory.Request.create();
        // load purchase info
        request.loadAllPurchases();
        // load SKU details
        request.loadSkus(ProductTypes.IN_APP, CATEGORY);
        mCheckout.loadInventory(request, new InventoryCallback());
    }

    //Производится покупка
    public void buy() {
        final Purchase purchase = mProduct.getPurchaseInState(CATEGORY, Purchase.State.PURCHASED);
        if (purchase != null) {
            consume(purchase);
        } else {

            try {
                mCheckout.startPurchaseFlow(mProduct.getSku(CATEGORY), null, new PurchaseListener());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
