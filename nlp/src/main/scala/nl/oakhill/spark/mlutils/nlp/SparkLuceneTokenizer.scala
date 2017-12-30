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

package nl.oakhill.spark.mlutils.nlp

import java.io.StringReader

import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.util.{DefaultParamsWritable, Identifiable}
import org.apache.spark.sql.types.{ArrayType, DataType, StringType}


class SparkLuceneTokenizer(override val uid: String) extends UnaryTransformer[String, Seq[String], SparkLuceneTokenizer] with DefaultParamsWritable {

  def this() = this(Identifiable.randomUID("ltok"))

  protected override def createTransformFunc = luceneBasedTokenizerFunction(_)

  private def getTokensFromStream(tokenStream: TokenStream, term: CharTermAttribute, acc: List[String]): List[String] = {
    if (tokenStream.incrementToken()) {
      getTokensFromStream(tokenStream, term, term.toString :: acc)
    }
    else {
      acc
    }
  }

  def luceneBasedTokenizerFunction(input: String): Seq[String] = {
    val lowercaseWhitespaceTokenizer = new LowercaseWhitespaceTokenizer
    val reader = new StringReader(input)
    val inputField = this.inputCol.name
    val tokenStream = lowercaseWhitespaceTokenizer.tokenStream(inputField, reader)
    val term = tokenStream.getAttribute(classOf[CharTermAttribute])
    tokenStream.reset()
    getTokensFromStream(tokenStream, term, List[String]()).reverse.toSeq
  }

  override def copy(extra: ParamMap): SparkLuceneTokenizer = defaultCopy(extra)

  override protected def outputDataType: DataType = ArrayType(StringType, true)
}
