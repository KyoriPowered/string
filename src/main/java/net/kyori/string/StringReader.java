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

import java.util.function.Consumer;
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

  /**
   * Creates a mark.
   *
   * @return a mark
   */
  @NonNull Mark mark();

  /**
   * A mark captures the index of the string reader is was created from at the time of creation, and
   * allows {@link Mark#restoreIndex() restoring} the captured index to the string reader after any
   * operation to the string reader has been made.
   */
  interface Mark {
    /**
     * Gets the string reader index when the mark was created.
     *
     * @return the string reader index when the mark was created
     */
    @NonNegative int start();

    /**
     * Apply {@code consumer} to the string reader.
     *
     * @param consumer the reader consumer
     * @return this mark
     */
    @NonNull Mark with(final @NonNull Consumer<StringReader> consumer);

    /**
     * Restores the string reader index to the index captured when the mark was created.
     *
     * @return this mark
     */
    @NonNull Mark restoreIndex();

    /**
     * Gets a range of the {@link #start() start} and {@link StringReader#index() current}.
     *
     * @return a range
     */
    @NonNull StringRange range();

    /**
     * Gets the string between the {@link #start() start} and {@link StringReader#index() current} positions.
     *
     * @return a string
     */
    @NonNull String string();
  }
}
