/*
 * Copyright (c) 2017, Erwin van Eijk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.oakhill.spark.mlutils

import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.util.{DefaultParamsWritable, Identifiable}
import org.apache.spark.sql.types.{DataType, StringType}

/**
  * Transform words to a cleaner version of themselves.
  *
  * @author Erwin van Eijk
  */
class WordsCleaner(override val uid: String) extends UnaryTransformer[Array[String], Array[String], WordsCleaner] with DefaultParamsWritable {

  def this() = this(Identifiable.randomUID("wc"))

  private def wholeWords = "\\W+".r

  private def noDigits = "[\\d]".r

  protected override def createTransformFunc: (Array[String]) => Array[String] = wordCleanerFunction

  def wordCleanerFunction(a: Array[String]): Array[String] = {
    a.map { str =>
      val word = wholeWords.replaceAllIn(str, "")
      val noDigit = noDigits.replaceAllIn(word, "")
      noDigit
    }
  }

  override def copy(extra: ParamMap): WordsCleaner = defaultCopy(extra)

  override protected def outputDataType: DataType = StringType
}
