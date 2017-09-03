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

class WordCleanerSpec extends FlatSpec with SharedSparkContext with Matchers with SparkMatchers {
  private def testColumnName = "words"

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

  it should "clean the words" in {

  }
}
