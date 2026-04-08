package utils;

public class MathUtils {

    public static int Reverse(int n) {

        int sign;
        int rev = 0;

        if (n < 0) {
            sign = -1;
            n = -n;
        } else {
            sign = 1;
        }
        
        while (n > 0) {
            rev = rev * 10 + n % 10;
            n /= 10;
        }

        return sign * rev;
    }

}
