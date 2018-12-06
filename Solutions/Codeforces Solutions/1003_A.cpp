#include <bits/stdc++.h>
#include <bitset>

#define REP(i,n) for(int i=0;i<(int)n;i++)
#define REP1(i,j,n) for(int i=j;i<(int)n;i++)
#define all(x) x.begin(),x.end()
#define double long double
#define BUG cerr<<"BUG\n";

typedef long long ll;

using namespace std;

int main() {
    ios_base::sync_with_stdio(0); cin.tie(0);

    int n;
    cin>>n;
    unordered_map<int,int>mp;
    int mx=0;
    REP(i,n){
        int x;
        cin>>x;
        mp[x]++;
        mx=max(mx,mp[x]);
    }
    cout<<mx<<"\n";
    return 0;
}