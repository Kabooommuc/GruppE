package eu.bsinfo.GruppE.GUI;

import java.math.BigInteger;

public class CustomUUID {

    BigInteger value ;
    static BigInteger lastValue = new BigInteger("0");

    @Override
    public String toString() {
        return getValue();
    }

    private CustomUUID(BigInteger value) {this.value = value;}


    private static CustomUUID next() {
        BigInteger nextValue = lastValue.add(new BigInteger("1"));
        lastValue = nextValue;
        return new CustomUUID(nextValue);
    }

    public static String nextValue() {
        return next().getValue();
    }

    public String getValue() {
        String base36Value = value.toString(36);

        return
                "K" + ("0".repeat(9-base36Value.length())
                        + base36Value
                ).toUpperCase();
    }
}
