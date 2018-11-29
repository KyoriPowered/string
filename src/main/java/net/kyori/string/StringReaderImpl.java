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

public class StringReaderImpl implements StringReader {
  protected final String string;
  protected int index;

  public StringReaderImpl(final @NonNull String string) {
    this.string = string;
  }

  private StringReaderImpl(final @NonNull StringReaderImpl reader) {
    this.string = reader.string;
    this.index = reader.index;
  }

  @Override
  public @NonNull String asString() {
    return this.string;
  }

  @Override
  public @NonNull String string(@NonNegative final int start, @NonNegative final int end) {
    return this.string.substring(start, end);
  }

  @Override
  public @NonNegative int length() {
    return this.string.length();
  }

  @Override
  public @NonNegative int remaining() {
    return this.string.length() - this.index;
  }

  @Override
  public @NonNegative int index() {
    return this.index;
  }

  @Override
  public @NonNegative int index(@NonNegative final int newIndex) {
    final int oldIndex = this.index;
    this.index = newIndex;
    return oldIndex;
  }

  @Override
  public boolean readable(final @NonNegative int length) {
    return this.index + length <= this.string.length();
  }

  @Override
  public void skip() {
    this.index++;
  }

  @Override
  public char peek() {
    this.assertReadable();
    return this.string.charAt(this.index);
  }

  @Override
  public char peek(final int offset) {
    this.assertOffsetReadable(offset);
    return this.string.charAt(this.index + offset);
  }

  @Override
  public char next() {
    this.assertReadable();
    return this.string.charAt(this.index++);
  }

  @Override
  public @NonNull StringReader copy() {
    return new StringReaderImpl(this);
  }

  protected void assertReadable() {
    if(!this.readable()) {
      throw new StringIndexOutOfBoundsException(this.index);
    }
  }

  protected void assertOffsetReadable(final @NonNegative int offset) {
    if(!this.readable(offset)) {
      throw new StringIndexOutOfBoundsException(this.index + offset);
    }
  }
}
