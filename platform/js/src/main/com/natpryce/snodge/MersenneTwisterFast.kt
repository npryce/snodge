package com.natpryce.snodge

import kotlin.js.Math

/**
 * <h3>MersenneTwister and MersenneTwisterFast</h3>
 *
 * **Version 22**, based on version MT199937(99/10/29)
 * of the Mersenne Twister algorithm found at
 * [
   * The Mersenne Twister Home Page](http://www.math.keio.ac.jp/matumoto/emt.html), with the initialization
 * improved using the new 2002/1/26 initialization algorithm
 * By Sean Luke, October 2004.
 
 *
 * **MersenneTwister** is a drop-in subclass replacement
 * for java.util.Random.  It is properly synchronized and
 * can be used in a multithreaded environment.  On modern VMs such
 * as HotSpot, it is approximately 1/3 slower than java.util.Random.
 
 *
 * **MersenneTwisterFast** is not a subclass of java.util.Random.  It has
 * the same public methods as Random does, however, and it is
 * algorithmically identical to MersenneTwister.  MersenneTwisterFast
 * has hard-code inlined all of its methods directly, and made all of them
 * final (well, the ones of consequence anyway).  Further, these
 * methods are *not* synchronized, so the same MersenneTwisterFast
 * instance cannot be shared by multiple threads.  But all this helps
 * MersenneTwisterFast achieve well over twice the speed of MersenneTwister.
 * java.util.Random is about 1/3 slower than MersenneTwisterFast.
 
 * <h3>About the Mersenne Twister</h3>
 *
 * This is a Java version of the C-program for MT19937: Integer version.
 * The MT19937 algorithm was created by Makoto Matsumoto and Takuji Nishimura,
 * who ask: "When you use this, send an email to: matumoto@math.keio.ac.jp
 * with an appropriate reference to your work".  Indicate that this
 * is a translation of their algorithm into Java.
 
 *
 * **Reference. **
 * Makato Matsumoto and Takuji Nishimura,
 * "Mersenne Twister: A 623-Dimensionally Equidistributed Uniform
 * Pseudo-Random Number Generator",
 * *ACM Transactions on Modeling and. Computer Simulation,*
 * Vol. 8, No. 1, January 1998, pp 3--30.
 
 * <h3>About this Version</h3>
 
 *
 * Converted to Kotlin by copying the original Java source into IntelliJ and auto-correcting all errors
 *
 * **Changes since V21:** Minor documentation HTML fixes.
 
 *
 * **Changes since V20:** Added clearGuassian().  Modified stateEquals()
 * to be synchronizd on both objects for MersenneTwister, and changed its
 * documentation.  Added synchronization to both setSeed() methods, to
 * writeState(), and to readState() in MersenneTwister.  Removed synchronization
 * from readObject() in MersenneTwister.
 
 *
 * **Changes since V19:** nextFloat(boolean, boolean) now returns float,
 * not double.
 
 *
 * **Changes since V18:** Removed old final declarations, which used to
 * potentially speed up the code, but no longer.
 
 *
 * **Changes since V17:** Removed vestigial references to &amp;= 0xffffffff
 * which stemmed from the original C code.  The C code could not guarantee that
 * ints were 32 bit, hence the masks.  The vestigial references in the Java
 * code were likely optimized out anyway.
 
 *
 * **Changes since V16:** Added nextDouble(includeZero, includeOne) and
 * nextFloat(includeZero, includeOne) to allow for half-open, fully-closed, and
 * fully-open intervals.
 
 *
 * **Changes Since V15:** Added serialVersionUID to quiet compiler warnings
 * from Sun's overly verbose compilers as of JDK 1.5.
 
 *
 * **Changes Since V14:** made strictfp, with StrictMath.log and StrictMath.sqrt
 * in nextGaussian instead of Math.log and Math.sqrt.  This is largely just to be safe,
 * as it presently makes no difference in the speed, correctness, or results of the
 * algorithm.
 
 *
 * **Changes Since V13:** clone() method CloneNotSupportedException removed.
 
 *
 * **Changes Since V12:** clone() method added.
 
 *
 * **Changes Since V11:** stateEquals(...) method added.  MersenneTwisterFast
 * is equal to other MersenneTwisterFasts with identical state; likewise
 * MersenneTwister is equal to other MersenneTwister with identical state.
 * This isn't equals(...) because that requires a contract of immutability
 * to compare by value.
 
 *
 * **Changes Since V10:** A documentation error suggested that
 * setSeed(int[]) required an int[] array 624 long.  In fact, the array
 * can be any non-zero length.  The new version also checks for this fact.
 
 *
 * **Changes Since V9:** readState(stream) and writeState(stream)
 * provided.
 
 *
 * **Changes Since V8:** setSeed(int) was only using the first 28 bits
 * of the seed; it should have been 32 bits.  For small-number seeds the
 * behavior is identical.
 
 *
 * **Changes Since V7:** A documentation error in MersenneTwisterFast
 * (but not MersenneTwister) stated that nextDouble selects uniformly from
 * the full-open interval [0,1].  It does not.  nextDouble's contract is
 * identical across MersenneTwisterFast, MersenneTwister, and java.util.Random,
 * namely, selection in the half-open interval [0,1).  That is, 1.0 should
 * not be returned.  A similar contract exists in nextFloat.
 
 *
 * **Changes Since V6:** License has changed from LGPL to BSD.
 * New timing information to compare against
 * java.util.Random.  Recent versions of HotSpot have helped Random increase
 * in speed to the point where it is faster than MersenneTwister but slower
 * than MersenneTwisterFast (which should be the case, as it's a less complex
 * algorithm but is synchronized).
 
 *
 * **Changes Since V5:** New empty constructor made to work the same
 * as java.util.Random -- namely, it seeds based on the current time in
 * milliseconds.
 
 *
 * **Changes Since V4:** New initialization algorithms.  See
 * (see [
   * http://www.math.keio.ac.jp/matumoto/MT2002/emt19937ar.html](http://www.math.keio.ac.jp/matumoto/MT2002/emt19937ar.html))
 
 *
 * The MersenneTwister code is based on standard MT19937 C/C++
 * code by Takuji Nishimura,
 * with suggestions from Topher Cooper and Marc Rieffel, July 1997.
 * The code was originally translated into Java by Michael Lecuyer,
 * January 1999, and the original code is Copyright (c) 1999 by Michael Lecuyer.
 
 * <h3>Java notes</h3>
 
 *
 * This implementation implements the bug fixes made
 * in Java 1.2's version of Random, which means it can be used with
 * earlier versions of Java.  See
 * [
   * the JDK 1.2 java.util.Random documentation](http://www.javasoft.com/products/jdk/1.2/docs/api/java/util/Random.html) for further documentation
 * on the random-number generation contracts made.  Additionally, there's
 * an undocumented bug in the JDK java.util.Random.nextBytes() method,
 * which this code fixes.
 
 *
 *  Just like java.util.Random, this
 * generator accepts a long seed but doesn't use all of it.  java.util.Random
 * uses 48 bits.  The Mersenne Twister instead uses 32 bits (int size).
 * So it's best if your seed does not exceed the int range.
 
 *
 * MersenneTwister can be used reliably
 * on JDK version 1.1.5 or above.  Earlier Java versions have serious bugs in
 * java.util.Random; only MersenneTwisterFast (and not MersenneTwister nor
 * java.util.Random) should be used with them.
 
 * <h3>License</h3>
 
 * Copyright (c) 2003 by Sean Luke. <br></br>
 * Portions copyright (c) 1993 by Michael Lecuyer. <br></br>
 * All rights reserved. <br></br>
 
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  *  Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  *  Neither the name of the copyright owners, their employers, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 
 * @version 22
 */


// Note: this class is hard-inlined in all of its methods.  This makes some of
// the methods well-nigh unreadable in their complexity.  In fact, the Mersenne
// Twister is fairly easy code to understand: if you're trying to get a handle
// on the code, I strongly suggest looking at MersenneTwister.java first.
// -- Sean

class MersenneTwisterFast {
    
    private var mt: IntArray? = null // the array for the state vector
    private var mti: Int = 0 // mti==N+1 means mt[N] is not initialized
    private var mag01: IntArray? = null
    
    // a good initial seed (of int size, though stored in a long)
    //private static final long GOOD_SEED = 4357;
    
    private var __nextNextGaussian: Double = 0.toDouble()
    private var __haveNextNextGaussian: Boolean = false
    
    /** Returns true if the MersenneTwisterFast's current internal state is equal to another MersenneTwisterFast.
     * This is roughly the same as equals(other), except that it compares based on value but does not
     * guarantee the contract of immutability (obviously random number generators are immutable).
     * Note that this does NOT check to see if the internal gaussian storage is the same
     * for both.  You can guarantee that the internal gaussian storage is the same (and so the
     * nextGaussian() methods will return the same values) by calling clearGaussian() on both
     * objects.  */
    fun stateEquals(other: MersenneTwisterFast?): Boolean {
        if (other === this) return true
        if (other == null) return false
        
        if (mti != other.mti) return false
        for (x in mag01!!.indices)
            if (mag01!![x] != other.mag01!![x]) return false
        for (x in mt!!.indices)
            if (mt!![x] != other.mt!![x]) return false
        return true
    }
    
    constructor(): this((Math.random() * Long.MAX_VALUE).toLong())
    
    constructor(seed: Long) {
        setSeed(seed)
    }
    
    
    /**
     * Constructor using an array of integers as seed.
     * Your array must have a non-zero length.  Only the first 624 integers
     * in the array are used; if the array is shorter than this then
     * integers are repeatedly used in a wrap-around fashion.
     */
    constructor(array: IntArray) {
        setSeed(array)
    }
    
    
    /**
     * Initalize the pseudo random number generator.  Don't
     * pass in a long that's bigger than an int (Mersenne Twister
     * only uses the first 32 bits for its seed).
     */
    
    fun setSeed(seed: Long) {
        // Due to a bug in java.util.Random clear up to 1.2, we're
        // doing our own Gaussian variable.
        __haveNextNextGaussian = false
        
        mt = IntArray(N)
        
        mag01 = IntArray(2).also {
            it[0] = 0x0
            it[1] = MATRIX_A
        }
        
        mt!![0] = (seed and 0xfffffffffffffff).toInt()
        mti = 1
        while (mti < N) {
            mt!![mti] = 1812433253 * (mt!![mti - 1] xor mt!![mti - 1].ushr(30)) + mti
            mti++
            /* See Knuth TAOCP Vol2. 3rd Ed. P.106 for multiplier. */
            /* In the previous versions, MSBs of the seed affect   */
            /* only MSBs of the array mt[].                        */
            /* 2002/01/09 modified by Makoto Matsumoto             */
            // mt[mti] &= 0xffffffff;
            /* for >32 bit machines */
        }
    }
    
    
    /**
     * Sets the seed of the MersenneTwister using an array of integers.
     * Your array must have a non-zero length.  Only the first 624 integers
     * in the array are used; if the array is shorter than this then
     * integers are repeatedly used in a wrap-around fashion.
     */
    
    fun setSeed(array: IntArray) {
        if (array.isEmpty())
            throw IllegalArgumentException("Array length must be greater than zero")
        var i: Int
        var j: Int
        var k: Int
        setSeed(19650218)
        i = 1
        j = 0
        k = if (N > array.size) N else array.size
        while (k != 0) {
            mt!![i] = (mt!![i] xor (mt!![i - 1] xor mt!![i - 1].ushr(30)) * 1664525) + array[j] + j /* non linear */
            // mt[i] &= 0xffffffff; /* for WORDSIZE > 32 machines */
            i++
            j++
            if (i >= N) {
                mt!![0] = mt!![N - 1]
                i = 1
            }
            if (j >= array.size) j = 0
            k--
        }
        k = N - 1
        while (k != 0) {
            mt!![i] = (mt!![i] xor (mt!![i - 1] xor mt!![i - 1].ushr(30)) * 1566083941) - i /* non linear */
            // mt[i] &= 0xffffffff; /* for WORDSIZE > 32 machines */
            i++
            if (i >= N) {
                mt!![0] = mt!![N - 1]
                i = 1
            }
            k--
        }
        mt!![0] = 0x80000000.toInt() /* MSB is 1; assuring non-zero initial array */
    }
    
    
    fun nextInt(): Int {
        var y: Int
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        return y
    }
    
    
    fun nextShort(): Short {
        var y: Int
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        return y.ushr(16).toShort()
    }
    
    
    fun nextChar(): Char {
        var y: Int
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        return y.ushr(16).toChar()
    }
    
    
    fun nextBoolean(): Boolean {
        var y: Int
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        return y.ushr(31) != 0
    }
    
    
    /** This generates a coin flip with a probability <tt>probability</tt>
     * of returning true, else returning false.  <tt>probability</tt> must
     * be between 0.0 and 1.0, inclusive.   Not as precise a random real
     * event as nextBoolean(double), but twice as fast. To explicitly
     * use this, remember you may need to cast to float first.  */
    
    fun nextBoolean(probability: Float): Boolean {
        var y: Int
        
        if (probability < 0.0f || probability > 1.0f)
            throw IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.")
        if (probability == 0.0f)
            return false            // fix half-open issues
        else if (probability == 1.0f) return true        // fix half-open issues
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        return y.ushr(8) / (1 shl 24).toFloat() < probability
    }
    
    
    /** This generates a coin flip with a probability <tt>probability</tt>
     * of returning true, else returning false.  <tt>probability</tt> must
     * be between 0.0 and 1.0, inclusive.  */
    
    fun nextBoolean(probability: Double): Boolean {
        var y: Int
        var z: Int
        
        if (probability < 0.0 || probability > 1.0)
            throw IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.")
        if (probability == 0.0)
            return false             // fix half-open issues
        else if (probability == 1.0) return true // fix half-open issues
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor z.ushr(1) xor mag01!![z and 0x1]
                kk++
            }
            while (kk < N - 1) {
                z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor z.ushr(1) xor mag01!![z and 0x1]
                kk++
            }
            z = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor z.ushr(1) xor mag01!![z and 0x1]
            
            mti = 0
        }
        
        z = mt!![mti++]
        z = z xor z.ushr(11)                          // TEMPERING_SHIFT_U(z)
        z = z xor (z shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(z)
        z = z xor (z shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(z)
        z = z xor z.ushr(18)                        // TEMPERING_SHIFT_L(z)
        
        /* derived from nextDouble documentation in jdk 1.2 docs, see top */
        return ((y.ushr(6).toLong() shl 27) + z.ushr(5)) / (1L shl 53).toDouble() < probability
    }
    
    
    fun nextByte(): Byte {
        var y: Int
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        return y.ushr(24).toByte()
    }
    
    
    fun nextBytes(bytes: ByteArray) {
        var y: Int
        
        for (x in bytes.indices) {
            if (mti >= N)
            // generate N words at one time
            {
                var kk: Int
                val mt = this.mt // locals are slightly faster
                val mag01 = this.mag01 // locals are slightly faster
                
                kk = 0
                while (kk < N - M) {
                    y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                    kk++
                }
                while (kk < N - 1) {
                    y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                    kk++
                }
                y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
                
                mti = 0
            }
            
            y = mt!![mti++]
            y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
            y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
            y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
            y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
            
            bytes[x] = y.ushr(24).toByte()
        }
    }
    
    
    /** Returns a long drawn uniformly from 0 to n-1.  Suffice it to say,
     * n must be greater than 0, or an IllegalArgumentException is raised.  */
    
    fun nextLong(): Long {
        var y: Int
        var z: Int
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor z.ushr(1) xor mag01!![z and 0x1]
                kk++
            }
            while (kk < N - 1) {
                z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor z.ushr(1) xor mag01!![z and 0x1]
                kk++
            }
            z = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor z.ushr(1) xor mag01!![z and 0x1]
            
            mti = 0
        }
        
        z = mt!![mti++]
        z = z xor z.ushr(11)                          // TEMPERING_SHIFT_U(z)
        z = z xor (z shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(z)
        z = z xor (z shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(z)
        z = z xor z.ushr(18)                        // TEMPERING_SHIFT_L(z)
        
        return (y.toLong() shl 32) + z.toLong()
    }
    
    
    /** Returns a long drawn uniformly from 0 to n-1.  Suffice it to say,
     * n must be &gt; 0, or an IllegalArgumentException is raised.  */
    fun nextLong(n: Long): Long {
        if (n <= 0)
            throw IllegalArgumentException("n must be positive, got: " + n)
        
        var bits: Long
        var `val`: Long
        do {
            var y: Int
            var z: Int
            
            if (mti >= N)
            // generate N words at one time
            {
                var kk: Int
                val mt = this.mt // locals are slightly faster
                val mag01 = this.mag01 // locals are slightly faster
                
                kk = 0
                while (kk < N - M) {
                    y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                    kk++
                }
                while (kk < N - 1) {
                    y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                    kk++
                }
                y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
                
                mti = 0
            }
            
            y = mt!![mti++]
            y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
            y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
            y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
            y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
            
            if (mti >= N)
            // generate N words at one time
            {
                var kk: Int
                val mt = this.mt // locals are slightly faster
                val mag01 = this.mag01 // locals are slightly faster
                
                kk = 0
                while (kk < N - M) {
                    z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + M] xor z.ushr(1) xor mag01!![z and 0x1]
                    kk++
                }
                while (kk < N - 1) {
                    z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + (M - N)] xor z.ushr(1) xor mag01!![z and 0x1]
                    kk++
                }
                z = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                mt[N - 1] = mt[M - 1] xor z.ushr(1) xor mag01!![z and 0x1]
                
                mti = 0
            }
            
            z = mt!![mti++]
            z = z xor z.ushr(11)                          // TEMPERING_SHIFT_U(z)
            z = z xor (z shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(z)
            z = z xor (z shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(z)
            z = z xor z.ushr(18)                        // TEMPERING_SHIFT_L(z)
            
            bits = ((y.toLong() shl 32) + z.toLong()).ushr(1)
            `val` = bits % n
        }
        while (bits - `val` + (n - 1) < 0)
        return `val`
    }
    
    /** Returns a random double in the half-open range from [0.0,1.0).  Thus 0.0 is a valid
     * result but 1.0 is not.  */
    fun nextDouble(): Double {
        var y: Int
        var z: Int
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor z.ushr(1) xor mag01!![z and 0x1]
                kk++
            }
            while (kk < N - 1) {
                z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor z.ushr(1) xor mag01!![z and 0x1]
                kk++
            }
            z = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor z.ushr(1) xor mag01!![z and 0x1]
            
            mti = 0
        }
        
        z = mt!![mti++]
        z = z xor z.ushr(11)                          // TEMPERING_SHIFT_U(z)
        z = z xor (z shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(z)
        z = z xor (z shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(z)
        z = z xor z.ushr(18)                        // TEMPERING_SHIFT_L(z)
        
        /* derived from nextDouble documentation in jdk 1.2 docs, see top */
        return ((y.ushr(6).toLong() shl 27) + z.ushr(5)) / (1L shl 53).toDouble()
    }
    
    
    /** Returns a double in the range from 0.0 to 1.0, possibly inclusive of 0.0 and 1.0 themselves.  Thus:
     
     * <table border=0>
     * <tr><th>Expression</th><th>Interval</th></tr>
     * <tr><td>nextDouble(false, false)</td><td>(0.0, 1.0)</td></tr>
     * <tr><td>nextDouble(true, false)</td><td>[0.0, 1.0)</td></tr>
     * <tr><td>nextDouble(false, true)</td><td>(0.0, 1.0]</td></tr>
     * <tr><td>nextDouble(true, true)</td><td>[0.0, 1.0]</td></tr>
     * <caption>Table of intervals</caption>
    </table> *
     
     *
     * This version preserves all possible random values in the double range.
     */
    fun nextDouble(includeZero: Boolean, includeOne: Boolean): Double {
        var d : Double
        do {
            d = nextDouble()                           // grab a value, initially from half-open [0.0, 1.0)
            if (includeOne && nextBoolean()) d += 1.0  // if includeOne, with 1/2 probability, push to [1.0, 2.0)
        }
        while (d > 1.0 || // everything above 1.0 is always invalid
            !includeZero && d == 0.0)            // if we're not including zero, 0.0 is invalid
        return d
    }
    
    
    /**
     * Clears the internal gaussian variable from the RNG.  You only need to do this
     * in the rare case that you need to guarantee that two RNGs have identical internal
     * state.  Otherwise, disregard this method.  See stateEquals(other).
     */
    fun clearGaussian() {
        __haveNextNextGaussian = false
    }
    
    
    fun nextGaussian(): Double {
        if (__haveNextNextGaussian) {
            __haveNextNextGaussian = false
            return __nextNextGaussian
        }
        else {
            var v1: Double
            var v2: Double
            var s: Double
            do {
                var y: Int
                var z: Int
                var a: Int
                var b: Int
                
                if (mti >= N)
                // generate N words at one time
                {
                    var kk: Int
                    val mt = this.mt // locals are slightly faster
                    val mag01 = this.mag01 // locals are slightly faster
                    
                    kk = 0
                    while (kk < N - M) {
                        y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                        mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                        kk++
                    }
                    while (kk < N - 1) {
                        y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                        mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                        kk++
                    }
                    y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                    mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
                    
                    mti = 0
                }
                
                y = mt!![mti++]
                y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
                y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
                y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
                y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
                
                if (mti >= N)
                // generate N words at one time
                {
                    var kk: Int
                    val mt = this.mt // locals are slightly faster
                    val mag01 = this.mag01 // locals are slightly faster
                    
                    kk = 0
                    while (kk < N - M) {
                        z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                        mt[kk] = mt[kk + M] xor z.ushr(1) xor mag01!![z and 0x1]
                        kk++
                    }
                    while (kk < N - 1) {
                        z = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                        mt[kk] = mt[kk + (M - N)] xor z.ushr(1) xor mag01!![z and 0x1]
                        kk++
                    }
                    z = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                    mt[N - 1] = mt[M - 1] xor z.ushr(1) xor mag01!![z and 0x1]
                    
                    mti = 0
                }
                
                z = mt!![mti++]
                z = z xor z.ushr(11)                          // TEMPERING_SHIFT_U(z)
                z = z xor (z shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(z)
                z = z xor (z shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(z)
                z = z xor z.ushr(18)                        // TEMPERING_SHIFT_L(z)
                
                if (mti >= N)
                // generate N words at one time
                {
                    var kk: Int
                    val mt = this.mt // locals are slightly faster
                    val mag01 = this.mag01 // locals are slightly faster
                    
                    kk = 0
                    while (kk < N - M) {
                        a = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                        mt[kk] = mt[kk + M] xor a.ushr(1) xor mag01!![a and 0x1]
                        kk++
                    }
                    while (kk < N - 1) {
                        a = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                        mt[kk] = mt[kk + (M - N)] xor a.ushr(1) xor mag01!![a and 0x1]
                        kk++
                    }
                    a = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                    mt[N - 1] = mt[M - 1] xor a.ushr(1) xor mag01!![a and 0x1]
                    
                    mti = 0
                }
                
                a = mt!![mti++]
                a = a xor a.ushr(11)                          // TEMPERING_SHIFT_U(a)
                a = a xor (a shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(a)
                a = a xor (a shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(a)
                a = a xor a.ushr(18)                        // TEMPERING_SHIFT_L(a)
                
                if (mti >= N)
                // generate N words at one time
                {
                    var kk: Int
                    val mt = this.mt // locals are slightly faster
                    val mag01 = this.mag01 // locals are slightly faster
                    
                    kk = 0
                    while (kk < N - M) {
                        b = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                        mt[kk] = mt[kk + M] xor b.ushr(1) xor mag01!![b and 0x1]
                        kk++
                    }
                    while (kk < N - 1) {
                        b = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                        mt[kk] = mt[kk + (M - N)] xor b.ushr(1) xor mag01!![b and 0x1]
                        kk++
                    }
                    b = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                    mt[N - 1] = mt[M - 1] xor b.ushr(1) xor mag01!![b and 0x1]
                    
                    mti = 0
                }
                
                b = mt!![mti++]
                b = b xor b.ushr(11)                          // TEMPERING_SHIFT_U(b)
                b = b xor (b shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(b)
                b = b xor (b shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(b)
                b = b xor b.ushr(18)                        // TEMPERING_SHIFT_L(b)
                
                /* derived from nextDouble documentation in jdk 1.2 docs, see top */
                v1 = 2 * (((y.ushr(6).toLong() shl 27) + z.ushr(5)) / (1L shl 53).toDouble()) - 1
                v2 = 2 * (((a.ushr(6).toLong() shl 27) + b.ushr(5)) / (1L shl 53).toDouble()) - 1
                s = v1 * v1 + v2 * v2
            }
            while (s >= 1 || s == 0.0)
            val multiplier = Math.sqrt(-2 * Math.log(s) / s)
            __nextNextGaussian = v2 * multiplier
            __haveNextNextGaussian = true
            return v1 * multiplier
        }
    }
    
    
    /** Returns a random float in the half-open range from [0.0f,1.0f).  Thus 0.0f is a valid
     * result but 1.0f is not.  */
    fun nextFloat(): Float {
        var y: Int
        
        if (mti >= N)
        // generate N words at one time
        {
            var kk: Int
            val mt = this.mt // locals are slightly faster
            val mag01 = this.mag01 // locals are slightly faster
            
            kk = 0
            while (kk < N - M) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            while (kk < N - 1) {
                y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                kk++
            }
            y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
            mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
            
            mti = 0
        }
        
        y = mt!![mti++]
        y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
        y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
        y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
        y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
        
        return y.ushr(8) / (1 shl 24).toFloat()
    }
    
    
    /** Returns a float in the range from 0.0f to 1.0f, possibly inclusive of 0.0f and 1.0f themselves.  Thus:
     
     * <table border=0>
     * <tr><th>Expression</th><th>Interval</th></tr>
     * <tr><td>nextFloat(false, false)</td><td>(0.0f, 1.0f)</td></tr>
     * <tr><td>nextFloat(true, false)</td><td>[0.0f, 1.0f)</td></tr>
     * <tr><td>nextFloat(false, true)</td><td>(0.0f, 1.0f]</td></tr>
     * <tr><td>nextFloat(true, true)</td><td>[0.0f, 1.0f]</td></tr>
     * <caption>Table of intervals</caption>
    </table> *
     
     *
     * This version preserves all possible random values in the float range.
     */
    fun nextFloat(includeZero: Boolean, includeOne: Boolean): Float {
        var d: Float
        do {
            d = nextFloat()                            // grab a value, initially from half-open [0.0f, 1.0f)
            if (includeOne && nextBoolean()) d += 1.0f // if includeOne, with 1/2 probability, push to [1.0f, 2.0f)
        }
        while (d > 1.0f || // everything above 1.0f is always invalid
            !includeZero && d == 0.0f)           // if we're not including zero, 0.0f is invalid
        return d
    }
    
    
    /** Returns an integer drawn uniformly from 0 to n-1.  Suffice it to say,
     * n must be &gt; 0, or an IllegalArgumentException is raised.  */
    fun nextInt(n: Int): Int {
        if (n <= 0)
            throw IllegalArgumentException("n must be positive, got: " + n)
        
        if (n and -n == n)
        // i.e., n is a power of 2
        {
            var y: Int
            
            if (mti >= N)
            // generate N words at one time
            {
                var kk: Int
                val mt = this.mt // locals are slightly faster
                val mag01 = this.mag01 // locals are slightly faster
                
                kk = 0
                while (kk < N - M) {
                    y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                    kk++
                }
                while (kk < N - 1) {
                    y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                    kk++
                }
                y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
                
                mti = 0
            }
            
            y = mt!![mti++]
            y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
            y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
            y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
            y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
            
            return (n * y.ushr(1).toLong() shr 31).toInt()
        }
        
        var bits: Int
        var `val`: Int
        do {
            var y: Int
            
            if (mti >= N)
            // generate N words at one time
            {
                var kk: Int
                val mt = this.mt // locals are slightly faster
                val mag01 = this.mag01 // locals are slightly faster
                
                kk = 0
                while (kk < N - M) {
                    y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + M] xor y.ushr(1) xor mag01!![y and 0x1]
                    kk++
                }
                while (kk < N - 1) {
                    y = mt!![kk] and UPPER_MASK or (mt[kk + 1] and LOWER_MASK)
                    mt[kk] = mt[kk + (M - N)] xor y.ushr(1) xor mag01!![y and 0x1]
                    kk++
                }
                y = mt!![N - 1] and UPPER_MASK or (mt[0] and LOWER_MASK)
                mt[N - 1] = mt[M - 1] xor y.ushr(1) xor mag01!![y and 0x1]
                
                mti = 0
            }
            
            y = mt!![mti++]
            y = y xor y.ushr(11)                          // TEMPERING_SHIFT_U(y)
            y = y xor (y shl 7 and TEMPERING_MASK_B)       // TEMPERING_SHIFT_S(y)
            y = y xor (y shl 15 and TEMPERING_MASK_C)      // TEMPERING_SHIFT_T(y)
            y = y xor y.ushr(18)                        // TEMPERING_SHIFT_L(y)
            
            bits = y.ushr(1)
            `val` = bits % n
        }
        while (bits - `val` + (n - 1) < 0)
        return `val`
    }
    
    companion object {
        // Serialization
        private const val serialVersionUID = -8219700664442619525L  // locked as of Version 15
        
        // Period parameters
        private val N = 624
        private val M = 397
        private val MATRIX_A = 0x9908b0df.toInt()   //    private static final * constant vector a
        private val UPPER_MASK = 0x80000000.toInt() // most significant w-r bits
        private val LOWER_MASK = 0x7fffffff // least significant r bits
        
        // Tempering parameters
        private val TEMPERING_MASK_B = 0x9d2c5680.toInt()
        private val TEMPERING_MASK_C = 0xefc60000.toInt()
    }
}
