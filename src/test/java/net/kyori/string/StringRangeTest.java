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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringRangeTest {
  @Test
  void testSanity() {
    assertThrows(IndexOutOfBoundsException.class, () -> StringRange.between(-1, 0));
    assertThrows(IndexOutOfBoundsException.class, () -> StringRange.between(0, -1));
  }

  @Test
  void testFull() {
    final StringReader reader = StringReader.create("foo bar");
    final StringRange range = StringRange.full(reader);
    assertEquals(0, range.start());
    assertEquals(7, range.end());
  }

  @Test
  void testRead() {
    final StringReader reader = StringReader.create("foo bar");
    reader.skip(3);
    final StringRange range = StringRange.read(reader);
    assertEquals(0, range.start());
    assertEquals(3, range.end());
  }

  @Test
  void testRemaining() {
    final StringReader reader = StringReader.create("foo bar");
    reader.skip(3);
    final StringRange range = StringRange.remaining(reader);
    assertEquals(4, range.start());
    assertEquals(7, range.end());
  }

  @Test
  void testEmpty() {
    assertTrue(StringRange.between(0, 0).isEmpty());
    assertFalse(StringRange.between(0, 1).isEmpty());
  }

  @Test
  void testExpand() {
    final StringRange range = StringRange.between(0, 3).expand(StringRange.between(1, 6));
    assertEquals(0, range.start());
    assertEquals(6, range.end());
  }
}
