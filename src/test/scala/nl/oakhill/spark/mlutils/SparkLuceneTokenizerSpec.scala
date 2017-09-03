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

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.{FlatSpec, Matchers}

class SparkLuceneTokenizerSpec extends FlatSpec with Matchers with SparkMatchers with DataFrameSuiteBase {

  protected override implicit def enableHiveSupport : Boolean = false

  private val inputColumnName = "contents"
  private val outputColumnName = "tokenized"

  // scalastyle:off underscore.import
  import spark.implicits._
  // scalastyle:on underscore.import

  "The tokenizer" should "allow setting the input" in {
    val tokenizer = new SparkLuceneTokenizer()
    tokenizer.setInputCol(inputColumnName)
    tokenizer.getInputCol should be(inputColumnName)
  }

  it should "allow setting the output" in {
    val tokenizer = new SparkLuceneTokenizer()
    tokenizer.setOutputCol(outputColumnName)
    tokenizer.getOutputCol should be(outputColumnName)
  }

  it should "chop strings in bits" in {
    val values = List[String]("This is a test")
    val df = values.toDF("contents")
    val tokenizer = new SparkLuceneTokenizer()
      .setInputCol(inputColumnName)
      .setOutputCol(outputColumnName)
    val newDf = tokenizer.transform(df)
    val row = newDf.first()
    row should containColumnName(outputColumnName)
    val c = row.getAs[Seq[String]](outputColumnName)
    c should have length(4)
    c shouldEqual(Seq("this", "is", "a", "test"))
  }
}
