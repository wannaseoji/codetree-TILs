import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {
	static int[][]board;
	static int[][]board2;
	static boolean[][] canSplit;
	static int changed;
	static int n, L, R; /*총 칸의 크기, 이동범위의 최솟값, 계란이동 범위의 최댓값*/
	public static void main(String[] args) throws IOException {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		board = new int[n][n];
		board2 = new int[n][n];
		canSplit = new boolean[n][n];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				board2[i][j] = board[i][j];
			}
		}
		int cnt = 0;
		while(true) {
			changed= 0;
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					if(!canSplit[i][j]) {
						BFS(i,j, board2);
						
					}
					
				}
			}
			
			if(changed<2)break;
			cnt++;
		}
//		print(canSplit);
		System.out.println(cnt);
	}
	
	
	
	private static void print(boolean[][] m) {
		System.out.println();
		for(int i=0; i<n; i++) {
			
			for(int j=0; j<n; j++) {
				System.out.print(m[i][j]+" ");
			}
			System.out.println();
		}
	}
	static int[]dr = {0,0,1,-1};
	static int[]dc = {1,-1,0,0};
	private static void BFS(int r, int c,int[][]board) {
		ArrayDeque<int[]> adq = new ArrayDeque<>();
		ArrayDeque<int[]> sub = new ArrayDeque<>();
		adq.add(new int[] {r,c});
		sub.add(new int[] {r,c});
		int totalEgg= 0;
		int ecnt = 1;
		while(!adq.isEmpty()) {
			int[] curr = adq.poll();
			int cr = curr[0];
			int cc = curr[1];
			
			int curEgg = board[cr][cc];
			
			for(int i=0; i<4; i++) {
				int nr = cr+dr[i];
				int nc = cc+dc[i];
				if(check(nr,nc))continue;
				if(canSplit[nr][nc])continue;
				int nEgg = board[nr][nc];
				
				int diffEgg = Math.abs(nEgg-curEgg);
				if(diffEgg>=L && diffEgg<=R)
				{
//					System.out.print("@!@!@!@ ");
					canSplit[nr][nc] = true;
					adq.add(new int[] {nr,nc});
					sub.add(new int[] {nr,nc});
					changed++;
					totalEgg+=board[nr][nc];
					ecnt++;
				}
			}
			//totalEgg = ce;
		}
		if(ecnt>1) {
//			System.out.println(totalEgg);
//			System.out.println(ecnt);
			
			while(!sub.isEmpty()){
				int[] e = sub.poll();
				int rr = e[0];
				int cc = e[1];
				board2[rr][cc]=totalEgg/(ecnt-1);
			}
		}
		
		
		
		
	}
	private static boolean check(int nr, int nc) {
		if(nr<0||nr>=n||nc<0||nc>=n)return true;
		return false;
	}
}