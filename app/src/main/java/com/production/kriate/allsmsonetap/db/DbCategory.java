package com.production.kriate.allsmsonetap.db;

import android.content.Context;
import com.production.kriate.allsmsonetap.R;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс уровня модели.
 * Описывает сущность "Категория", дающей возможность группировать шаблоны по категориям.
 */
public class DbCategory implements Serializable {
    public static final long EMPTY_ID = -1;

    private final long mId;       // Идентификатор категории в базе
    private final String mName;  // Название категории
    private final ArrayList<DbSms> mListSms; //Список шаблонов категории

    public DbCategory(long id, String name, ArrayList<DbSms> listSms){
        mId = id;
        mName = name;
        mListSms = listSms;
    }

    public static DbCategory getEmptyCategory(Context context) {
        return new DbCategory(EMPTY_ID, context.getString(R.string.category_null),  null);
    }
    public long getId() {
        return mId;
    }
    public String getName() {
        return mName;
    }
    public ArrayList<DbSms> getListSms() {
        return mListSms;
    }
}
