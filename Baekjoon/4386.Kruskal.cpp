//4386.별자리 만들기
//1. Kruskal's Algorithm, UnionFind
#include <iostream>
#include <tuple>
#include <vector>
#include <algorithm>
#include <cmath>
using namespace std;

const int maxVertexSize = 101; //최대 정점 개수
const int maxEdgeSize = 4951; //최대 간선 개수

int vertex = 1;
int edge, connectedEdge = 0; //전체 간선과, 저장된 간선....
double weight = 0;
pair<double,double> coordinate[maxVertexSize]; //좌표 정보
tuple<double,int,int> edgeList[maxEdgeSize]; //간선 정보(가중치, 정점1, 정점2)

vector<int> groupInfo(maxVertexSize, -1); //정점과 그 그룹에 대한 벡터, -1로 초기화

//정점v가 소속된 그룹(최종 root) 찾는 함수
int findRoot(int v) {

	if (groupInfo[v] < 0) //루트인 경우
		return v;

	//재귀적으로 최종 그룹(루트) 찾기 & 경로 최적화
	return groupInfo[v] = findRoot(groupInfo[v]);
}

//정점 2개u, v가 같은 그룹으로 병합되었는지 체크(반환) 후, 그룹 합하는 함수
bool uni(int u, int v, int edge) {
	u = findRoot(u);
	v = findRoot(v);

	if (u == v) //같은 그룹이면
		return 0; //병합 불필요

	else { //다른 그룹이면
		groupInfo[v] = u; //병합
		weight += get<0>(edgeList[edge]);//거리 업데이트
		connectedEdge++;
		//cout << "연결: " << u << " " << v << " " << weight << endl;
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
		
		//cout << "입력 되는 거리: " << get<0>(edgeList[current]) << endl;
		uni(vertex1, vertex2, curEdge);
	}
}


int main() {

	ios::sync_with_stdio(0);
	cin.tie(0);

	//입력
	cin >> vertex;

	for (int curVertex = 1; curVertex <= vertex; curVertex++) {
		double curX, curY; 
		cin >> curX >> curY;
		coordinate[curVertex] = { curX, curY };


		//정점마다 간선 생성
		if (curVertex > 1) {
			for (int vt = 1; vt < curVertex; vt++) {
				double x = coordinate[vt].first;
				double y = coordinate[vt].second;

				//거리 계산
				//double width = abs(x-curX);
				//double height = abs(y-curY);
				double distance = sqrt(pow( abs(x - curX) , 2) + pow( abs(y - curY) , 2));

				edgeList[++edge] = make_tuple(distance, vt, curVertex); //간선 정보 저장

			}
		}

	}


	//간선 정보를 가중치 기준으로 오름차순 정렬
	sort(edgeList + 1, edgeList + edge + 1);

	//그래프 탐색
	kruskalMST();

	//소수점 2자리수로
	cout << fixed;
	cout.precision(2);

	cout << weight;

}