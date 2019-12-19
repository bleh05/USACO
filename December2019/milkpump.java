import java.io.*;
import java.util.*;

public class milkpump {
	public static void main(String[] args) throws IOException {
		FastReader scan = new FastReader("pump.in");
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pump.out")));
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
			int m = sc.nextInt();
			edge[] edg = new edge[m];
			for(int i=0;i<m;i++){
				edg[i]= new edge(sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt());
			}
			Arrays.sort(edg);
			ArrayList<ArrayList<tuple>> arr= new ArrayList<ArrayList<tuple>>();
			for(int i=0;i<=n;i++){
				arr.add(new ArrayList<tuple>());
			}
			long max = 0;
			for(int i =0 ;i<edg.length;){
				int asd = edg[i].flow;
				while(i<m&&edg[i].flow==asd){
					int from = edg[i].from;
					int to = edg[i].to;
					arr.get(from).add(new tuple(to,edg[i].cost,edg[i].flow));
					arr.get(to).add(new tuple(from,edg[i].cost,edg[i].flow));
					i++;
				}
				long[] bfs = new long[n+1];
				long[] flow = new long[n+1];
				Arrays.fill(bfs,Integer.MAX_VALUE/2);
				Queue<pair> q = new LinkedList<pair>();
				q.add(new pair(1,100000));
				bfs[1]=0;
				while(!q.isEmpty()){
					pair pol = q.poll();
					for(tuple x : arr.get(pol.s)){
						if(bfs[pol.s]+x.cost<bfs[x.b]){
							bfs[x.b]=bfs[pol.s]+x.cost;
							flow[x.b]=Math.min(x.flow,pol.flow);
							q.add(new pair(x.b,Math.min(x.flow,pol.flow)));
						}
					}
				}
				if(flow[n]>0&&bfs[n]>0) {
					max = (int)Math.max((double)asd/ bfs[n] * 1000000, max);
				}
			}
			pw.println(max);

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
	static class pair{
		int s, flow;
		pair(int s, int flow){
			this.s=s;
			this.flow=flow;
		}
	}
	static class tuple{
		int b, cost, flow;
		tuple(int b,int cost,int flow){
			this.b=b;
			this.cost=cost;
			this.flow=flow;
		}
	}
	static class edge implements Comparable<edge>{
		int from, to, cost, flow;
		public edge(int a, int b, int c, int d){
			from=a;
			to=b;
			cost=c;
			flow=d;
		}

		@Override
		public int compareTo(edge o) {
			return Integer.compare(o.flow,flow);
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
