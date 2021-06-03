import java.io.*;
class DigitDpSumRange {
    static long dp[][][] = new long[100][100][2];
    public static long solve(String number, int n, int x, int tight) {
        if(x < 0) return 0;
        if(n == 1) {
            if(x >= 0 && x <= 9) {
                return 1l;
            }
            return 0l;
        }
        if(dp[n][x][tight] != -1) return dp[n][x][tight];
        int ub = tight == 1 ? number.charAt(number.length()-n) - '0' : 9;
        long ans = 0l;
        for(int dig = 0; dig <= ub; dig++) {
            ans += solve(number, n - 1, x - dig, tight == 1 && dig == ub ? 1 : 0);
        }
        return dp[n][x][tight] = ans;
    }
    public static void memset() {
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                for(int k = 0; k < 2; k++) {
                    dp[i][j][k] = -1l;
                }
            }
        }
    }
    public static void main (String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s[] = br.readLine().split(" ");
		long a = Long.parseLong(s[0])-1;
		long b = Long.parseLong(s[1]);
		int sum = Integer.parseInt(s[2]);
		memset();
		long ans = solve(s[1], s[1].length(), sum, 1);
		memset();
		ans = ans - solve(String.valueOf(a), String.valueOf(a).length(), sum, 1);
		System.out.println(ans);
	}
}