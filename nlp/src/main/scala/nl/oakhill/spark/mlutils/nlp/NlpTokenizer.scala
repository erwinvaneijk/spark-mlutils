/*
 * Copyright (c) 2018 Erwin van Eijk
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

import java.io.{StringReader, File => JFile}

import better.files.File
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import org.apache.spark.ml.UnaryTransformer
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.util.{DefaultParamsWritable, Identifiable}
import org.apache.spark.sql.types.{ArrayType, DataType, StringType}

class NlpTokenizer(override val uid: String) extends UnaryTransformer[String, Seq[String], NlpTokenizer] with DefaultParamsWritable {

  def this() = this(Identifiable.randomUID("ltok"))

  private var tokenizer: Option[TokenizerME] = None

  protected override def createTransformFunc = openNlpTokenizerFunction(_)

  def openNlpTokenizerFunction(input: String): Seq[String] = {
    tokenizer match {
      case Some(t) => t.tokenize(input)
      case None => Seq.empty
    }
  }

  override def copy(extra: ParamMap): NlpTokenizer = defaultCopy(extra)

  override protected def outputDataType: DataType = ArrayType(StringType, true)

  def setModel(modelName: JFile): Unit = {
    val model = File(modelName.getAbsolutePath).fileInputStream(new TokenizerModel(_))
    tokenizer = Some(new TokenizerME(model))
  }
}

