/*
 * This file is part of string, licensed under the MIT License.
 *
 * Copyright (c) 2018 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.string;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.IntPredicate;

public interface StringReader extends StringReaderGetter {
  /**
   * Creates a new string reader.
   *
   * @param string the string
   * @return a string reader
   */
  static @NonNull StringReader create(final @NonNull String string) {
    return new StringReaderImpl(string);
  }

  /**
   * Sets the index.
   *
   * @param index the new index
   * @return the old index
   */
  @NonNegative int index(final @NonNegative int index);

  /**
   * Skips a single character.
   */
  void skip();

  /**
   * Skips {@code n} characters.
   *
   * @param n the the number of characters to skip
   */
  default void skip(final int n) {
    for(int i = 0; i < n; i++) {
      this.skip();
    }
  }

  /**
   * Skips characters while {@code predicate} is satisfied.
   *
   * @param predicate the predicate
   */
  default void skip(final @NonNull IntPredicate predicate) {
    while(this.readable() && predicate.test(this.peek())) {
      this.skip();
    }
  }

  /**
   * Gets the next character.
   *
   * @return the next character
   * @throws IndexOutOfBoundsException if there is no character available
   */
  char next();
}
