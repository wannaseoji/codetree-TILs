import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int L, N, Q;
	static int[][] map;
	static int[][] kmap;
	static Knight[] knights;
	static class Knight{
		int r,c,h,w,k,damage;
		
		public Knight(int r, int c, int h, int w, int k) {
			super();
			this.r = r;
			this.c = c;
			this.h = h;
			this.w = w;
			this.k = k;
		}

		@Override
		public String toString() {
			return "Knight [r=" + r + ", c=" + c + ", h=" + h + ", w=" + w + ", k=" + k + ", damage=" + damage + "]";
		}

		public Knight() {
			// TODO Auto-generated constructor stub
		}
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		map = new int[L][L];
		kmap = new int[L][L];
		knights = new Knight[L+1];
		for (int i = 0; i < L; ++i) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < L; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		knights[0] = new Knight();
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			knights[i] = new Knight(r,c,h,w,k);
			for (int x = 0; x < knights[i].h; ++x) {
				for (int y = 0; y < knights[i].w; ++y) {
					kmap[x + knights[i].r][y + knights[i].c] = i;
				}
			}
		}
//		print(kmap);
		for(int i=0; i<Q; i++) {
			st = new StringTokenizer(br.readLine());
			int id = Integer.parseInt(st.nextToken()); // i번째 기사
			int d = Integer.parseInt(st.nextToken()); //d방향으로 
			game(id, d);
//			System.out.println(Arrays.toString(knights));
		}
		int sum = 0;
		for(int i=0; i<knights.length; i++) {
			if(knights[i]!=null)
				if(knights[i].k>knights[i].damage)sum+=knights[i].damage;
		}
		System.out.println(sum);
		
	}
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,1,0,-1};
	private static void game(int id, int d) {
		if(knights[id].damage >= knights[id].k) return;
		if(!canMove(id, d))return;
		move(id, d, true);
//		print(kmap);
	}
	private static void move(int id, int d, boolean sel) {
		boolean flag = false;
		int br = knights[id].r;
		int bc = knights[id].c;
		
		for(int i=0; i<knights[id].h; i++) {
			for(int j=0; j<knights[id].w; j++) {
				int r = knights[id].r+i;
				int c = knights[id].c+j;
				int nr = r+dr[d];
				int nc = c+dc[d];
				
				if(check(nr,nc))return;
				if(map[nr][nc]==2)return;
				
				
				
				int nKnight = kmap[nr][nc];
				if(nKnight>0 && nKnight != id) {
					move(nKnight, d, false);
				}
				if(map[nr][nc]==1 && !sel) {
					knights[id].damage++;
//					System.out.println("dammmm!!");
					
				}
				kmap[r][c] = 0;
				
				if(!flag) {
					flag=true;
					br = br+dr[d];
					bc = bc+dc[d];
				}
			}
		}
		knights[id].r = br;
		knights[id].c = bc;
		
		for(int i=0; i<knights[id].h; i++) {
			for(int j=0; j<knights[id].w; j++) {
				int r = knights[id].r+i;
				int c = knights[id].c+j;
				if(knights[id].k>knights[id].damage)kmap[r][c]=id;
				else kmap[r][c] = 0;
			}
		}
		
	}
	private static boolean canMove(int id, int d) {
		boolean  canmove = true;
		for(int i=0; i<knights[id].h; i++) {
			for(int j=0; j<knights[id].w; j++) {
				int nr = knights[id].r+i+dr[d];
				int nc = knights[id].c+j+dc[d];
				
				if(check(nr,nc))return false;
				if(map[nr][nc]==2)return false; //벽
				
				int nKnight = kmap[nr][nc];
				if(nKnight>0 && nKnight != id) {
					if(!canMove(nKnight, d))return false;
				}
				
				
			}
		}
		
		return canmove;
	}
	private static boolean check(int nr, int nc) {
		if(nr<0 || nr>=L || nc <0 || nc>=L)return true;
		return false;
	}
	private static void print(int[][] kmap2) {
		for(int i=0; i<kmap2.length; i++) {
			for(int j=0; j<kmap2[i].length; j++) {
				System.out.print(kmap2[i][j]+" ");
			}
			System.out.println();
		}
	}
}