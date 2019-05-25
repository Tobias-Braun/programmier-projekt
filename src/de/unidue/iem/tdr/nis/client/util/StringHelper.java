package de.unidue.iem.tdr.nis.client.util;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;

public class StringHelper {

    public static StringHelper empty() {
        return new StringHelper();
    }

    public static StringHelper of(String s) {
        return new StringHelper(s);
    }

    public static StringHelper of(StringBuilder sb) {
        return new StringHelper(sb);
    }

    public static StringHelper applyForEachChar(StringHelper a, StringHelper b, BinaryOperator<Character> o) {
        Logger.logEnter("StringHelper.applyForEachChar", a, b);
        final StringHelper solution = StringHelper.empty();
        for (int i = 0; i < a.length(); i += 0) {
            for (int j = 0; j < b.length(); j++) {
                solution.append(o.apply(a.charAt(i), b.charAt(j)));
                if (++i >= a.length()) break;
            }
        }
        Logger.logExit("StringHelper.applyForEachChar", a, b, solution);
        return solution;
    }

    private final StringBuilder builder;

    public StringHelper(StringBuilder builder) {
        this.builder = builder;
    }

    public String substituteBits(int[] substitutionBits) {
        final StringHelper sub = StringHelper.empty();
        for (int i : substitutionBits) {
            sub.append(this.charAt(i - 1));
        }
        return sub.toString();
    }

    public StringHelper(String string) {
        this.builder = new StringBuilder(string);
    }

    public StringHelper() {
        this.builder = new StringBuilder();
    }

    public String current() {
        return this.builder.toString();
    }

    /**
     * for each sequence of size *size* beginning at index 0, applys the function. If n blocks dont fit the string length (length % size != 0)
     * the remaining chars are appended onto the new string, without the function beeing applied.
     * @param size
     * @param function
     * @return
     */


    public StringHelper repeatForBlockSize(int size, Function<StringHelper, StringHelper> function) {
        assert this.builder.length() % size == 0;
        final int length = this.builder.length();
        final StringHelper[] subStringHelpers = new StringHelper[length / size];
        final StringHelper endAppend = new StringHelper();
        for (int i = 0; i * size + size <= length; i++) {
            subStringHelpers[i] = subStringHelper(i * size, i * size + size);
        }
        for (int i = length - (length % size); i < length; i++) {
            endAppend.append(builder.charAt(i));
        }
        this.builder.delete(0, this.builder.length());
        for (int i = 0; i < subStringHelpers.length; i ++) {
            this.append(function.apply(subStringHelpers[i]).toString());
        }
        this.append(endAppend.toString());
        return this;
    }

    public StringHelper subStringHelper(int start, int end) {
        return StringHelper.of(this.builder.substring(start, end));
    }

    public StringHelper subStringHelper(int start) {
        return StringHelper.of(this.builder.substring(start, this.builder.length()));
    }

    public StringHelper replaceWith(String replacement) {
        this.delete(0, this.length()).append(replacement);
        return this;
    }

    public StringHelper append(Object obj) {
        builder.append(obj);
        return this;
    }

    public StringHelper append(String str) {
        builder.append(str);
        return this;
    }

    /**
     * Appends the specified {@code StringBuffer} to this sequence.
     * <p>
     * The characters of the {@code StringBuffer} argument are appended,
     * in order, to this sequence, increasing the
     * length of this sequence by the length of the argument.
     * If {@code sb} is {@code null}, then the four characters
     * {@code "null"} are appended to this sequence.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at index
     * <i>k</i> in the new character sequence is equal to the character at
     * index <i>k</i> in the old character sequence, if <i>k</i> is less than
     * <i>n</i>; otherwise, it is equal to the character at index <i>k-n</i>
     * in the argument {@code sb}.
     *
     * @param   sb   the {@code StringBuffer} to append.
     * @return a reference to this object.
     */
    public StringHelper append(StringBuffer sb) {
        builder.append(sb);
        return this;
    }

    public StringHelper append(CharSequence s) {
        builder.append(s);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @param s
     * @param start
     * @param end
     */
    public StringHelper append(CharSequence s, int start, int end) {
        builder.append(s, start, end);
        return this;
    }

    public StringHelper append(char[] str) {
        builder.append(str);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @param str
     * @param offset
     * @param len
     */
    public StringHelper append(char[] str, int offset, int len) {
        builder.append(str, offset, len);
        return this;
    }

    public StringHelper append(boolean b) {
        builder.append(b);
        return this;
    }

    public StringHelper append(char c) {
        builder.append(c);
        return this;
    }

    public StringHelper append(int i) {
        builder.append(i);
        return this;
    }

    public StringHelper append(long lng) {
        builder.append(lng);
        return this;
    }

    public StringHelper append(float f) {
        builder.append(f);
        return this;
    }

    public StringHelper append(double d) {
        builder.append(d);
        return this;
    }

    /**
     * @since 1.5
     * @param codePoint
     */
    public StringHelper appendCodePoint(int codePoint) {
        builder.appendCodePoint(codePoint);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param start
     * @param end
     */
    public StringHelper delete(int start, int end) {
        builder.delete(start, end);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param index
     */
    public StringHelper deleteCharAt(int index) {
        builder.deleteCharAt(index);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param start
     * @param end
     * @param str
     */
    public StringHelper replace(int start, int end, String str) {
        builder.replace(start, end, str);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param index
     * @param str
     * @param offset
     * @param len
     */
    public StringHelper insert(int index, char[] str, int offset, int len) {
        builder.insert(index, str, offset, len);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param obj
     */
    public StringHelper insert(int offset, Object obj) {
        builder.insert(offset, obj);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param str
     */
    public StringHelper insert(int offset, String str) {
        builder.insert(offset, str);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param str
     */
    public StringHelper insert(int offset, char[] str) {
        builder.insert(offset, str);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @param dstOffset
     * @param s
     */
    public StringHelper insert(int dstOffset, CharSequence s) {
        builder.insert(dstOffset, s);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @param dstOffset
     * @param s
     * @param start
     * @param end
     */
    public StringHelper insert(int dstOffset, CharSequence s, int start, int end) {
        builder.insert(dstOffset, s, start, end);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param b
     */
    public StringHelper insert(int offset, boolean b) {
        builder.insert(offset, b);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param c
     */
    public StringHelper insert(int offset, char c) {
        builder.insert(offset, c);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param i
     */
    public StringHelper insert(int offset, int i) {
        builder.insert(offset, i);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param l
     */
    public StringHelper insert(int offset, long l) {
        builder.insert(offset, l);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param f
     */
    public StringHelper insert(int offset, float f) {
        builder.insert(offset, f);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @param offset
     * @param d
     */
    public StringHelper insert(int offset, double d) {
        builder.insert(offset, d);
        return this;
    }

    public int indexOf(String str) {
        return builder.indexOf(str);

    }

    public int indexOf(String str, int fromIndex) {
        return builder.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return builder.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return builder.lastIndexOf(str, fromIndex);
    }

    public StringHelper reverse() {
        builder.reverse();
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    /**
     * Returns the length (character count).
     *
     * @return the length of the sequence of characters currently
     *          represented by this object
     */
    public int length() {
        return builder.length();
    }

    /**
     * Returns the current capacity. The capacity is the amount of storage
     * available for newly inserted characters, beyond which an allocation
     * will occur.
     *
     * @return the current capacity
     */
    public int capacity() {
        return builder.capacity();
    }

    /**
     * Ensures that the capacity is at least equal to the specified minimum.
     * If the current capacity is less than the argument, then a new internal
     * array is allocated with greater capacity. The new capacity is the
     * larger of:
     * <ul>
     * <li>The {@code minimumCapacity} argument.
     * <li>Twice the old capacity, plus {@code 2}.
     * </ul>
     * If the {@code minimumCapacity} argument is nonpositive, this
     * method takes no action and simply returns.
     * Note that subsequent operations on this object can reduce the
     * actual capacity below that requested here.
     *
     * @param   minimumCapacity   the minimum desired capacity.
     */
    public void ensureCapacity(int minimumCapacity) {
        builder.ensureCapacity(minimumCapacity);
    }

    /**
     * Attempts to reduce storage used for the character sequence.
     * If the buffer is larger than necessary to hold its current sequence of
     * characters, then it may be resized to become more space efficient.
     * Calling this method may, but is not required to, affect the value
     * returned by a subsequent call to the {@link #capacity()} method.
     */
    public void trimToSize() {
        builder.trimToSize();
    }

    /**
     * Sets the length of the character sequence.
     * The sequence is changed to a new character sequence
     * whose length is specified by the argument. For every nonnegative
     * index <i>k</i> less than {@code newLength}, the character at
     * index <i>k</i> in the new character sequence is the same as the
     * character at index <i>k</i> in the old sequence if <i>k</i> is less
     * than the length of the old character sequence; otherwise, it is the
     * null character {@code '\u005Cu0000'}.
     *
     * In other words, if the {@code newLength} argument is less than
     * the current length, the length is changed to the specified length.
     * <p>
     * If the {@code newLength} argument is greater than or equal
     * to the current length, sufficient null characters
     * ({@code '\u005Cu0000'}) are appended so that
     * length becomes the {@code newLength} argument.
     * <p>
     * The {@code newLength} argument must be greater than or equal
     * to {@code 0}.
     *
     * @param      newLength   the new length
     * @throws IndexOutOfBoundsException  if the
     *               {@code newLength} argument is negative.
     */
    public void setLength(int newLength) {
        builder.setLength(newLength);
    }

    /**
     * Returns the {@code char} value in this sequence at the specified index.
     * The first {@code char} value is at index {@code 0}, the next at index
     * {@code 1}, and so on, as in array indexing.
     * <p>
     * The index argument must be greater than or equal to
     * {@code 0}, and less than the length of this sequence.
     *
     * <p>If the {@code char} value specified by the index is a
     * <a href="Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     *
     * @param      index   the index of the desired {@code char} value.
     * @return the {@code char} value at the specified index.
     * @throws IndexOutOfBoundsException  if {@code index} is
     *             negative or greater than or equal to {@code length()}.
     */
    public char charAt(int index) {
        return builder.charAt(index);
    }

    /**
     * Returns the character (Unicode code point) at the specified
     * index. The index refers to {@code char} values
     * (Unicode code units) and ranges from {@code 0} to
     * {@link #length()}{@code  - 1}.
     *
     * <p> If the {@code char} value specified at the given index
     * is in the high-surrogate range, the following index is less
     * than the length of this sequence, and the
     * {@code char} value at the following index is in the
     * low-surrogate range, then the supplementary code point
     * corresponding to this surrogate pair is returned. Otherwise,
     * the {@code char} value at the given index is returned.
     *
     * @param      index the index to the {@code char} values
     * @return the code point value of the character at the
     *             {@code index}
     * @exception IndexOutOfBoundsException  if the {@code index}
     *             argument is negative or not less than the length of this
     *             sequence.
     */
    public int codePointAt(int index) {
        return builder.codePointAt(index);
    }

    /**
     * Returns the character (Unicode code point) before the specified
     * index. The index refers to {@code char} values
     * (Unicode code units) and ranges from {@code 1} to {@link
     * #length()}.
     *
     * <p> If the {@code char} value at {@code (index - 1)}
     * is in the low-surrogate range, {@code (index - 2)} is not
     * negative, and the {@code char} value at {@code (index -
     * 2)} is in the high-surrogate range, then the
     * supplementary code point value of the surrogate pair is
     * returned. If the {@code char} value at {@code index -
     * 1} is an unpaired low-surrogate or a high-surrogate, the
     * surrogate value is returned.
     *
     * @param     index the index following the code point that should be returned
     * @return the Unicode code point value before the given index.
     * @exception IndexOutOfBoundsException if the {@code index}
     *            argument is less than 1 or greater than the length
     *            of this sequence.
     */
    public int codePointBefore(int index) {
        return builder.codePointBefore(index);
    }

    /**
     * Returns the number of Unicode code points in the specified text
     * range of this sequence. The text range begins at the specified
     * {@code beginIndex} and extends to the {@code char} at
     * index {@code endIndex - 1}. Thus the length (in
     * {@code char}s) of the text range is
     * {@code endIndex-beginIndex}. Unpaired surrogates within
     * this sequence count as one code point each.
     *
     * @param beginIndex the index to the first {@code char} of
     * the text range.
     * @param endIndex the index after the last {@code char} of
     * the text range.
     * @return the number of Unicode code points in the specified text
     * range
     * @exception IndexOutOfBoundsException if the
     * {@code beginIndex} is negative, or {@code endIndex}
     * is larger than the length of this sequence, or
     * {@code beginIndex} is larger than {@code endIndex}.
     */
    public int codePointCount(int beginIndex, int endIndex) {
        return builder.codePointCount(beginIndex, endIndex);
    }

    /**
     * Returns the index within this sequence that is offset from the
     * given {@code index} by {@code codePointOffset} code
     * points. Unpaired surrogates within the text range given by
     * {@code index} and {@code codePointOffset} count as
     * one code point each.
     *
     * @param index the index to be offset
     * @param codePointOffset the offset in code points
     * @return the index within this sequence
     * @exception IndexOutOfBoundsException if {@code index}
     *   is negative or larger then the length of this sequence,
     *   or if {@code codePointOffset} is positive and the subsequence
     *   starting with {@code index} has fewer than
     *   {@code codePointOffset} code points,
     *   or if {@code codePointOffset} is negative and the subsequence
     *   before {@code index} has fewer than the absolute value of
     *   {@code codePointOffset} code points.
     */
    public int offsetByCodePoints(int index, int codePointOffset) {
        return builder.offsetByCodePoints(index, codePointOffset);
    }

    /**
     * Characters are copied from this sequence into the
     * destination character array {@code dst}. The first character to
     * be copied is at index {@code srcBegin}; the last character to
     * be copied is at index {@code srcEnd-1}. The total number of
     * characters to be copied is {@code srcEnd-srcBegin}. The
     * characters are copied into the subarray of {@code dst} starting
     * at index {@code dstBegin} and ending at index:
     * <pre>{@code
     * dstbegin + (srcEnd-srcBegin) - 1
     * }</pre>
     *
     * @param      srcBegin   start copying at this offset.
     * @param      srcEnd     stop copying at this offset.
     * @param      dst        the array to copy the data into.
     * @param      dstBegin   offset into {@code dst}.
     * @throws IndexOutOfBoundsException  if any of the following is true:
     *             <ul>
     *             <li>{@code srcBegin} is negative
     *             <li>{@code dstBegin} is negative
     *             <li>the {@code srcBegin} argument is greater than
     *             the {@code srcEnd} argument.
     *             <li>{@code srcEnd} is greater than
     *             {@code this.length()}.
     *             <li>{@code dstBegin+srcEnd-srcBegin} is greater than
     *             {@code dst.length}
     *             </ul>
     */
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        builder.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    /**
     * The character at the specified index is set to {@code ch}. This
     * sequence is altered to represent a new character sequence that is
     * identical to the old character sequence, except that it contains the
     * character {@code ch} at position {@code index}.
     * <p>
     * The index argument must be greater than or equal to
     * {@code 0}, and less than the length of this sequence.
     *
     * @param      index   the index of the character to modify.
     * @param      ch      the new character.
     * @throws IndexOutOfBoundsException  if {@code index} is
     *             negative or greater than or equal to {@code length()}.
     */
    public void setCharAt(int index, char ch) {
        builder.setCharAt(index, ch);
    }

    /**
     * Returns a new {@code String} that contains a subsequence of
     * characters currently contained in this character sequence. The
     * substring begins at the specified index and extends to the end of
     * this sequence.
     *
     * @param      start    The beginning index, inclusive.
     * @return The new string.
     * @throws StringIndexOutOfBoundsException  if {@code start} is
     *             less than zero, or greater than the length of this object.
     */
    public String substring(int start) {
        return builder.substring(start);
    }

    /**
     * Returns a new character sequence that is a subsequence of this sequence.
     *
     * <p> An invocation of this method of the form
     *
     * <pre>{@code
     * sb.subSequence(begin,&nbsp;end)}</pre>
     *
     * behaves in exactly the same way as the invocation
     *
     * <pre>{@code
     * sb.substring(begin,&nbsp;end)}</pre>
     *
     * This method is provided so that this class can
     * implement the {@link CharSequence} interface.
     *
     * @param      start   the start index, inclusive.
     * @param      end     the end index, exclusive.
     * @return the specified subsequence.
     *
     * @throws IndexOutOfBoundsException
     *          if {@code start} or {@code end} are negative,
     *          if {@code end} is greater than {@code length()},
     *          or if {@code start} is greater than {@code end}
     * @spec JSR-51
     */
    public CharSequence subSequence(int start, int end) {
        return builder.subSequence(start, end);
    }

    /**
     * Returns a new {@code String} that contains a subsequence of
     * characters currently contained in this sequence. The
     * substring begins at the specified {@code start} and
     * extends to the character at index {@code end - 1}.
     *
     * @param      start    The beginning index, inclusive.
     * @param      end      The ending index, exclusive.
     * @return The new string.
     * @throws StringIndexOutOfBoundsException  if {@code start}
     *             or {@code end} are negative or greater than
     *             {@code length()}, or {@code start} is
     *             greater than {@code end}.
     */
    public String substring(int start, int end) {
        return builder.substring(start, end);
    }
}
