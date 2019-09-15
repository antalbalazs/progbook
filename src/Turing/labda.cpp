#include <unistd.h>
#include <iostream>
using namespace std;

void printXToPosInScreen(int x, int y, int w, int h, string str){
	system("clear");
	for(int i = 0; i < y; i++) cout<<endl;
	for(int i = 0; i < x; i++) cout<<" ";
	cout<<str<<endl;
	for(int i = y + 1; i < h; i++) cout << endl;
}
int main()
{
	int maxX = 80;
	int maxY = 30;
	int egyx = 1, egyy = -1, x = 1, y = 10;
	int ty[maxY], tx[maxX];

	for(int i=1; i < maxY; i++){
		ty[i]=1; ty[1]=-1; ty[maxY - 1]=-1;
	}

	for(int i=1; i < maxX; i++){
		tx[i]=1; tx[1]=-1; tx[maxX - 1]=-1;
	}

	while(true){
		printXToPosInScreen(x, y, maxX, maxY, "o");
		x += egyx;
		y += egyy;
		egyx *= tx[x];
		egyy *= ty[y];
		usleep(100000);
	}
	return 0;
}
