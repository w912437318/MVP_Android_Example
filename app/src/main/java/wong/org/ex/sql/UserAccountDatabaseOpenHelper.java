package wong.org.ex.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class UserAccountDatabaseOpenHelper extends OrmLiteSqliteOpenHelper {
    private static UserAccountDatabaseOpenHelper sUserAccountDatabaseOpenHelper;
    private Dao<UserAccountDao, Integer> mUserAccountDao;

    public static UserAccountDatabaseOpenHelper getUserAccountDatabaseOpenHelper(Context context) {
        if (sUserAccountDatabaseOpenHelper == null) {
            synchronized (UserAccountDatabaseOpenHelper.class) {
                if (sUserAccountDatabaseOpenHelper == null) {
                    sUserAccountDatabaseOpenHelper = new UserAccountDatabaseOpenHelper(context, "UserAccount", null, 1);
                }
            }
        }
        return sUserAccountDatabaseOpenHelper;
    }

    public Dao<UserAccountDao, Integer> getUserAccountDao() {
        if (mUserAccountDao == null) {
            synchronized (UserAccountDatabaseOpenHelper.class) {
                if (mUserAccountDao == null) {
                    try {
                        mUserAccountDao = getDao(UserAccountDao.class);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mUserAccountDao;
    }

    private UserAccountDatabaseOpenHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserAccountDao.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
