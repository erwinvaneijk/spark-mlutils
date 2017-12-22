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

import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.{FlatSpec, Matchers}

class WordsCleanerSpec extends FlatSpec with SharedSparkContext with Matchers with SparkMatchers {
  private def testColumnName = "words"

  "A WordsCleaner" should "have an input specification" in {
    val wordsCleaner = new WordsCleaner()
    wordsCleaner.setInputCol(testColumnName)
    wordsCleaner.getInputCol should be (testColumnName)
    wordsCleaner.getOutputCol should not be testColumnName
  }

  it should "have an output specification" in {
    val wordsCleaner = new WordsCleaner()
    wordsCleaner.setOutputCol(testColumnName)
    wordsCleaner.getOutputCol should be (testColumnName)
    an [NoSuchElementException] should be thrownBy wordsCleaner.getInputCol
  }

  it should "clean the words" in {
    val values = Array[String]("This"," is"," a", "t3st")
    val expected = Array[String]("This","is","a", "tst")
    val wordsCleaner = new WordsCleaner()
    val transformed = wordsCleaner.wordCleanerFunction(values)
    transformed should be (expected)
  }
}
