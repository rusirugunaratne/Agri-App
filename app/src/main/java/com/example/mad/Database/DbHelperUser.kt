import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelperUser(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Users.db"
        private const val TABLE_NAME = "users"
        private const val COL_FIRST_NAME = "firstName"
        private const val COL_LAST_NAME = "lastName"
        private const val COL_COUNTRY = "country"
        private const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_FIRST_NAME TEXT, " +
                "$COL_LAST_NAME TEXT, " +
                "$COL_COUNTRY TEXT, " +
                "$COL_EMAIL TEXT PRIMARY KEY, " +
                "$COL_PASSWORD TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(firstName: String, lastName: String, country: String, email: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_FIRST_NAME, firstName)
            put(COL_LAST_NAME, lastName)
            put(COL_COUNTRY, country)
            put(COL_EMAIL, email)
            put(COL_PASSWORD, password)
        }
        val result = db.insert(TABLE_NAME, null, values)
        return result != -1L
    }

    @SuppressLint("Range")
    fun getUser(email: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        return if (cursor.moveToFirst()) {
            User(
                cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME)),
                cursor.getString(cursor.getColumnIndex(COL_LAST_NAME)),
                cursor.getString(cursor.getColumnIndex(COL_COUNTRY)),
                cursor.getString(cursor.getColumnIndex(COL_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
            )
        } else {
            null
        }
    }

    fun updateUser(email: String, firstName: String?, lastName: String?, country: String?, password: String?): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        if (firstName != null) {
            values.put(COL_FIRST_NAME, firstName)
        }
        if (lastName != null) {
            values.put(COL_LAST_NAME, lastName)
        }
        if (country != null) {
            values.put(COL_COUNTRY, country)
        }
        if (password != null) {
            values.put(COL_PASSWORD, password)
        }
        val result = db.update(TABLE_NAME, values, "$COL_EMAIL = ?", arrayOf(email))
        return result > 0
    }

    data class User(val firstName: String, val lastName: String, val country: String, val email: String, val password: String)
}
