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

import java.util.Objects;

/* package */ final class StringRangeImpl implements StringRange {
  private final int start;
  private final int end;

  /* package */ StringRangeImpl(final int start, final int end) {
    this.start = checkRange("start", start);
    this.end = checkRange("end", end);
  }

  private static int checkRange(final String name, final int value) {
    if(value < 0) {
      throw new IndexOutOfBoundsException(name + " cannot be less than zero");
    }
    return value;
  }

  @Override
  public @NonNegative int start() {
    return this.start;
  }

  @Override
  public @NonNegative int end() {
    return this.end;
  }

  @Override
  public boolean isEmpty() {
    return this.start == this.end;
  }

  @Override
  public @NonNull StringRange expand(final @NonNull StringRange that) {
    return new StringRangeImpl(Math.min(this.start(), that.start()), Math.max(this.end(), that.end()));
  }

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(!(other instanceof StringRange)) return false;
    final StringRange that = (StringRange) other;
    return this.start == that.start() && this.end == that.end();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.start, this.end);
  }
}
