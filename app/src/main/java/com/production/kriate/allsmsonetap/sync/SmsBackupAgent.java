package com.production.kriate.allsmsonetap.sync;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupManager;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.os.ParcelFileDescriptor;

import java.io.IOException;

/**
 * Created by dima on 01.02.2018.
 */

public class SmsBackupAgent extends BackupAgentHelper {

    static final Object[] sDataLock = new Object[0];

    // The name of the file
    static final String FILE_SMS = "sms";
    static final String FILE_COUNT = "count";


    // A key to uniquely identify the set of backup data
    static final String FILES_BACKUP_KEY = "smsFiles";

    //region Override methods
    @Override
    public void onCreate() {
        FileBackupHelper helper = new FileBackupHelper(this,
                FILE_SMS, FILE_COUNT);
        addHelper(FILES_BACKUP_KEY, helper);
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {
        synchronized (SmsBackupAgent.sDataLock) {
            super.onBackup(oldState, data, newState);
        }
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {
        synchronized (SmsBackupAgent.sDataLock) {
            super.onRestore(data, appVersionCode, newState);
        }
    }
    //endregion

    // Уведомление об изменении данных
    public static void requestBackup(Context context) {
        LoadDataFromDb();

        BackupManager bm = new BackupManager(context);
        bm.dataChanged();
    }

    private static void LoadDataFromDb() {

    }
}
