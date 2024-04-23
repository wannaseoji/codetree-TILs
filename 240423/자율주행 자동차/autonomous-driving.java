import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int M;
	static int[][] map;
	//NxM
	//청소기 방향 : 0북 1동 2남 3서
	static int[] dy = {-1,0,1,0};
	static int[] dx = {0,1,0,-1};
	static int ans;
	// 
	
	//	0은 청소 x
	//  1은 청소 o 
	public static void main(String[] args) throws IOException {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 StringTokenizer st = new StringTokenizer(br.readLine());
		 N = Integer.parseInt(st.nextToken());
		 M = Integer.parseInt(st.nextToken());
		 map = new int[N][M];
		 
		 st = new StringTokenizer(br.readLine());
		 int sy = Integer.parseInt(st.nextToken());
		 int sx = Integer.parseInt(st.nextToken());
		 int d = Integer.parseInt(st.nextToken()); //0북 1동 2남 3서
		 
		 for(int i=0; i<N; i++) {
			 st = new StringTokenizer(br.readLine());
			 for(int j=0; j<M; j++) {
				 map[i][j] = Integer.parseInt(st.nextToken());
			 }
		 }
		 
		 cleanRobotMove(sy,sx,d);
		 System.out.println(ans+1);
	}
	private static void cleanRobotMove(int y, int x, int d) {
		//basis
		
		map[y][x] = 3;// cleaned 표시
		
		//print(map);
		
		//inductive
		// 3. 청소되지 않은 빈칸이 있다면 
		//		반시계 방향으로 90회전한다.
		//		바라보는 방향을 기준으로 앞쪽칸이 청소되지 않은 빈 칸인 경우 한칸 전진한다.
		//		1.번으로 돌아간다.
		for(int i=0; i<4; i++) {
			d = (d+3)%4;
			int ny = y+dy[d];
			int nx = x+dx[d];
			if(isInside(ny, nx) && notCleaned(ny,nx))
			{
				ans ++;
				cleanRobotMove(ny,nx,d);
				return;
			}
		}
		
		
		//2. 현재 칸의 주변 4칸중 청소되지 않은 빈 칸이 없는 경우 -> 바라보는 방향을 유지한 채로 한칸 후진.
		int rear = (d+2)%4;
		int ry = y +dy[rear];
		int rx = x +dx[rear];
		if(isInside(ry, rx)&& notWall(ry,rx))cleanRobotMove(ry, rx, d);
	
	}
	private static boolean notCleaned(int ny, int nx) {
		if(map[ny][nx]==0) return true;
		return false;
	}
	private static boolean isInside(int ny, int nx) {
		if(ny>=0 && ny<N && nx>=0 && nx<M )return true;
		return false;
	}
	private static boolean notWall(int ny, int nx) {
		if(map[ny][nx]!=1) return true;
		// TODO Auto-generated method stub
		return false;
	}
	private static void print(int[][] m) {
		System.out.println();
		for (int i = 0; i < m.length; i++) {
			for(int j=0; j<m[i].length; j++) {
				System.out.print(m[i][j]+" ");
			}
			System.out.println();
		}
	}
	
}