package es.fedefernandez.android.orm.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.{LinearLayout, TextView}
import es.fedefernandez.android.orm.db.slick.SlickDatabaseHelper
import macroid.FullDsl._
import macroid.{Contexts, IdGeneration}
import slick.dbio.DBIO
import slick.driver.SQLiteDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

class MainActivity extends FragmentActivity with Contexts[FragmentActivity] with IdGeneration {

  var textView = slot[TextView]

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    val view = l[LinearLayout](
      w[TextView] <~ wire(textView)
    ) <~ vertical

    setContentView(getUi(view))
  }

  override def onStart() = {
    super.onStart()

    val helper = new SlickDatabaseHelper(this)
    helper.getReadableDatabase

    helper.database map { db =>
      val query = helper.sampleEntityConfig += (1, "Title", "Description")
      for {
        _ <- db.run(DBIO.seq(query))
        list <- db.run(helper.sampleEntityConfig.result)
      } yield {
        list foreach {
          case (id, title, description) =>
            Log.d("ORMTest", "  " + id + "\t" + title + "\t" + description)
        }
      }
    }
  }
}
