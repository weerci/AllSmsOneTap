package com.production.kriate.allsmsonetap;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupManager;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by dima on 01.02.2018.
 */

public class SmsBackupAgent extends BackupAgentHelper {

    static final String FILES_BACKUP_KEY = "smsFiles";

    @Override
    public void onCreate() {
        addHelper(FILES_BACKUP_KEY, new DbBackupHelper(App.getContext(), FILES_BACKUP_KEY));
        Log.d(TAG, FILES_BACKUP_KEY);
    }

    private class DbBackupHelper extends FileBackupHelper {

        public DbBackupHelper(Context ctx, String dbName) {
            super(ctx, ctx.getDatabasePath(dbName).getAbsolutePath());
            Log.d(TAG, ctx.getDatabasePath(dbName).getAbsolutePath());
        }
    }

    public static void requestBackup() {
        BackupManager bm = new BackupManager(App.getContext());
        bm.dataChanged();
        Log.d(TAG, "requestBackup");
    }

}
