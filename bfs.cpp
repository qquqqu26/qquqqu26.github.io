//Backjoon 88031252��. 
//���ͺ��� ť�� ����ϴ� �� �ո���: �ݿ� �Ϸ�
//BSF�˰��� ����ؾ� ����
#include <iostream>;
#include <vector>
#include <queue>
using namespace std;

int days = 0;
vector<vector<int>> tomatoMatrix; //�丶�� ���
queue<pair<int, int>> ripeTomatoes; //���� �丶�� �迭
int unripeTomatoes = 0; //������ �丶�� ����

int main()
{
	int col, row;
	cin >> col >> row;

	//�丶�� ��� �Ҵ�(?)
	vector<vector<int>> tomatoMatrix(row); //��
	for (int i = 0; i < row; i++) {
		tomatoMatrix[i] = vector<int>(col); //��
	}

	//�丶�� ��� �� �Է�
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			cin >> tomatoMatrix[i][j];

			//�Է� ���� ��, �̸� ���� �丶�� ���Ϳ� �߰�, ������ �丶��� ����
			if (tomatoMatrix[i][j] == 1) {
				ripeTomatoes.push({ i, j });

			}
			else if (tomatoMatrix[i][j] == 0) {
				unripeTomatoes++;
			}
		}
	}

	//���� ��� �丶����� �� ������� ����� ������
	while (!ripeTomatoes.empty())
	{
		int numOfTomatoesByDate = ripeTomatoes.size(); //�ӹ��� ���� �丶��
		int checkedTomatoes = 0; //�ӹ��� �� ��(?) �丶��

		while (checkedTomatoes != numOfTomatoesByDate) { // ���� �丶�� ���ΰ� �ӹ��� �� �� ������
			int ripeRow = ripeTomatoes.front().first;
			int ripeCol = ripeTomatoes.front().second;


			//if�� ����ó��: ���� üũ && ������ �丶������
			//��
			if ((ripeRow > 0) && (tomatoMatrix[ripeRow - 1][ripeCol] == 0)) 
			{
				tomatoMatrix[ripeRow - 1 ][ripeCol] = 1; //����
				ripeTomatoes.push({ ripeRow - 1, ripeCol }); //���� �丶��� �з�
				unripeTomatoes--; //������ �丶��� ����
			}

			//��
			if ((ripeCol > 0) && (tomatoMatrix[ripeRow][ripeCol - 1] == 0))
			{
				tomatoMatrix[ripeRow][ripeCol-1] = 1;
				ripeTomatoes.push({ ripeRow , ripeCol - 1 }); 
				unripeTomatoes--; 
			}

			//��
			if ((ripeRow < row - 1) && (tomatoMatrix[ripeRow + 1][ripeCol] == 0))
			{
				tomatoMatrix[ripeRow + 1][ripeCol] = 1;
				ripeTomatoes.push({ ripeRow + 1 , ripeCol });
				unripeTomatoes--;
			}

			//��
			if ((ripeCol < col - 1) && (tomatoMatrix[ripeRow][ripeCol + 1] == 0))
			{
				tomatoMatrix[ripeRow][ripeCol + 1] = 1;
				ripeTomatoes.push({ ripeRow , ripeCol + 1 });
				unripeTomatoes--;
			}


			ripeTomatoes.pop(); //�� ���� �� �丶��� ����
			checkedTomatoes++;
		}

		days++;
	}

	//��� ����
	if (ripeTomatoes.empty() && unripeTomatoes == 0)
		cout << --days << endl;
	else cout << -1 << endl;
}