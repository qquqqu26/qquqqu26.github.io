//Baekjoon 1926. Pictures
//1-1. Dfs, Stack
//1-2. Recursion으로 풀 수 있음
#include <iostream>
#include <stack>
#include <algorithm>
using namespace std;

int sizeRow, sizeCol;
int picture[500][500];
int numOfPictures = 0;
int maxSize = 0;

stack<pair<int, int>> s;
pair<int, int> direction[4] = { {-1, 0}, {0, -1}, {0 , +1}, {+1, 0} };

int dfs(int row, int col) {
	s.push({ row, col });
	picture[row][col] = 0;

	int size = 1;
	int dirRow, dirCol;

	while (!s.empty()) {
		row = s.top().first;
		col = s.top().second;
		s.pop();

		for (pair <int, int> dir : direction) {

			dirRow = row + dir.first;
			dirCol = col + dir.second;

			if (dirRow > -1 && dirRow < sizeRow && dirCol > -1 && dirCol < sizeCol &&
				picture[dirRow][dirCol] == 1 ) {

				s.push({ dirRow, dirCol });
				picture[dirRow][dirCol] = 0;

				size++;
			}
		}
	}
	return size;
}

int main() {

	ios::sync_with_stdio(false);
	cin.tie(NULL);

	cin >> sizeRow >> sizeCol;

	for (int i = 0; i < sizeRow; i++) {
		for (int j = 0; j < sizeCol; j++) {
			cin >> picture[i][j];
		}
	}

	int before, after = 0;

	//탐색
	for (int i = 0; i < sizeRow; i++) {
		for (int j = 0; j < sizeCol; j++) {

			//그림 시작점 조건
			if (picture[i][j] == 1) {
				before = maxSize;
				after = dfs(i, j);
				maxSize = max(before, after);

				numOfPictures++;
			}
		}
	}

	cout << numOfPictures << endl;
	cout << maxSize << endl;

}