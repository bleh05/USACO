import java.io.*;
import java.util.*;

public class cowmbat {
    public static void main(String[] args) throws IOException {
        FastReader scan = new FastReader("cowmbat.in");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowmbat.out")));
        //PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        Task solver = new Task();
        //int t = scan.nextInt();
        int t = 1;
        for(int i = 1; i <= t; i++) solver.solve(i, scan, out);
        out.close();
    }
    static class Task {
        public void solve(int testNumber, FastReader sc, PrintWriter pw) {
            Set<Integer[]> hAshSet = new HashSet<Integer[]>();
            int n = sc.nextInt();
            int chars = sc.nextInt();
            int le = sc.nextInt();
            String co = sc.nextLine();
            ArrayList<ArrayList<pair>> adjl = new ArrayList<ArrayList<pair>>();
            int bfs[][] = new int[chars][chars];
            for(int i = 0;i<chars;i++){
                adjl.add(new ArrayList<pair>());
            }
            for(int i =0;i<chars;i++){
                for(int j=0;j<chars;j++){
                    bfs[i][j]=sc.nextInt();
                    adjl.get(i).add(new pair(j,bfs[i][j]));
                    bfs[i][j]=Integer.MAX_VALUE/2;
                }
            }
            for(int i = 0;i<chars;i++){
                Queue<Integer> q = new LinkedList<Integer>();
                q.add(i);
                bfs[i][i]=0;
                while(!q.isEmpty()){
                    int ind = q.poll();
                    for(pair x : adjl.get(ind)){
                        if(x.we+bfs[i][ind]<bfs[i][x.s]){
                            bfs[i][x.s] = x.we+bfs[i][ind];
                            q.add(x.s);
                        }
                    }
                }
            }
            int dp[][] = new int[n+1][chars];
            int[][] sums = new int[chars][n+1];
            for(int i=0;i<chars;i++){
                for(int j=1;j<=n;j++){
                    sums[i][j]=sums[i][j-1]+bfs[co.charAt(j-1)-'a'][i];
                }
            }
            for(int i=1;i<=n;i++)
                Arrays.fill(dp[i],Integer.MAX_VALUE/2);
            for(int i=0;i<chars;i++){
                dp[le][i]=sums[i][le];
            }
            for(int i=le+1;i<=n;i++) {
                for(int j=0;j<chars;j++){
                    for(int k=0;k<chars;k++){
                        if(j==k){
                            dp[i][j]=Math.min(dp[i][j],dp[i-1][j]+bfs[co.charAt(i-1)-'a'][j]);
                        }
                        else{
                            dp[i][j]=Math.min(dp[i][j],dp[i-le][k]+sums[j][i]-sums[j][i-le]);
                        }
                    }
                }
            }
            //System.out.println(Arrays.deepToString(dp));
            int min=Integer.MAX_VALUE;
            for(int i=0;i<chars;i++){
                min = Math.min(min,dp[n][i]);
            }
            pw.println(min);
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
        int s, we;
        pair(int s, int we){
            this.s=s;
            this.we=we;
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
