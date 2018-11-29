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

import net.kyori.lambda.StringRepresentable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface StringReaderGetter extends StringRepresentable {
  /**
   * Gets the underlying string.
   *
   * @return the underlying string
   */
  @Override
  @NonNull String asString();

  /**
   * Gets a substring of the underlying string in range of {@code start} to {@code end}.
   *
   * @param start the start index
   * @param end the end index
   * @return a string
   */
  @NonNull String string(final @NonNegative int start, final @NonNegative int end);

  /**
   * Gets a substring of the underlying string in range of {@code range}.
   *
   * @param range the string range
   * @return a string
   */
  default @NonNull String string(final @NonNull StringRange range) {
    return this.string(range.start(), range.end());
  }

  /**
   * Gets the total length.
   *
   * @return the total length
   */
  @NonNegative int length();

  /**
   * Gets the remaining length.
   *
   * @return the remaining length
   */
  @NonNegative int remaining();

  /**
   * Gets the current index.
   *
   * @return the current index
   */
  @NonNegative int index();

  /**
   * Checks if a single character can be read.
   *
   * @return if a single character can be read
   */
  default boolean readable() {
    return this.readable(1);
  }

  /**
   * Checks if {@code length} characters can be read.
   *
   * @param length the number of characters
   * @return if {@code length} characters can be read
   */
  boolean readable(final @NonNegative int length);

  /**
   * Peeks at the next character.
   *
   * @return the next character
   * @throws IndexOutOfBoundsException if there is no character available
   */
  char peek();

  /**
   * Peeks at the character at {@code index + offset}.
   *
   * @param offset the offset
   * @return the next character
   * @throws IndexOutOfBoundsException if there is no character available
   */
  char peek(final int offset);

  /**
   * Creates a copy.
   *
   * @return a copy
   */
  @NonNull StringReader copy();
}
