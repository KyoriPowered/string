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

class StringReaderTest {
  @Test
  void testString() {
    final String string = "foo";
    final StringReader reader = StringReader.create(string);
    assertEquals(string, reader.asString());
  }

  @Test
  void testString_range() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals("foo", reader.string(StringRange.between(0, 3)));
  }

  @Test
  void testLength() {
    assertEquals(3, StringReader.create("foo").length());
    assertEquals(7, StringReader.create("foo bar").length());
  }

  @Test
  void testRemaining() {
    final StringReader reader = StringReader.create("foo");
    assertEquals(3, reader.remaining());
    reader.peek();
    assertEquals(3, reader.remaining());
    reader.skip();
    assertEquals(2, reader.remaining());
    reader.next();
    assertEquals(1, reader.remaining());
  }

  @Test
  void testIndex() {
    final StringReader reader = StringReader.create("foo");
    assertEquals(0, reader.index());
    reader.peek();
    assertEquals(0, reader.index());
    reader.skip();
    assertEquals(1, reader.index());
    reader.next();
    assertEquals(2, reader.index());
  }

  @Test
  void testReadable() {
    final String string = "foo";
    final int length = string.length();
    final StringReader reader = StringReader.create(string);
    assertTrue(reader.readable());
    assertTrue(reader.readable(length)); // we should be able to read the entire string...
    assertFalse(reader.readable(length + 1)); // ...but not more
    for(int i = 0; i < length; i++) {
      reader.skip();
    }
    assertFalse(reader.readable()); // we've read everything available
  }

  @Test
  void testSkip() {
    final StringReader reader = StringReader.create("foo");
    assertEquals('f', reader.peek());
    reader.skip();
    assertEquals('o', reader.peek());
  }

  @Test
  void testSkip_quantity() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals('f', reader.peek());
    reader.skip(6);
    assertEquals('r', reader.peek());
    reader.skip(3);
    assertEquals(9, reader.index());
  }

  @Test
  void testSkip_predicate() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals('f', reader.peek());
    reader.skip(character -> character != 'r');
    assertEquals('r', reader.next());
  }

  @Test
  void testMarkAndReset() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals('f', reader.next());
    final StringReader.Mark mark = reader.mark();
    assertEquals(1, mark.index());
    for(int i = 0; i < 5; i++) {
      reader.skip();
    }
    assertEquals('r', reader.next());
    assertEquals(1, mark.range().start());
    assertEquals(7, mark.range().end());
    assertEquals("oo bar", mark.string());
    assertEquals(7, mark.revert());
    assertEquals('o', reader.next());
    assertEquals('o', reader.next());
  }

  @Test
  void testPeek() {
    final StringReader reader = StringReader.create("foo");
    assertEquals('f', reader.peek());
    assertEquals('f', reader.peek()); // twice to ensure that peeking does not increment
    assertEquals('o', reader.peek(2));
    reader.skip();
    assertEquals('o', reader.peek()); // and once more after skipping
  }

  @Test
  void testPeekTooFar() {
    assertThrows(IndexOutOfBoundsException.class, StringReader.create("")::peek);
    assertThrows(IndexOutOfBoundsException.class, () -> StringReader.create("foo").peek(4));
  }

  @Test
  void testPeekWhile() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals("foo", reader.peek(character -> !Character.isWhitespace(character)));
    assertEquals('f', reader.peek()); // we peeked above, we should be at the beginning
  }

  @Test
  void testNext() {
    final StringReader reader = StringReader.create("foo");
    assertEquals('f', reader.next());
    assertEquals('o', reader.next());
    assertEquals('o', reader.next());
  }

  @Test
  void testNextTooFar() {
    final StringReader reader = StringReader.create("a");
    assertEquals(0, reader.index());
    reader.next();
    assertEquals(1, reader.index());
    assertThrows(IndexOutOfBoundsException.class, reader::next);
    assertEquals(1, reader.index());
  }

  @Test
  void testCopy() {
    final StringReader a = StringReader.create("foo");
    assertEquals('f', a.next());
    assertEquals(2, a.remaining());
    final StringReader b = a.copy();
    assertEquals('o', b.next());
    assertEquals(2, a.remaining());
  }

  @Test
  void testNextWhile() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals("foo", reader.next(character -> !Character.isWhitespace(character)));
    assertEquals(' ', reader.next());
    assertEquals('b', reader.next());
    reader.next(); reader.next();
  }
}
