package com.production.kriate.allsmsonetap.tools;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.production.kriate.allsmsonetap.App;
import com.production.kriate.allsmsonetap.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dima on 02.03.2018.
 */

public class AppSettings {

    final String AUTH_ON_START = "auth_on_start";

    /**
     * Возвращает экземпляр класса настроек. Синглетон
     */
    public static AppSettings Item() {
        if (App._AppSettings == null) {
            App._AppSettings = new AppSettings();
        }
        return App._AppSettings;
    }

    /**
     * Определяет, будет ли отображаться запрос на авторизацию при старте приложения, если клиент не автаризован
     */
    private boolean _AuthOnStart = true;
    public boolean isAuthOnStart() {
        return _AuthOnStart;
    }
    public AppSettings setAuthOnStart(boolean authOnStart) {
        this._AuthOnStart = authOnStart;
        return this;
    }

    //region Methods

    /**
     * Сохраняет настройки приложения в файле настроек
     */
    public void Save(Activity activity){
        SharedPreferences pref = activity.getPreferences(MODE_PRIVATE);

        Editor ed = pref.edit();
        ed.putBoolean(AUTH_ON_START, _AuthOnStart);
        ed.commit();

        Toast.makeText(activity, R.string.preferences_saved, Toast.LENGTH_SHORT).show();
    }

    /**
     * Загружает настройки приложения из файла настроек
     */
    public void Load(Activity activity) {
        SharedPreferences pref = activity.getPreferences(MODE_PRIVATE);
        _AuthOnStart = pref.getBoolean(AUTH_ON_START, true);
    }

    //endregion
}
