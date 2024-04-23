import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
//예술성 평가
	//1-10
	//그룹 a와 b의 조화로움 = (칸수1+ 칸수2)*숫자1*숫자2*변의수
	//todo 1. 칸수 구하기
	//todo 2. 변의 수 구하기
	//십자 반시계 회전
	//개별적 90도 시계회전
	//ab bc bd cd -> 조화로움 값 더하면 초기 예술점수
	//맞닿아있는 것만 조화로움을 구할 수 있음
	//12 23 24 34 
	//4C2 = 6  12 (13 14) 23 24 34
	//초기예술점수1.2.3. 회전 후 예술점수 합 구하라
	static int N;//N은 항상 홀수
	static int[][] map;
	static int[][] gmap;
	static boolean visited[][];
	static int score;
	
	static int[]count = new int[200];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		gmap = new int[N][N];
		visited = new boolean[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int sum = 0;
		makeGroup();
		getScore();
		//System.out.println(score);
		for(int tc=0; tc<3; tc++) {
			rotate1();//십자 반시계 회전
			rotate2();//개별적 90도 시계회전
			makeGroup();
			getScore();
			
		}
		System.out.println(score);
	}
	
	
	private static void rotate2() {
		 int[][] tempMap = new int[N / 2][N / 2];
	        int[][] rotated = new int[N / 2][N / 2];

	        for(int i=0; i<N/2; i++)
	            for(int j=0; j<N/2; j++)
	                tempMap[i][j] = map[i][j];
	        for(int i=0; i<N/2; i++)
	            for(int j=0; j<N/2; j++){
	                rotated[i][j] = tempMap[N/2 - 1 - j][i];
	                map[i][j] = rotated[i][j];
	            }

	        for(int i=0; i<N/2; i++)
	            for(int j=N/2+1; j<N; j++)
	                tempMap[i][j - (N/2 + 1)] = map[i][j];
	        for(int i=0; i<N/2; i++)
	            for(int j=N/2+1; j<N; j++){
	                rotated[i][j - (N/2 + 1)] = tempMap[N/2 - 1 - (j - (N/2 + 1))][i];
	                map[i][j] = rotated[i][j - (N/2 + 1)];
	            }

	        for(int i=N/2+1; i<N; i++)
	            for(int j=0; j<N/2; j++)
	                tempMap[i - (N/2+1)][j] = map[i][j];
	        for(int i=N/2+1; i<N; i++)
	            for(int j=0; j<N/2; j++){
	                rotated[i - (N/2+1)][j] = tempMap[N/2 - 1 - j][i - (N/2+1)];
	                map[i][j] = rotated[i - (N/2+1)][j];
	            }

	        for(int i=N/2+1; i<N; i++)
	            for(int j=N/2+1; j<N; j++)
	                tempMap[i - (N/2+1)][j - (N/2+1)] = map[i][j];
	        for(int i=N/2+1; i<N; i++)
	            for(int j=N/2+1; j<N; j++){
	                rotated[i - (N/2+1)][j - (N/2+1)] = tempMap[N/2 - 1 - (j - (N/2 + 1))][i - (N/2+1)];
	                map[i][j] = rotated[i - (N/2+1)][j - (N/2+1)];
	            }
	}


	private static void rotate1() {
		int[][] tempMap = new int[N][N];
        for(int i=0; i<N; i++)
            for(int j=0; j<N; j++)
                tempMap[i][j] = map[i][j];
        
        for(int i=0; i<N/2; i++) // 왼
            tempMap[N / 2][i] = map[i][N / 2];
        for(int i=N/2+1; i<N; i++) // 오
            tempMap[N / 2][i] = map[i][N / 2];
        for(int i=0; i<N/2; i++) // 위
            tempMap[i][N / 2] = map[N / 2][N - 1 - i];
        for(int i=N/2+1; i<N; i++) // 아
            tempMap[i][N / 2] = map[N / 2][N - 1 - i];

        for(int i=0; i<N; i++)
            for(int j=0; j<N; j++)
                map[i][j] = tempMap[i][j];
        //System.out.println("after crossRotate");
        //print(map);
	}


	static ArrayList<Integer> sel = new ArrayList<>();
	
	private static int getScore() {
		ArrayList<Integer> arr = new ArrayList<>();
		for(int i=0; i<200; i++) {
			if(count[i]!=0)
				arr.add(i);
		}
		recursive(0, 0, new int [2], arr);
		return 0;
	}

	private static void recursive(int idx, int k,int[] sel, ArrayList<Integer> arr) {
		if(k==2) {
			
			//System.out.println(Arrays.toString(sel));
			int a = sel[0];
			int b = sel[1];
			int sr = 0,sc=0;
			int tr=0,tc=0;
			int temp = 0; //변
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					for(int d=0; d<4; d++) {
						
						int nr = i+dr[d];
						int nc = j+dc[d];
						if(check(nr,nc))continue;
						if(gmap[nr][nc]==b && gmap[i][j]==a) {
							temp++;
							sr = i;sc=j;
							tr = nr;tc=nc;
						}
						
					}
				}
			}
			//System.out.println(temp);
			if(temp==0)return;
//			System.out.println("a"+" " +count[a]+"b "+count[b]);
			score += (count[a]+count[b])*map[sr][sc]*map[tr][tc]*temp;
			//System.out.println("score:"+score);
			
			
			return;
		}
		if(idx == arr.size())return;
		sel[k]= arr.get(idx); 
		recursive(idx+1,k+1,sel,arr);
		recursive(idx+1,k,sel,arr);
		
	}
	 

	private static int makeGroup() {
		visited = new boolean[N][N];
		gmap = new int[N][N];
		Arrays.fill(count, 0);
		num=1;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(!visited[i][j]) {
					getGroupCount(i,j);
					num++;
				}
			}
		}
		for(int i=0; i<200; i++) {
			if(count[i]!=1 && count[i]!=0)count[i]--;
		}
		//print(gmap);
		//System.out.println(Arrays.toString(count));
		return 0;
	}
	static int num =1;
	static int[] dr = {0,0,1,-1};
	static int[] dc = {1,-1,0,0};
	private static void getGroupCount(int r, int c) {
//		int cnt =1;
		gmap[r][c]=num;
		count[num]++;
		ArrayDeque<int[]> adq = new ArrayDeque<>();
		adq.add(new int[] {r,c});
		while(!adq.isEmpty()) {
			int[] cur = adq.poll();
			int cr = cur[0];
			int cc = cur[1];
			int cn = map[r][c];
			for(int i=0; i<4; i++) {
				int nr = cr+dr[i];
				int nc = cc+dc[i];
				if(check(nr,nc))continue;
				if(visited[nr][nc])continue;
				if(cn == map[nr][nc]) {
					visited[nr][nc]=true;
					adq.add(new int[] {nr,nc});
					gmap[nr][nc] = num;
					count[num]++;
				}
			}
		}
		
		
	}
	private static boolean check(int nr, int nc) {
		if(nr<0||nr>=N||nc<0||nc>=N)return true;
		return false;
	}
	private static void print(int[][] m) {
		System.out.println();
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m[i].length; j++) {
				System.out.print(m[i][j]+ " ");
			}
			System.out.println();
		}
		
	}
}