//Backjoon 88031252번. 
//벡터보단 큐를 사용하는 게 합리적: 반영 완료
#include <iostream>;
#include <queue>
using namespace std;

int days = 0;
int col, row;
const int maxSize = 1001;
int tomatoMatrix[maxSize][maxSize];
queue<pair<int, int>> ripeTomatoes; //익은 토마토 배열
int unripeTomatoes = 0; //안익은 토마토 개수
pair<int, int> direction[4] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

void bfs() {

	while (!ripeTomatoes.empty()) {
		
		int queueSize = ripeTomatoes.size(); //변하기 전 숫자 변수에 담기
		
		//하루 단위로 토마토 체크
		for (int checkedTomatoes = 0; checkedTomatoes < queueSize; checkedTomatoes++) {
			int ripeRow = ripeTomatoes.front().first;
			int ripeCol = ripeTomatoes.front().second;

			//상하좌우 익히는 과정
			for (pair<int, int> dir : direction) {
				int srow = ripeRow + dir.first;
				int scol = ripeCol + dir.second;

				if (srow >= 0 && scol >= 0 && srow < row && scol < col && tomatoMatrix[srow][scol] == 0) {

					tomatoMatrix[srow][scol] = 1;
					unripeTomatoes--;

					ripeTomatoes.push({ srow, scol });
				}
			}

			ripeTomatoes.pop();
		}
		days++;
	}
}

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(NULL);

	cin >> col >> row;

	//토마토 2차원 배열 값 입력
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			cin >> tomatoMatrix[i][j];

			//익은 토마토 큐에 추가, 안익은 토마토 수 세기
			if (tomatoMatrix[i][j] == 1) {
				ripeTomatoes.push({ i, j });

			}
			else if (tomatoMatrix[i][j] == 0) {
				unripeTomatoes++;
			}
		}
	}

	bfs();

	//결과 보기
	if (unripeTomatoes == 0)
		cout << --days << endl;
	else cout << -1 << endl;
}