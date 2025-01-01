//Backjoon 88031252번. 
//벡터보단 큐를 사용하는 게 합리적: 반영 완료
//BSF알고리즘를 사용해야 했음
#include <iostream>;
#include <vector>
#include <queue>
using namespace std;

int days = 0;
vector<vector<int>> tomatoMatrix; //토마토 행렬
queue<pair<int, int>> ripeTomatoes; //익은 토마토 배열
int unripeTomatoes = 0; //안익은 토마토 개수

int main()
{
	int col, row;
	cin >> col >> row;

	//토마토 행렬 할당(?)
	vector<vector<int>> tomatoMatrix(row); //행
	for (int i = 0; i < row; i++) {
		tomatoMatrix[i] = vector<int>(col); //열
	}

	//토마토 행렬 값 입력
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			cin >> tomatoMatrix[i][j];

			//입력 받을 때, 미리 익은 토마토 벡터에 추가, 안익은 토마토수 세기
			if (tomatoMatrix[i][j] == 1) {
				ripeTomatoes.push({ i, j });

			}
			else if (tomatoMatrix[i][j] == 0) {
				unripeTomatoes++;
			}
		}
	}

	//익은 모든 토마토들이 다 영향력을 행사할 때까지
	while (!ripeTomatoes.empty())
	{
		int numOfTomatoesByDate = ripeTomatoes.size(); //임무가 남은 토마토
		int checkedTomatoes = 0; //임무를 다 한(?) 토마토

		while (checkedTomatoes != numOfTomatoesByDate) { // 익은 토마토 전부가 임무를 다 할 때까지
			int ripeRow = ripeTomatoes.front().first;
			int ripeCol = ripeTomatoes.front().second;


			//if로 예외처리: 범위 체크 && 안익은 토마토인지
			//상
			if ((ripeRow > 0) && (tomatoMatrix[ripeRow - 1][ripeCol] == 0)) 
			{
				tomatoMatrix[ripeRow - 1 ][ripeCol] = 1; //익음
				ripeTomatoes.push({ ripeRow - 1, ripeCol }); //익은 토마토로 분류
				unripeTomatoes--; //안익은 토마토수 감소
			}

			//좌
			if ((ripeCol > 0) && (tomatoMatrix[ripeRow][ripeCol - 1] == 0))
			{
				tomatoMatrix[ripeRow][ripeCol-1] = 1;
				ripeTomatoes.push({ ripeRow , ripeCol - 1 }); 
				unripeTomatoes--; 
			}

			//하
			if ((ripeRow < row - 1) && (tomatoMatrix[ripeRow + 1][ripeCol] == 0))
			{
				tomatoMatrix[ripeRow + 1][ripeCol] = 1;
				ripeTomatoes.push({ ripeRow + 1 , ripeCol });
				unripeTomatoes--;
			}

			//우
			if ((ripeCol < col - 1) && (tomatoMatrix[ripeRow][ripeCol + 1] == 0))
			{
				tomatoMatrix[ripeRow][ripeCol + 1] = 1;
				ripeTomatoes.push({ ripeRow , ripeCol + 1 });
				unripeTomatoes--;
			}


			ripeTomatoes.pop(); //제 몫을 한 토마토는 삭제
			checkedTomatoes++;
		}

		days++;
	}

	//결과 보기
	if (ripeTomatoes.empty() && unripeTomatoes == 0)
		cout << --days << endl;
	else cout << -1 << endl;
}