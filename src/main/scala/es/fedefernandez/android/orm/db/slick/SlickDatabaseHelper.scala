package es.fedefernandez.android.orm.db.slick

import android.content.Context
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}
import es.fedefernandez.android.orm.db.slick.SlickDatabaseHelper._
import slick.driver.SQLiteDriver
import slick.driver.SQLiteDriver.api._

class SlickDatabaseHelper(context: Context)
  extends SQLiteOpenHelper(context, databaseName, null, databaseVersion)
  with SlickDatabaseConfig {

  val driverName = "org.sqldroid.SQLDroidDriver"

  var database: Option[SQLiteDriver.backend.DatabaseDef] = None

  override def onCreate(sqliteDatabase: SQLiteDatabase): Unit = {
    sampleEntityConfig.schema.create.statements foreach sqliteDatabase.execSQL

    database = Some(Database.forURL(
      url = "jdbc:sqlite:" + sqliteDatabase.getPath,
      driver = driverName))
  }

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int): Unit =
    throw new IllegalStateException("Can't make an upgrade")
}

object SlickDatabaseHelper {

  val databaseName = "slick.db"

  val databaseVersion = 1

}
