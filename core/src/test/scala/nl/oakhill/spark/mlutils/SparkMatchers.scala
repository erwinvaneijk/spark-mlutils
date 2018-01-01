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

import org.apache.spark.sql.Row
import org.scalatest.matchers.{MatchResult, Matcher}

trait SparkMatchers {

  class ColumnContainsNameMatcher(expectedColumnName: String) extends Matcher[Row] {
    def apply(row: Row): MatchResult = {
      val idx = try {
        Some(row.fieldIndex(expectedColumnName))
      }
      catch {
        case e: IllegalArgumentException =>
          None
      }
      MatchResult(
        idx.isDefined,
        s"""Row did not contain field "$expectedColumnName"""",
        s"""Row did contain field "$expectedColumnName""""
      )
    }
  }

  class ColumnContentMatcher(columnName: String, subMatcher: Matcher[Seq[String]]) extends Matcher[Row] {
    def apply(row: Row): MatchResult = {
      val idx = row.fieldIndex(columnName)
      val content = row.getAs[Seq[String]](idx)
      subMatcher.apply(content)
    }
  }

  def containColumnName(expectedColumnName: String): ColumnContainsNameMatcher = new ColumnContainsNameMatcher(expectedColumnName)
  def containColumnContent(columnName: String, matcher: Matcher[Seq[String]]): ColumnContentMatcher = new ColumnContentMatcher(columnName, matcher)
}


object SparkMatchers extends SparkMatchers

