#include <iostream>
#include <cstdlib>
#include <vector>
#include <ctime>
#include <cmath>
#include <map>
using namespace std;
class TicTacToe{
public:
	int arr[10][10],n;
	bool turn;
	int getN(){return n;}
	TicTacToe(int n){
		this->n=n;
		srand(time(NULL));
		turn=rand()%2;
		// turn =1;
	}
	void set(pair<int,int> act,int move){
		if((turn&&move==1)||(!turn&&move==2)){
			arr[act.first][act.second]=move;
			turn=!turn;
		}
	}
	void clear(){
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				arr[i][j]=0;
		turn=rand()%2;
		// turn=1;
	}
	bool gameOver(){
		if(checkWinner(1)){
			cout<<1<<" wins!!!"<<endl;
			return true;
		}
		if(checkWinner(2)){
			cout<<2<<" wins!!!"<<endl;
			return true;
		}
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				if(arr[i][j]==0)
					return false;
		cout<<"Draw!!!"<<endl;
		return true;
	}
	bool checkWinner(int move){
		for(int i=0;i<n;i++){
			int j;
			for(j=0;j<n;j++)
				if(arr[i][j]!=move)
					break;
			if(j>=n)
				return true;
		}
		for(int i=0;i<n;i++){
			int j;
			for(j=0;j<n;j++)
				if(arr[j][i]!=move)
					break;
			if(j>=n)
				return true;
		}
		int i;
		for(i=0;i<n;i++)
			if(arr[i][i]!=move)
				break;
		if(i>=n)
			return true;
		for(i=0;i<n;i++)
			if(arr[i][n-i-1]!=move)
				break;
		if(i>=n)
			return true;
		return false;
	}
	void print(){
		for(int i=0;i<3;i++){
			cout<<"|";
			for(int j=0;j<3;j++)
				if(arr[i][j]==0)
					cout<<"   |";
				else
					cout<<" "<<arr[i][j]<<" |";
			cout<<endl<<"-------------"<<endl;
		}
	}
};
TicTacToe t(3);
class QPlayer{
	int move;
	map<int,int*> qmap;
public:
	QPlayer(int i){
		move=i;
	}
	void print(){
		for(auto it=qmap.begin();it!=qmap.end();it++){
			cout<<it->first<<" : ";
			for(int i=0;i<t.n*t.n;i++)
				cout<<(it->second)[i]<<" ";
			cout<<endl;
		}
	}
	void play(){
		// cout<<"**"<<qmap.size()<<endl;
		vector<pair<int,int>> actions;
		int max=-400;		
		for(int i=0;i<t.getN();i++){
			for(int j=0;j<t.getN();j++){
				int qua=q(t.arr,t.getN(),*(new pair<int,int>(i,j)));
				// cout<<i<<"**"<<j<<"**"<<qua<<endl;
				if(qua==max)
					actions.push_back(*(new pair<int,int>(i,j)));
				if(qua>max){
					actions.clear();
					actions.push_back(*(new pair<int,int>(i,j)));
					max=qua;
				}
			}
		}		

		pair<int,int> act=actions[rand()%(actions.size())];
		// for(int i=0;i<actions.size();i++)
		// 	cout<<actions[i].first<<"&&"<<actions[i].second<<endl;
		// cout<<actions.size()<<"**"<<act.first<<"**"<<act.second<<"**"<<max<<endl;
		t.set(act,move);

	}
	int q(int state[10][10],int n,pair<int, int> act){
		int s=0,sn=0;
		if(state[act.first][act.second]!=0){
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++)
					s=s*10+state[i][j];
			if(qmap.find(s)!=qmap.end())
				qmap[s][act.first*n+act.second]=-200;
			return -200;
		}
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				s=s*10+state[i][j];
				if(i==act.first&&j==act.second){
					sn=sn*10+move;
				}
				else
					sn=sn*10+state[i][j];
				// cout<<"++"<<sn<<endl;
			}
		// cout<<s<<"^^"<<act.first<<"^^"<<act.second<<"^^"<<sn<<endl;
		if(qmap.find(s)==qmap.end())
			qmap[s]=new int[n*n];
		qmap[s][act.first*n+act.second]=r(state,n,act);
		// cout<<sn<<"$$"<<endl;
		int max=0;
		for(int i=1;i<=n*n;i++){
			if((int)(sn/pow(10,n*n-i))%10!=0)
				continue;
			int m=0;
			if(move==1)
				m=2;
			else
				m=1;
			sn=sn+(m*pow(10,n*n-i));
			
			// cout<<sn<<"$$"<<endl;
			if(qmap.find(sn)!=qmap.end()){
				for(int i=0;i<n;i++)
					if(qmap[sn][i]>max)
						max=qmap[sn][i];
			}
			sn=sn-(m*pow(10,n*n-i));

		}
		qmap[s][act.first*n+act.second]+=(0.8*max);
		return qmap[s][act.first*n+act.second];
	}
	int r(int state[10][10],int n,pair<int,int> act){
		if(state[act.first][act.second]!=0)
			return -200;
		state[act.first][act.second]=move;
		if(checkWinner(state,n,move)){
			state[act.first][act.second]=0;
			return 100;
		}
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				if(state[i][j]!=0)
					continue;
				int m=(move==1)?2:1;

				state[i][j]=m;
				if(checkWinner(state,n,m)){
					state[i][j]=0;
					state[act.first][act.second]=0;
					return -100;
				}
				state[i][j]=0;
			}
		state[act.first][act.second]=0;
		return 0;

	}
	bool checkWinner(int state[][10],int n,int m){
		for(int i=0;i<n;i++){
			int j;
			for(j=0;j<n;j++)
				if(state[i][j]!=m)
					break;
			if(j>=n)
				return true;
		}
		for(int i=0;i<n;i++){
			int j;
			for(j=0;j<n;j++)
				if(state[j][i]!=m)
					break;
			if(j>=n)
				return true;
		}
		int i;
		for(i=0;i<n;i++)
			if(state[i][i]!=m)
				break;
		if(i>=n)
			return true;
		for(i=0;i<n;i++)
			if(state[i][n-i-1]!=m)
				break;
		if(i>=n)
			return true;
		return false;
	}
};
int main(){
	QPlayer p1(1),p2(2);
	int i=1000;
	while(i-->0){
		while(1){
			p1.play();
			//t.print();
			//cout<<endl;
			if(t.gameOver())
				break;
			p2.play();
			//t.print();
			//cout<<endl;
			if(t.gameOver())
				break;
		}
		t.clear();
	}
	while(1){
		t.clear();
		while(1){
			p1.play();
			t.print();
			cout<<endl;
			if(t.gameOver())
				break;
			int m,n;
			cin>>m>>n;
			t.set(*(new pair<int,int>(m,n)),2);
			t.print();
			cout<<endl;
			if(t.gameOver())
				break;
		}
	}
}
