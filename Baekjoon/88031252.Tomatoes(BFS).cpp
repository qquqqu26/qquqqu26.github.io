//Backjoon 88031252��. 
//���ͺ��� ť�� ����ϴ� �� �ո���: �ݿ� �Ϸ�
#include <iostream>;
#include <queue>
using namespace std;

int days = 0;
int col, row;
const int maxSize = 1001;
int tomatoMatrix[maxSize][maxSize];
queue<pair<int, int>> ripeTomatoes; //���� �丶�� �迭
int unripeTomatoes = 0; //������ �丶�� ����
pair<int, int> direction[4] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

void bfs() {

	while (!ripeTomatoes.empty()) {
		
		int queueSize = ripeTomatoes.size(); //���ϱ� �� ���� ������ ���
		
		//�Ϸ� ������ �丶�� üũ
		for (int checkedTomatoes = 0; checkedTomatoes < queueSize; checkedTomatoes++) {
			int ripeRow = ripeTomatoes.front().first;
			int ripeCol = ripeTomatoes.front().second;

			//�����¿� ������ ����
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

	//�丶�� 2���� �迭 �� �Է�
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			cin >> tomatoMatrix[i][j];

			//���� �丶�� ť�� �߰�, ������ �丶�� �� ����
			if (tomatoMatrix[i][j] == 1) {
				ripeTomatoes.push({ i, j });

			}
			else if (tomatoMatrix[i][j] == 0) {
				unripeTomatoes++;
			}
		}
	}

	bfs();

	//��� ����
	if (unripeTomatoes == 0)
		cout << --days << endl;
	else cout << -1 << endl;
}