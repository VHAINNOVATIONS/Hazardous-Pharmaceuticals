/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;


/**
 * Generates random values for primitives and simple objects.
 */
public class RandomGenerator {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    /**
     * RandomGenerator
     */
    private RandomGenerator() {
        
    }
    
    /**
     * Random String containing characters chosen from the set of all characters.
     * 
     * @param length of String to produce
     * @return random String of characters with given length
     */
    public static String nextString(int length) {
        return RandomStringUtils.random(length);
    }

    /**
     * Random String containing only alphabetic characters.
     * 
     * @param length of String to produce
     * @return random String of characters with given length
     */
    public static String nextAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * Random String containing only alphanumeric characters.
     * 
     * @param length of String to produce
     * @return random String of characters with given length
     */
    public static String nextAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * Random String containing only numeric characters.
     * 
     * @param length of String to produce
     * @return random String of characters with given length
     */
    public static String nextNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
    
    /**
     * nextInt
     * @return next random int between 0 (inclusive) and MAX_INT
     */
    public static int nextInt() {
        return RANDOM.nextInt(Integer.MAX_VALUE);
    }

    /**
     * nextInt
     * @param i int max value (exclusive)
     * @return next random int between 0 (inclusive) and i (exclusive)
     */
    public static int nextInt(int i) {
        return RANDOM.nextInt(i);
    }

    /**
     * nextBoolean
     * @return next random boolean
     */
    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    /**
     * nextDouble
     * @return next random double between 0.0 (inclusive) and 1.0 (exclusive)
     */
    public static double nextDouble() {
        return RANDOM.nextDouble();
    }

    /**
     * nextDouble
     * @param integer length of integer part of double
     * @param decimal length of decimal part of double
     * @return next random double with specified lengths.
     */
    public static double nextDouble(int integer, int decimal) {
        StringBuffer nextDouble = new StringBuffer();
        nextDouble.append(nextNumeric(integer));
        nextDouble.append(".");
        nextDouble.append(nextNumeric(decimal));
        
        return Double.parseDouble(nextDouble.toString());
    }
    
    /**
     * nextDouble
     * @param d double max value (exclusive)
     * @return next random double between 0.0 (inclusive) and d (exclusive)
     */
    public static double nextDouble(double d) {
        return RANDOM.nextDouble() * d;
    }

    /**
     * nextFloat
     * @return next random float between 0.0 (inclusive) and 1.0 (exclusive)
     */
    public static float nextFloat() {
        return RANDOM.nextFloat();
    }

    /**
     * nextFloat
     * @param f float max value (exclusive)
     * @return next random float between 0.0 (inclusive) and f (exclusive)
     */
    public static float nextFloat(float f) {
        return RANDOM.nextFloat() * f;
    }
    
    /**
     * nextLong
     * @return next random long between 0 (inclusive) and MAX_LONG (exclusive)
     */
    public static long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }

    /**
     * nextLong
     * @param l long max value (exclusive)
     * @return next random long between 0 (inclusive) and l (exclusive)
     */
    public static long nextLong(long l) {
        return Math.abs(RANDOM.nextLong()) % l;
    }
}
