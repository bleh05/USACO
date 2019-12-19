import java.io.*;
import java.util.*;

public class milkvisits {
	public static void main(String[] args) throws IOException {
		FastReader scan = new FastReader("milkvisits.in");
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milkvisits.out")));
		//PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		Task solver = new Task();
		//int t = scan.nextInt();
		int t = 1;
		for(int i = 1; i <= t; i++) solver.solve(i, scan, out);
		out.close();
	}
	static class Task {
		public void solve(int testNumber, FastReader sc, PrintWriter pw) {
			int n = sc.nextInt();
			int q = sc.nextInt();
			int[] cows = new int[n+1];
			int max = 0;
			for(int i=1;i<=n;i++){
				cows[i]=sc.nextInt();
				max=Math.max(cows[i],max);
			}
			int[][] sets = new int[n+1][max+1];
			ArrayList<ArrayList<Integer>> adjl = new ArrayList<ArrayList<Integer>>();
			for(int i=0;i<=n;i++){
				adjl.add(new ArrayList<Integer>());
			}
			for(int i = 0;i<n-1;i++){
				int a = sc.nextInt();
				int b = sc.nextInt();
				adjl.get(a).add(b);
				adjl.get(b).add(a);
			}
			sets[1][cows[1]]++;
			Queue<Integer> qu = new LinkedList<Integer>();
			qu.add(1);
			boolean[] vis = new boolean[n+1];
			while(!qu.isEmpty()){
				int x = qu.poll();
				if(vis[x])continue;
				for(int i : adjl.get(x)){
					if(vis[i])continue;
					int ct = 0;
					for(int s : sets[x]){
						sets[i][ct++]+=s;
					}
					qu.add(i);
					if(cows[i]>max)continue;
					sets[i][cows[i]]++;
				}
				vis[x]=true;
			}
			lcaq solv = new lcaq(n+1,1,adjl);
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<q;i++){
				int a = sc.nextInt();
				int b = sc.nextInt();
				int c =sc.nextInt();
				int d = solv.lca(a,b);
				int out=0;
				if(c>max){
					out=0;
				}
				else {
					out = Math.min(1, sets[a][c] + sets[b][c] - 2 * sets[d][c] + (cows[d] == c ? 1 : 0));
				}
				sb.append(out);
			}
			pw.println(sb);
		}
		/*public static long exp(long base, int power){
			if(power == 0) return 1;
			if(power == 1) return (base + mod) % mod;
			long ans = exp(base,power/2);
			ans = (ans*ans + mod) % mod;
			if(power%2 == 1) ans = (ans*base + mod) % mod;
			return (ans + mod) % mod;
		}*/

	}
	static class lcaq{
		int n, l;
		int timer;
		int[] in;
		int[] out;
		ArrayList<ArrayList<Integer>> adjl;
		ArrayList<ArrayList<Integer>> up;
		lcaq(int n,int root,ArrayList<ArrayList<Integer>> adjl){
			this.n=n;
			in= new int[n];
			out = new int[n];
			this.adjl=adjl;
			timer = 0;
			l=(int)Math.ceil(Math.log(n)/Math.log(2));
			up=new ArrayList<ArrayList<Integer>>();
			for(int i=0;i<=n;i++){
				up.add(new ArrayList<Integer>());
			}
			dfs(root,root);
		}
		void dfs(int v, int p){
			in[v]=timer+=1;
			up.get(v).add(p);
			for(int i=1;i<=l;i++){
				up.get(v).add(up.get(up.get(v).get(i-1)).get(i-1));
			}
			for(int x : adjl.get(v)){
				if(x!=p)
					dfs(x,v);
			}
			out[v]=timer+=1;
		}
		boolean is_ancestor(int u, int v)
		{
			return in[u] <= in[v] && out[u] >= out[v];
		}

		int lca(int u, int v)
		{
			if (is_ancestor(u, v))
				return u;
			if (is_ancestor(v, u))
				return v;
			for (int i = l; i >= 0; --i) {
				if (!is_ancestor(up.get(u).get(i), v))
					u = up.get(u).get(i);
			}
			return up.get(u).get(0);
		}
	}
    static void shuffle(long[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            long temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    static void shuffle(int[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public FastReader(String s) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(new File(s)));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

}
