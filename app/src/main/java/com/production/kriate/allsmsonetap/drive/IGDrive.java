package com.production.kriate.allsmsonetap.drive;

import android.app.Activity;

/**
 * Created by dima on 05.03.2018.
 */

public interface IGDrive {
    /**
     * Пользователь авторизуется на google drive
     * @return Если авторизация прошла успешно, возвращается true, иначе false
     */
    boolean auth();

    /**
     * Данные сохраняеются на google disk
     * @return Если сохранение прошло успешно, возвращается true, иначе false
     */
    boolean save();

    /**
     *
     * @return Если загрузка файла прошла успешно, возвращается true, иначе false
     */
    boolean load();

    /**
     * Вызывается диалого подключения к google drive под другим аккаунтом
     * @return
     */
    boolean reSing();

}
