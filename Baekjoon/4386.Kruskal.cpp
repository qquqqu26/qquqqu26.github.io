//4386.���ڸ� �����
//1. Kruskal's Algorithm, UnionFind
#include <iostream>
#include <tuple>
#include <vector>
#include <algorithm>
#include <cmath>
using namespace std;

const int maxVertexSize = 101; //�ִ� ���� ����
const int maxEdgeSize = 4951; //�ִ� ���� ����

int vertex = 1;
int edge, connectedEdge = 0; //��ü ������, ����� ����....
double weight = 0;
pair<double,double> coordinate[maxVertexSize]; //��ǥ ����
tuple<double,int,int> edgeList[maxEdgeSize]; //���� ����(����ġ, ����1, ����2)

vector<int> groupInfo(maxVertexSize, -1); //������ �� �׷쿡 ���� ����, -1�� �ʱ�ȭ

//����v�� �Ҽӵ� �׷�(���� root) ã�� �Լ�
int findRoot(int v) {

	if (groupInfo[v] < 0) //��Ʈ�� ���
		return v;

	//��������� ���� �׷�(��Ʈ) ã�� & ��� ����ȭ
	return groupInfo[v] = findRoot(groupInfo[v]);
}

//���� 2��u, v�� ���� �׷����� ���յǾ����� üũ(��ȯ) ��, �׷� ���ϴ� �Լ�
bool uni(int u, int v, int edge) {
	u = findRoot(u);
	v = findRoot(v);

	if (u == v) //���� �׷��̸�
		return 0; //���� ���ʿ�

	else { //�ٸ� �׷��̸�
		groupInfo[v] = u; //����
		weight += get<0>(edgeList[edge]);//�Ÿ� ������Ʈ
		connectedEdge++;
		//cout << "����: " << u << " " << v << " " << weight << endl;
		return 1;
	}
}

void kruskalMST() {
	for (int curEdge = 1; curEdge <= edge; curEdge++) {
		if ( connectedEdge == vertex - 1) {
			return;
		}

		int vertex1 = get<1>(edgeList[curEdge]);
		int vertex2 = get<2>(edgeList[curEdge]);
		
		//cout << "�Է� �Ǵ� �Ÿ�: " << get<0>(edgeList[current]) << endl;
		uni(vertex1, vertex2, curEdge);
	}
}


int main() {

	ios::sync_with_stdio(0);
	cin.tie(0);

	//�Է�
	cin >> vertex;

	for (int curVertex = 1; curVertex <= vertex; curVertex++) {
		double curX, curY; 
		cin >> curX >> curY;
		coordinate[curVertex] = { curX, curY };


		//�������� ���� ����
		if (curVertex > 1) {
			for (int vt = 1; vt < curVertex; vt++) {
				double x = coordinate[vt].first;
				double y = coordinate[vt].second;

				//�Ÿ� ���
				//double width = abs(x-curX);
				//double height = abs(y-curY);
				double distance = sqrt(pow( abs(x - curX) , 2) + pow( abs(y - curY) , 2));

				edgeList[++edge] = make_tuple(distance, vt, curVertex); //���� ���� ����

			}
		}

	}


	//���� ������ ����ġ �������� �������� ����
	sort(edgeList + 1, edgeList + edge + 1);

	//�׷��� Ž��
	kruskalMST();

	//�Ҽ��� 2�ڸ�����
	cout << fixed;
	cout.precision(2);

	cout << weight;

}