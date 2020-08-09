#include <bits/stdc++.h>
using namespace std;

bool isDone;
bool tour[65][65],vis[65][65];
int TARGET, n, _count;


void go(int i, int j){
    if(isDone) return;
    if(!(i>=0 && i<n && j>=0 && j<n)) return;
    if(tour[i][j]) return;
    _count++;
    tour[i][j] = true;
    if(_count == TARGET) {
        isDone = true;
        return;
    }
    go(i+1, j);
    go(i, j-1);
    go(i-1, j+1);
    _count--;
    tour[i][j] = false;
}
int main()
{
    n = 2;
    while(n <= 8){
        cout<<"n = "<<n<<endl<<endl;
        TARGET = n*n;
        isDone = false;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                go(i,j);
                //cout<<i<<", "<<j<< " = "<<isDone<<endl;
                if(isDone){
                    vis[i][j] = true;
                }
                isDone = false;
                _count = 0;
                for(int i=0;i<n;i++){
                    for(int j=0;j<n;j++){
                        tour[i][j] = false;
                    }
                }
            }
        }
        for(int j=n-1;j >= 0;j--){
            for(int i=0;i<n;i++){
                if(vis[i][j]){
                    printf("Y ");
                }else{
                    printf("N ");
                }
            }
            printf("\n");
        }
        printf("------------\n");
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                tour[i][j] = false;
                vis[i][j] = false;
            }
        }
        n++;

    }
}
