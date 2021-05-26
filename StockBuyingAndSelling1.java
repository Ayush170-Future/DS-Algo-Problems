// Problem link: https://practice.geeksforgeeks.org/problems/stock-buy-and-sell-1587115621/1/?company[]=Amazon&company[]=Amazon&problemStatus=unsolved&problemType=functional&page=2&sortBy=submissions&query=company[]AmazonproblemStatusunsolvedproblemTypefunctionalpage2sortBysubmissionscompany[]Amazon#
// This is a functional code not the whole program
class StockBuyingAndSelling1{
    ArrayList<ArrayList<Integer>> stockBuySell(int a[], int n) {
        ArrayList<ArrayList<Integer>> profitList = new ArrayList<>();
        int bp = 0;
        int sp = 0;
        int profit = 0;
        for(int i = 1; i < n; i++) {
            if(a[i] >= a[i-1]) {
                sp++;
            } else if(a[i] < a[i-1]) {
                if(i != 1) {
                    ArrayList<Integer> trans = new ArrayList<>();
                    trans.add(bp);
                    trans.add(sp);
                    profit += a[sp] - a[bp];
                    profitList.add(trans);
                }
                sp++;
                bp = sp;
            }
        }
        if(bp != sp) {
            ArrayList<Integer> trans = new ArrayList<>();
            trans.add(bp);
            trans.add(sp);
            profitList.add(trans);
            profit += a[sp] - a[bp];
        }
        if(profit == 0) {
            ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
            return ans;
        }
        return profitList;
    }
}
