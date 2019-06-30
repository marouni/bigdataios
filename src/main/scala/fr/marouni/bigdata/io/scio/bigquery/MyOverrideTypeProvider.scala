package fr.marouni.bigdata.io.scio.bigquery

import com.google.api.services.bigquery.model.TableFieldSchema
import com.spotify.scio.bigquery.validation.OverrideTypeProvider

import scala.reflect.macros.blackbox
import scala.reflect.runtime.universe

class MyOverrideTypeProvider extends OverrideTypeProvider {

  override def shouldOverrideType(tfs: TableFieldSchema): Boolean = ???

  override def shouldOverrideType(c: blackbox.Context)(tpe: c.Type): Boolean = ???

  override def shouldOverrideType(tpe: universe.Type): Boolean = ???

  override def getScalaType(c: blackbox.Context)(tfs: TableFieldSchema): c.Tree = ???

  override def createInstance(c: blackbox.Context)(tpe: c.Type, tree: c.Tree): c.Tree = ???

  override def initializeToTable(c: blackbox.Context)(modifiers: c.universe.Modifiers, variableName: c.universe.TermName, tpe: c.universe.Tree): Unit = ???

  override def getBigQueryType(tpe: universe.Type): String = ???
}
