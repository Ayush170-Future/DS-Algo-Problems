import java.io.*;
class Solution {
    static long dp[][][][] = new long[20][2][2][2];
    public static long solve(String number, int n, int even, int leading_zeros, int tight) {
        if(n == 0) {
            return 1l;
        }
        int ub = tight == 1 ? number.charAt(number.length() - n) - '0' : 9;
        if(dp[n][even][leading_zeros][tight] != -1)
            return dp[n][even][leading_zeros][tight];
        if(even == 1) {
            long ans = 0l;
            int even_nos[] = {0, 2, 4, 6, 8};
            for(int val : even_nos) 
                if(val <= ub)
                    ans += solve(number, n-1, 0, 0, tight == 1 && val == ub ? 1 : 0);
            return dp[n][even][leading_zeros][tight] = ans;
        } else {
            long ans = 0;
            if(leading_zeros == 1)
                ans += solve(number, n-1, 0, 1, 0);
            int odd_nos[] = {1, 3, 5, 7, 9};
            for(int val : odd_nos) 
                if(val <= ub) 
                    ans += solve(number, n-1, 1, 0, tight == 1 && val == ub ? 1 : 0);
            return dp[n][even][leading_zeros][tight] = ans;
        }
    }
    public static void memset() {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 2; j++) {
                for(int k = 0; k < 2; k++) {
                    for(int l = 0; l < 2; l++)
                        dp[i][j][k][l] = -1l;
                }
            }
        }
    }
    public static void main (String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(br.readLine());
		for(int tt = 1; tt <= t; tt++) {
    		String s[] = br.readLine().split(" ");
    		String A = String.valueOf(Long.parseLong(s[0])-1);
    		memset();
    		long ansl = solve(s[1], s[1].length(), 0, 1, 1);
    		memset();
    		long ans = ansl - solve(A, A.length(), 0, 1, 1);
    		System.out.println("Case #" +tt +": " +ans);
		}
	}
}