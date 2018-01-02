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

import com.holdenkarau.spark.testing.{DataFrameSuiteBase, SharedSparkContext}
import org.scalatest.{FlatSpec, Matchers}

class WordCleanerSpec extends FlatSpec with DataFrameSuiteBase with Matchers with SparkMatchers {
  protected override implicit def enableHiveSupport : Boolean = false

  private def testColumnName = "words"
  private def inputColumnName = testColumnName
  private def outputColumnName = "clean-words"

  // scalastyle:off underscore.import
  import spark.implicits._
  // scalastyle:on underscore.import

  "A WordCleaner" should "have an input specification" in {
    val wordCleaner = new WordCleaner()
    wordCleaner.setInputCol(testColumnName)
    wordCleaner.getInputCol should be (testColumnName)
    wordCleaner.getOutputCol should not be testColumnName
  }

  it should "have an output specification" in {
    val wordCleaner = new WordCleaner()
    wordCleaner.setOutputCol(testColumnName)
    wordCleaner.getOutputCol should be (testColumnName)
    an [NoSuchElementException] should be thrownBy wordCleaner.getInputCol
  }

  it should "chop strings in bits" in {
    val values = List[String]("This","is", " a ", " test", "B3len", "трудно")
    val df = values.toDF(inputColumnName)
    val tokenizer = new WordCleaner()
      .setInputCol(inputColumnName)
      .setOutputCol(outputColumnName)
    val newDf = tokenizer.transform(df)
    val expectedResults = Seq("this", "is", "a", "test", "blen", "трудно" )
    newDf.collect().zip(expectedResults).foreach{
      case (row, expected) =>
        row should containColumnName(outputColumnName)
        val c = row.getAs[String](outputColumnName)
        c should be (expected)
    }
  }
}
