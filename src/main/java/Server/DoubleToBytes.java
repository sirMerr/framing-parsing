package Server;

/**
 *  Converting a double to an eight-byte array and back to a double.
 *
 *  @author     Alex Simonelis
 *  @version    October, 2011
 *
 */
public class DoubleToBytes
{
    public static void main(String s[])
    {
        double d =123.8;
        System.out.println("**** "+ d);
        d = byteArrayToDouble(doubleToByteArray(d));
        System.out.println("**** " + d);
    }


    public static byte [] doubleToByteArray (double numDouble)
    {
        byte [] arrayByte = new byte [8];
        long numLong;

        numLong = Double.doubleToRawLongBits(numDouble);

        arrayByte[0] = (byte)(numLong >>> 56);
        arrayByte[1] = (byte)(numLong >>> 48);
        arrayByte[2] = (byte)(numLong >>> 40);
        arrayByte[3] = (byte)(numLong >>> 32);
        arrayByte[4] = (byte)(numLong >>> 24);
        arrayByte[5] = (byte)(numLong >>> 16);
        arrayByte[6] = (byte)(numLong >>> 8);
        arrayByte[7] = (byte)numLong;

        return arrayByte;
    }

    public static double byteArrayToDouble (byte [] arrayByte)
    {
        double numDouble;
        long numLong;

        numLong = (((long)arrayByte[0] & 0x00000000000000FFL) << 56) | (((long)arrayByte[1] & 0x00000000000000FFL) << 48) |
                (((long)arrayByte[2] & 0x00000000000000FFL) << 40) |	(((long)arrayByte[3] & 0x00000000000000FFL) << 32) |
                (((long)arrayByte[4] & 0x00000000000000FFL) << 24) | (((long)arrayByte[5] & 0x00000000000000FFL) << 16) |
                (((long)arrayByte[6] & 0x00000000000000FFL) <<  8) | ((long)arrayByte[7] & 0x00000000000000FFL);

        numDouble = Double.longBitsToDouble(numLong);

        return numDouble;
    }
}