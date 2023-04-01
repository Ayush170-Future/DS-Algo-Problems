import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class DynamicRangeMinimumQueries {


    // Minimum positive number not in array: MEX
    public static int findMEX(int[] nums, int n)
    {
        Arrays.sort(nums);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == ans)
                ans++;
        }
        return ans;
    }

    // Sieve of Eratosthenes
    static int MAX = 1123456;
    static boolean[] prime = new boolean[MAX];
    static boolean[] used = new boolean[MAX];
    public static void seive() {
        for (int i = 2; i*i < MAX; ++i) {
            if (!used[i]) {
                prime[i] = true;
                for (int j = i*i; j < MAX; j += i) {
                    used[j] = true;
                }
            }
        }
        prime[1] = false;
    }

    // Prime factors using Sieve of Eratosthenes.
    static final int MAXN = 10000007;
    static int sieve[] = new int[MAXN]; // Stores the least prime number that is a factor of i at sieve[i]

    public static void setSieve() {
        for(int i = 0; i < MAXN; i++) {
            sieve[i] = -1;
        }
        for(int i = 2; i * i < MAXN; i++) {
            if(sieve[i] == -1) {
                for(int j = i*i; j < MAXN; j += i) {
                    if(sieve[j] == -1) {
                        sieve[j] = i;
                    }
                }
            }
        }
    }

    // Prime-factorization using Sieve.
    // Returns map of prime factors with their count
    public static HashMap<Integer, Integer> factors(int n) {
        HashMap<Integer, Integer> map = new HashMap<>();

        while(sieve[n] != -1) {
            map.put(sieve[n], map.getOrDefault(sieve[n], 0) + 1);
            n = n/sieve[n];
        }
        map.put(n, map.getOrDefault(n, 0) + 1);
        return map;
    }

    // Normal Prime factorization: as the Sieve one has big memory requirements
    // Optimized till root n
    public static HashMap<Integer, Integer> normalPrimeFactors(int n) {
        HashMap<Integer, Integer> map = new HashMap<>();

        int i = 0;
        for(i = 2;i*i<=n;i++)
        {
            if(n%i == 0)
            {
                int cnt = 0;
                while(n%i == 0)
                {
                    n/=i;
                    cnt++;
                }
                map.put(i, map.getOrDefault(i, 0) + cnt);
            }
        }
        if(n > 1)map.put(n, map.getOrDefault(i, 0) + 1);

        return map;
    }

    // nCr under mod 1e9 + 7
    static long mod = (long) (1e9 + 7);

    static long fast_power(long a, long p) {
        long res = 1;
        while(p > 0) {
            if(p % 2 == 0) {
                a = ((long) a * a) % mod;
                p = p/2;
            } else {
                res = ((long) res * a) % mod;
                p--;
            }
        }
        return res;
    }

    static int factorial (int n) {
        int fact = 1;
        for(int i = 1; i <= n; i++) {
            fact = (int) (((long) fact * i) % mod);
        }
        return fact;
    }

    static int C(int n, int k) {
        return (int) ((long) factorial(n) * fast_power(factorial(k), mod - 2) % mod * fast_power(factorial(n - k), mod - 2) % mod);
    }


    // Bitwise AND of range n to m: n, n+1, n+2,......m
    public static long rangeBitwiseAnd(long m, long n) {
        return (n > m) ? (rangeBitwiseAnd(m/2, n/2) << 1) : m;
    }

    // DSU
    public static int findParent(int u, int[] parent) {
        if(u == parent[u]) return u;
        return parent[u] = findParent(parent[u], parent);
    }

    public static void union(int u, int v, int[] rank, int[] parent) {
        u = findParent(u,parent);
        v = findParent(v, parent);

        if(rank[u] < rank[v]) {
            parent[u] = v;
        } else if(rank[u] > rank[v]) {
            parent[v] = u;
        } else {
            parent[v] = u;
            rank[u]++;
        }
    }

    // Segment tree
    static int size = (int) 5e5;
    static long[] a = new long[size];
    static long[] segment = new long[4*size];
    static long[] lazy = new long[4*size];
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = 1;
        while (t-- > 0) {
            String[] st = br.readLine().split(" ");
            int n = Integer.parseInt(st[0]);
            int q = Integer.parseInt(st[1]);
            String[] str = br.readLine().split(" ");

            for(int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(str[i]);
            }

            build(0, 0, n-1);

            while(q-- > 0) {
                String[] s = br.readLine().split(" ");
                int o = Integer.parseInt(s[0]);
                int l = Integer.parseInt(s[1]) - 1;
                int r = Integer.parseInt(s[2]) - 1;

                if(o == 1) {
                    pointUpdate(0, 0, n-1, l, r+1);
                    a[l] = r+1;
                } else {
                    sb.append(query(0, 0, n - 1, l, r));
                    sb.append("\n");
                }
            }

            sb.append("\n");
        }
        System.out.println(sb);
    }
    static void build(int ind, int low, int high) {
        if(low == high) {
            segment[ind] = a[low];
            return;
        }
        int mid = (low + high)/2;
        build(2*ind+1, low, mid);
        build(2*ind+2, mid+1, high);
        segment[ind] = Math.min(segment[2*ind+1], segment[2*ind+2]);
    }
    static long query(int ind, int low, int high, int l, int r) {
        if(low >= l && high <= r) {
            return segment[ind];
        }
        if(high < l || low > r) return Integer.MAX_VALUE;
        int mid = (low + high) /2;
        long left = query(2*ind+1, low, mid, l, r);
        long right = query(2*ind+2, mid+1, high, l, r);
        return Math.min(left, right);
    }
    static void pointUpdate(int ind, int low, int high, int node, int val) {
        if(low == high) {
            segment[ind] = val;
        }

        else {
            int mid = (low+high)/2;
            if(node <= mid && node >= low)
                pointUpdate(2*ind+1, low, mid, node, val);
            else pointUpdate(2*ind+2, mid+1, high, node, val);

            segment[ind] = Math.min(segment[2*ind+1], segment[2*ind+2]);
        }
    }

    static void rangeUpdate(int ind, int low, int high, int l, int r, int val) {
        if(lazy[ind] != 0) {
            segment[ind] += (high - low + 1)*lazy[ind];
            if(low != high) {
                lazy[2*ind + 1] += lazy[ind];
                lazy[2*ind + 2] += lazy[ind];
            }
            lazy[ind] = 0;
        }

        if(r < low || l > high || low > high) return;

        if(low>= l && high <= r) {
            segment[ind] += (high - low + 1)*val;
            if(low != high) {
                lazy[2*ind+1] += lazy[ind];
                lazy[2*ind+2] += lazy[ind];
            }
            return;
        }

        int mid = (low + high) >> 1;
        rangeUpdate(2*ind+1, low, mid, l, r, val);
        rangeUpdate(2*ind+2, mid+1, high, l, r, val);
        segment[ind] = segment[2*ind+1] + segment[2*ind+2];
    }
    static long querySumLazy(int ind, int low, int high, int l, int r, int val) {
        if(lazy[ind]!=0) {
            segment[ind] += (high - low + 1)*lazy[ind];
            if(low!=high) {
                lazy[2*ind+1] += lazy[ind];
                lazy[2*ind+1] += lazy[ind];
            }
            lazy[ind] = 0;
        }
        if(r < low || l > high || low > high) return 0;

        if(low >= l && high <= r){
            return segment[ind];
        }
        int mid = (low+high) >> 1;
        //return querySumLazy(2*ind+1, low, mid, l, r, val) + querySumLazy(2*ind+2, )
        return 1l;
    }

}
