import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PrecomputationOfBitwiseCountOfOne {
    public static void main (String[] args) throws IOException {

        long N = (long) (2e5+1);
        long pre[][] = new long[(int) N][19];

        for(int i = 0; i < 19; i++) pre[i][0] = 0l;
        for(int i = 1; i < N; i++) {
            int in = 0;
            long x = i;
            while(x > 0) {
                pre[i][in] = x%2 + pre[i-1][in];
                x = x/2;
                in++;
            }
        }
    }
}
