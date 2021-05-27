import java.util.*;
import java.io.*;

class BinaryLifting {
	static ArrayList<ArrayList<Integer>> tree = new ArrayList<>();
	static int up[][] = new int[200001][21];
	static int depth[] = new int[200001];; 
	static int log = 20;
	public static void main (String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s1[] = br.readLine().split(" ");
		int n = Integer.parseInt(s1[0]);
		int q = Integer.parseInt(s1[1]);
		depth = new int[n+1];
		for(int i = 0; i < n; i++) {
		    tree.add(new ArrayList<>());
		}
		for(int i = 0; i < n-1; i++) {
		    String s2[] = br.readLine().split(" ");
		    int a = Integer.parseInt(s2[0]);
		    int b = Integer.parseInt(s2[1]);
		    tree.get(Math.min(a, b) - 1).add(Math.max(a, b));
		} 
		depth[1] = 0;
		dfs(1);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < q; i++) {
		    String s[] = br.readLine().split(" ");
		    int a = Integer.parseInt(s[0]);
		    int b = Integer.parseInt(s[1]);
		    int lca = lca(a, b);
		    sb.append(depth[a] - depth[lca] + depth[b] - depth[lca]);
		    sb.append("\n");
		}
		System.out.println(sb);
	}
	public static void dfs(int parent) {
	    for(int child : tree.get(parent - 1)) {
	        up[child][0] = parent;
	        depth[child] = depth[parent] + 1;
	        for(int i = log; i >= 1; i--) {
	            up[child][i] = up[up[child][i-1]][i-1];
	        }
	        dfs(child);
	    }
	}
	public static int lca(int a, int b) {
	    if(depth[b] > depth[a]) {
	        int temp = a;
	        a = b;
	        b = temp;
	    }
	    for(int i = log; i >= 0; i--) {
	        if ((depth[a] - (int)Math.pow(2, i)) >= depth[b]) 
                    a = up[a][i];
	    }
	    if(a == b)
	        return a;
	    for(int j = log; j >= 0; j--) {
    		if(up[a][j] != up[b][j]) {
    			a = up[a][j];
    			b = up[b][j];
    		}
	    }
	    return up[a][0];
	}
}