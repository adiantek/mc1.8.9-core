package net.core;

public class Mth {

    public static float fastInvSqrt(float pNumber)
    {
        float f = 0.5F * pNumber;
        int i = Float.floatToIntBits(pNumber);
        i = 1597463007 - (i >> 1);
        pNumber = Float.intBitsToFloat(i);
        return pNumber * (1.5F - f * pNumber * pNumber);
    }

    public static double fastInvSqrt(double pNumber)
    {
        double d0 = 0.5D * pNumber;
        long i = Double.doubleToRawLongBits(pNumber);
        i = 6910469410427058090L - (i >> 1);
        pNumber = Double.longBitsToDouble(i);
        return pNumber * (1.5D - d0 * pNumber * pNumber);
    }

    public static float fastInvCubeRoot(float pNumber)
    {
        int i = Float.floatToIntBits(pNumber);
        i = 1419967116 - i / 3;
        float f = Float.intBitsToFloat(i);
        f = 0.6666667F * f + 1.0F / (3.0F * f * f * pNumber);
        return 0.6666667F * f + 1.0F / (3.0F * f * f * pNumber);
    }
}
