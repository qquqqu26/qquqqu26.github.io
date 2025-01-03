//Baekjoon 1260. DFS&BFS
//1. DFS(Recursion)&BFS(Queue), Adjacent List
#include <iostream>
#include <queue>
#include <algorithm>
using namespace std;

int vertex, edge, start;
const int maxVertexSize = 1001;
bool visitedBFS[maxVertexSize] = { false };
bool visitedDFS[maxVertexSize] = { false };
vector <int> list[maxVertexSize];

void dfs(int startVertex) {
	//방문 처리
	cout << startVertex << " ";
	visitedDFS[startVertex] = true;

	//인접 리스트에서 가장 작은&&방문한적 없는 정점 탐색
	for (int i = 0; i < list[startVertex].size(); i++) {
		int next = list[startVertex][i];

		if (!visitedDFS[next]) {
			dfs(next);
		}
	}
}

void bfs(int startVertex) {

	queue<int> q;
	q.push(startVertex);
	visitedBFS[startVertex] = true;
	
	while (q.empty() != true) {
		int current = q.front();
		q.pop();
		cout << current << " ";

		for (int i = 0; i < list[current].size(); i++) {
			int next = list[current][i];

			if (visitedBFS[next] == false) {
				q.push(next);
				visitedBFS[next] = true;
			}
		}
	}

}

int main() {

	ios::sync_with_stdio(false);
	cin.tie(NULL); cout.tie(NULL);

	//입력
	cin >> vertex >> edge >> start;
	int vertex1, vertex2 = 0;
	for (int curEdge = 0; curEdge < edge; curEdge++) {

		cin >> vertex1 >> vertex2;

		list[vertex1].push_back( vertex2 );
		list[vertex2].push_back( vertex1 );
	}

	//정점에 대한 요소들 오름차순 정렬
	for (int i = 1; i < vertex+1; i++) {
		sort(list[i].begin(), list[i].end());
	}
	
	dfs(start);
	cout << "\n";
	bfs(start);
}