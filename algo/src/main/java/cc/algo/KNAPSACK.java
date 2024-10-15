package cc.algo;

import java.util.HashMap;
import java.util.Map;

public class KNAPSACK {

  final private int _M; // weight limit
  
  final private int _n; // number of items

  final private int[] _V; // value per item

  final private int[] _W; // weight per item

  final int[] _default = new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE, 0 }; // value, weight, number
  
  String _solution = "";

  private Map<String, int[]> _memory = new HashMap<>();
  
  public KNAPSACK(int M, int n, int[] V, int[] W) {

    _M = M;
    
    _n = n;
        
    _V = V;
    
    _W = W;
  }
  
  public int M() {
    
    return _M;
  }
  
  public int n() {
    
    return _n;
  }

  public String V() {
    
    return Common.fold(_V);
  }
  
  public String W() {
    
    return Common.fold(_W);
  }
  
  public String solution() {
    
    return _solution;
  }

  public boolean solve() {

    int[] optimal = valuation(0, _n);

    _solution = "(value, weight, count) = (" + optimal[0] + "," + optimal[1] + "," + optimal[2] + ")";
    
    return optimal[0] != Integer.MIN_VALUE && optimal[1] != Integer.MAX_VALUE;
  }

  public int[] valuation(int i, int j) {

    String key = "" + i + "-" + j;
       
    if (_memory.containsKey(key)) {
      
      return _memory.get(key);
    }

    if (i == j) {
    
      int[] value = new int[] { 0, 0, 0 };
      
      if (value[1] > _M) {
      
        value = _default;       
      } 
            
      _memory.put(key, value);
      
      return value;
    }

    if ((j-i) == 1) {
            
      int[] value = new int[] { _V[i], _W[i], 1 };

      if (value[1] > _M) {
        
        value = _default;       
      } 

      _memory.put(key, value);

      return value;
    }

    int[] result = _default;
    
    for (int k = i; k < j; k++) {

      int[] lhs = valuation(i, k);
      
      int[] rhs = valuation(k + 1, j);

      _memory.put("" + i + "-" + k, lhs);

      _memory.put("" + (k+1) + "-" + j, rhs);

      if (lhs[1] <= _M && rhs[1] <= _M) {

        if ((lhs[1] + rhs[1]) <= _M &&
            (lhs[0] + rhs[0]) > result[0]) {

          result = new int[] { lhs[0] + rhs[0], lhs[1] + rhs[1], lhs[2] + rhs[2]};
        }
      } 
    }
    
    return result;
  }
  
}
