package es.fedefernandez.android.orm.db.slick

import slick.driver.SQLiteDriver.api._

trait SlickDatabaseConfig {

  class SampleEntity(tag: Tag) extends Table[(Int, String, String)](tag, "sample") {
    def id = column[Int]("ID", O.PrimaryKey)
    def title = column[String]("TITLE")
    def description = column[String]("DESCRIPTION")
    def * = (id, title, description)
  }

  lazy val sampleEntityConfig = TableQuery[SampleEntity]

}
