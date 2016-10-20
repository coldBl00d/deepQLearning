#include <iostream>
#include <vector>
#include <cstdlib>
#include <map>
using namespace std;
#define X 1
#define O 2
#define SIZE 3
#define ALPHA 0.5
bool choose(double prob){
	int a=prob*10;
	int temp=rand()%10;
	// cout<<a<<"**"<<temp<<endl;
	if(temp<a)
		return true;
	return false;
}
double prob=0.1;
class Board
{
	vector<vector<int>> b;
	int n,lastI,lastJ;
	bool turn;
public:
	Board(int n){
		turn=rand()%2;
		b = vector<vector<int>> (n,vector<int>(n,0));
		this->n=n;
	}
	Board(const Board &x){
		n=x.n;
		turn=x.turn;
		for(int i=0;i<n;i++){
			vector<int> z;
			for(int j=0;j<n;j++)
				z.push_back(x.b[i][j]);
			b.push_back(z);
		}
	}
	bool isTurn(int mark){
		if((mark==X&&turn)||(mark==O&&!turn))
			return true;
		return false;
	}
	int getState(){
		int s=0;
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				s=s*10+b[i][j];
		return s;
	}
	int getN(){return n;}
	void clear(){
		turn=rand()%2;
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				b[i][j]=0;
	}
	bool set(int i,int j,int mark){
		if((mark==X&&!turn)||(mark==O&&turn))
			return 0;
		if(b[i][j]!=0)
			return 0;
		b[i][j]=mark;
		lastI=i;
		lastJ=j;
		turn=!turn;
		return 1;
	}
	bool unSet(){
		b[lastI][lastJ]=0;
		turn=!turn;
	}
	bool isGameOver(){
		if(hasWon(X))
			return true;
		if(hasWon(O))
			return true;
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				if(b[i][j]==0)
					return false;
		return true;
	}
	bool hasWon(int mark){
		for(int i=0;i<n;i++){
			int j;
			for(j=0;j<n;j++)
				if(b[i][j]!=mark)
					break;
			if(j>=n)
				return true;
		}
		for(int i=0;i<n;i++){
			int j;
			for(j=0;j<n;j++)
				if(b[j][i]!=mark)
					break;
			if(j>=n)
				return true;
		}
		int i;
		for(i=0;i<n;i++)
			if(b[i][i]!=mark)
				break;
		if(i>=n)
			return true;
		for(i=0;i<n;i++)
			if(b[i][n-i-1]!=mark)
				break;
		if(i>=n)
			return true;
		return false;
	}
	void print(){
		for(int i=0;i<n;i++){
			cout<<"|";
			for(int j=0;j<n;j++)
				if(b[i][j]==0)
					cout<<"   |";
				else{
					char c=(b[i][j]==1)?'X':'O';
					cout<<" "<<c<<" |";
				}
			cout<<endl<<"-";
			for(int j=0;j<n;j++)
				cout<<"----";
			cout<<endl;
		}
	}
};
class QPlayer
{
	int mark,mark2;
	map<int,int*> qmap;
public:
	QPlayer(int mark){
		this->mark=mark;
		mark2=(mark==1)?2:1;
	}
	void play(Board &b){
		int s=b.getState();
		pair<int,int> *action;
		
		if(qmap.find(s)==qmap.end()){
			qmap[s]=new int[b.getN()*b.getN()];
			for(int i=0;i<b.getN()*b.getN();i++)
				qmap[s][i]=rand()%101;
		}
		if(choose(prob)){
			//max q
			int max=-400;
			for(int i=0;i<b.getN();i++)
				for(int j=0;j<b.getN();j++)
					if(qmap[s][i*b.getN()+j]>max){
						max=qmap[s][i*b.getN()+j];
						action=new pair<int,int>(i,j);
					}
		}else{
			
			int l=rand()%b.getN(),m=rand()%b.getN();
			while(!b.set(l,m,mark)){
				l=rand()%b.getN();
				m=rand()%b.getN();
			}
			b.unSet();
			action=new pair<int,int>(l,m);
		}
		int reward=r(b,action);
		b.set(action->first,action->second,mark);
		int qmax=-400;
		for(int i=0;i<b.getN();i++)
			for(int j=0;j<b.getN();j++){
				if(b.set(i,j,mark2)){
					int ns=b.getState();
					if(qmap.find(ns)==qmap.end()){
						qmap[ns]=new int[b.getN()*b.getN()];
						for(int z=0;z<b.getN()*b.getN();z++)
							qmap[ns][i]=rand()%101;
					}
					for(int z=0;z<b.getN()*b.getN();z++)
						if(qmap[ns][z]>qmax)
							qmax=qmap[ns][z];
					b.unSet(); 
				}
			}
		qmap[s][action->first*b.getN()+action->second]=qmap[s][action->first*b.getN()+action->second]*(1-ALPHA)+ALPHA*(reward+qmax);
	}
	int r(Board b,pair<int,int>* action){
		if(!b.set(action->first,action->second,mark))
			return -200;
		if(b.hasWon(mark))
			return 100;
		for(int i=0;i<b.getN();i++)
			for(int j=0;j<b.getN();j++)
				if(b.set(i,j,mark2)){
					if(b.hasWon(mark2))
						return -100;
					b.unSet();
				}
		return 0;
	}
};
int main(int argc, char const *argv[])
{
	Board b(SIZE);
	QPlayer x(X);
	QPlayer o(O);
	for(int i=0;i<10;i++){
		for(int j=0;j<1000;j++){
			b.clear();
			while(1){
				if(b.isTurn(X)){
					x.play(b);
					if(b.hasWon(X)){
						cout<<"X wins!!!"<<endl;
						break;
					}
					else if(b.isGameOver()){
						cout<<"Draw!!!"<<endl;
						break;
					}
					// cout<<"**"<<endl;
				}else{
					o.play(b);
					if(b.hasWon(O)){
						cout<<"O wins!!!"<<endl;
						break;
					}
					else if(b.isGameOver()){
						cout<<"Draw!!!"<<endl;
						break;
					}
					// cout<<"**"<<endl;
				}
			}
		}
		if(i<9)
			prob+=0.1;
	}
	return 0;
}