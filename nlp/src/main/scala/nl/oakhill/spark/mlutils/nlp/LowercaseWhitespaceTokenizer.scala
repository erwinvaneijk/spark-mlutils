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

import org.apache.lucene.analysis.{Analyzer, LowerCaseFilter}
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents
import org.apache.lucene.analysis.core.WhitespaceTokenizer

/**
  * Do cool stuff with lowercase whitespaces and stuff.
  *
  * @author Erwin van Eijk
  */
class LowercaseWhitespaceTokenizer extends Analyzer {
  override def createComponents(fieldName: String): TokenStreamComponents = {
    val tokenizer = new WhitespaceTokenizer()
    val filter = new LowerCaseFilter(tokenizer)
    new TokenStreamComponents(tokenizer, filter)
  }
}
