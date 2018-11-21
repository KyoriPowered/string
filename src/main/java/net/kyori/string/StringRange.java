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

/**
 * A string range.
 */
public interface StringRange {
  /**
   * Creates a string range encompassing the entire length.
   *
   * @param reader the string reader
   * @return the string range
   */
  static @NonNull StringRange full(final @NonNull StringReader reader) {
    return between(0, reader.length());
  }

  /**
   * Creates a string range encompassing the read length.
   *
   * @param reader the string reader
   * @return the string range
   */
  static @NonNull StringRange read(final @NonNull StringReader reader) {
    return between(0, reader.index());
  }

  /**
   * Creates a string range encompassing the remaining length.
   *
   * @param reader the string reader
   * @return the string range
   */
  static @NonNull StringRange remaining(final @NonNull StringReader reader) {
    return between(reader.remaining(), reader.length());
  }

  /**
   * Creates a string range.
   *
   * @param start the start index
   * @param end the end index
   * @return the string range
   */
  static @NonNull StringRange between(final @NonNegative int start, final @NonNegative int end) {
    return new StringRangeImpl(start, end);
  }

  /**
   * Gets the start index.
   *
   * @return the start index
   */
  @NonNegative int start();

  /**
   * Gets the end index.
   *
   * @return the end index
   */
  @NonNegative int end();

  /**
   * Checks if the string range is empty.
   *
   * @return {@code true} if empty, {@code false} otherwise
   */
  boolean isEmpty();

  /**
   * Creates a new string range composed of the {@link Math#min(int, int) minimum} start and {@link Math#max(int, int) maximum} end.
   *
   * @param that the other string range
   * @return a string range
   */
  @NonNull StringRange expand(final @NonNull StringRange that);
}
