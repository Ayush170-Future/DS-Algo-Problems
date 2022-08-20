import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class LongestRegularBracketSequence {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int n = s.length();
        Stack<Integer> stack = new Stack<>();
        int[] dp = new int[n];
        int ans = -1;
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(i);
                continue;
            }
            if(stack.isEmpty()) continue;
            int pop = stack.pop();
            dp[i] = i - pop + 1 + (pop == 1 ? 0 : dp[pop - 1]);
            ans = Math.max(ans, dp[i]);
        }

        int count = 0;
        for(int i = 0; i < n; i++) if(dp[i] == ans) count++;
        if(count == 0) {
            System.out.println("0 1");
            return;
        }
        System.out.println(ans +" " +count);
    }
}
