import java.util.*;
import java.io.*;
class EulerTour2PathSumOfTree {
    static ArrayList<ArrayList<Integer>> tree = new ArrayList<>();
    static ArrayList<Integer> flat_array = new ArrayList<>();
    static int start[];
    static int end[];
    static int seg[];
    public static int dfs(int parent) {
        flat_array.add(parent);
        ArrayList<Integer> childrens = tree.get(parent - 1);
        if(childrens == null) {
            return parent;
        }
        for(int child : childrens) {               // Making euler tour 2 in here
            int temp = dfs(child);
            flat_array.add(-1 * temp);
        }
        return parent;
    }
    
    public static void built(int i, int low, int high) {
        if(low == high) {
            seg[i] = flat_array.get(low);
            return;
        }                                         // Building segment tree of flat_array(euler tour type 2)
        int mid = (low + high)/2;
        built(2*i + 1, low, mid);
        built(2*i + 2, mid + 1, high);
        seg[i] = seg[2*i + 1] + seg[2*i + 2];
    }
    
    public static void update(int i, int low, int high, int ind, int value) {
        if(low == high) {
            if(low == ind)
                seg[i] = value;
            return;
        }                                         // Updating the segment tree
        int mid = (low + high)/2;              
        update(2*i + 1, low, mid, ind, value);
        update(2*i + 2, mid + 1, high, ind, value);
        seg[i] = seg[2*i + 1] + seg[2*i + 2];
    }
    
    public static int query(int i, int low, int high, int l, int h) {
        if(low >= l && high <= h) {
	        return seg[i];
	    }
        if(high < l || low > h) return 0;
        int mid = (low + high)/2;               // Finding sum of values in segment tree from 0 to start[inputed index]
        int left = query(2*i + 1, low, mid, l, h);
        int right = query(2*i + 2, mid + 1, high, l, h);
        return left + right;
    }
    
    public static void main (String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s1[] = br.readLine().split(" ");
		int n = Integer.parseInt(s1[0]);
		int q = Integer.parseInt(s1[1]);
	    String sv[] = br.readLine().split(" ");
	    int values[] = new int[n];
	    for(int i = 0; i < n; i++) {
	        values[i] = Integer.parseInt(sv[i]);
	    }
	    for(int i = 0; i < n; i++) {
	        tree.add(new ArrayList<>());
	    }
	    for(int i = 0; i < n-1; i++) {
	        String nodes[] = br.readLine().split(" ");
	        tree.get(Integer.parseInt(nodes[0])-1).add(Integer.parseInt(nodes[1]));
	    }
	    int temp = dfs(1);
	    flat_array.add(-1 * temp);
	    start = new int[n+1];
	    end = new int[n+1];
	    for(int i = 0; i < 2*n; i++) {
	        int val = flat_array.get(i);
	        if(val > 0) {
	            start[val] = i;
	        } else {
	            end[-1 * val] = i;
	        }
	    }
	    for(int i = 0; i < 2*n; i++) {
	        int val = flat_array.get(i);
	        if(val > 0) {
	            flat_array.set(i, values[val-1]);
	        } else {
	            val = val * -1;
	            flat_array.set(i, -1 * values[val-1]);
	        }
	    }
	    seg = new int[4*flat_array.size()];
	    built(0, 0, flat_array.size() - 1);
	    StringBuilder sb = new StringBuilder();
	    while(q-- > 0) {
	        String s[] = br.readLine().split(" ");
	        if(Integer.parseInt(s[0]) == 1) {
	            int x = Integer.parseInt(s[1]);
	            int value = Integer.parseInt(s[2]);
	            
	            update(0, 0, flat_array.size() - 1, start[x], value);
	            update(0, 0, flat_array.size() - 1, end[x], -1 * value);
	        } else {
	            int y = Integer.parseInt(s[1]);
	            int ans = query(0, 0, flat_array.size()-1, 0, start[y]);
	            sb.append(ans);
	            sb.append("\n");
	        }
	    }
	    System.out.println(sb);
	}
}