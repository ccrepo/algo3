package cc.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MCP {

  final private int[] _inputs;

  private int _n;

  private int[] _C;

  private int[] _S;

  private int[] _targets;
  
  private String _solution = "";

  public MCP(int[] inputs) {

    _inputs = inputs;

    if (_inputs.length <= 1) {

      return;
    }

    _n = _inputs.length - 1;

    _C = new int[_n * _n];

    _S = new int[_n * _n];

    _targets = setup_targets();
  }

  public boolean solve() {

    if (_inputs.length <= 0) {

      _solution = "()";

      return true;
    }

    if (_inputs.length == 1) {

      _solution = "(" + _inputs[0] + "x" + _inputs[0] + ")";

      return true;
    }

    if (_inputs.length == 2) {

      _solution = "(" + _inputs[0] + "x" + _inputs[1] + "." + _inputs[1] + "x" + _inputs[1] + ")";
      
      return true;
    }

    for (int i = 1; i <= _n; i++) {
      
      _C[ctoI(i, i)] = 0;
    }

    List<Integer> targets = new ArrayList<>();
    
    for (int L = 2; L <= _n; L++) {
      
      for (int i = 1; i <= (_n - L + 1); i++) {
        
        int j = i + L - 1;
        
        System.out.println("m[i,j] = m[" + i + "," + j + "]\t\t= " + Integer.MAX_VALUE);
        
        targets.add(ctoI(i, j));
        
        _C[ctoI(i, j)] = Integer.MAX_VALUE;
        
        for (int k = i; k <= (j - 1); k++) {
          
          int m1 = _C[ctoI(i, k)];

          int m2 = _C[ctoI(k + 1, j)];

          int p1 = _inputs[i - 1];

          int p2 = _inputs[k];

          int p3 = _inputs[j];

          int p = p1 * p2 * p3;
          
          int total = m1 + m2 + p;
          
          if (total < _C[ctoI(i, j)]) {
            
            //System.out.println("m1     = m[i,k]  \t= m[" + i + "," + k + "] = " + m1);
            
            //System.out.println("m2     = m[k+1,j]\t= m[" + (k+1) + "," + j + "] = " + m2);

            //System.out.println("p1     = " + p1);

            //System.out.println("p2     = " + p2);

            //System.out.println("p3     = " + p3);

            System.out.println("m[i,j] = m[" + i + "," + j + "]\t\t= " + total);
            
            _C[ctoI(i, j)] = total;
            
            _S[ctoI(i, j)] = k;
          }
        }
      }
    }

    targets.sort( (a, b) -> { return a.compareTo(b); } );

    if (!check(targets)) {
      
      System.out.println("check failed: " + targets.toString());     
      
      return false;
    }
    
    if (_inputs.length > 2) {

      if (!parenthesize()) {
        
        return true;
      }
    }
    
    return true;
  }

  private boolean check(List<Integer> targets) {
    
    if (_targets.length != targets.size()) {
      
      return false;
    }
    
    int[] arr = targets.stream().mapToInt(i -> i).toArray();

    for (int i = 0; i < arr.length; i++) {
      
      if (arr[i] != _targets[i]) {
        
        return false;
      }
    }
    
    return true;
  }
  
  public int n() {

    return _n;
  }

  public int count() {

    return _inputs.length;
  }

  public int[] setup_targets() {

    List<Integer> buffer = new ArrayList<Integer>();

    for (int i = 1; i <= _n; i++) {

      for (int j = i; j <= _n; j++) {

        if (i == j) {

          continue;
        }

        buffer.add(ctoI(i, j));
      }
    }

    return buffer.parallelStream().mapToInt(Integer::intValue).toArray();
  }

  public int ctoI(int row, int col) {

    return ((row - 1) * _n) + col - 1;
  }

  public int[] itoC(int index) {

    int row = (index / _n) + 1;

    int col = (index % _n) + 1;

    int[] buffer = new int[2];

    buffer[0] = row;

    buffer[1] = col;

    return buffer;
  }

  public String ctoS(int index) {

    StringBuilder buffer = new StringBuilder();

    int[] result = itoC(index);

    buffer.append("(");
    buffer.append(result[0]);
    buffer.append(",");
    buffer.append(result[1]);
    buffer.append(")");

    return buffer.toString();
  }

  public String solution() {

    return _solution;
  }

  private String grid() {

    StringBuilder buffer = new StringBuilder();

    buffer.append("{");

    buffer.append("\n");

    if (_inputs.length <= 0) {

      buffer.append("}");

      return buffer.toString();
    }

    if (_inputs.length == 1) {

      buffer.append("  1");

      buffer.append("\n");

      buffer.append("}");

      return buffer.toString();
    }

    if (_inputs.length == 2) {

      buffer.append("  0 1");

      buffer.append("\n");

      buffer.append("  0 0");

      buffer.append("\n");

      buffer.append("}");

      return buffer.toString();
    }

    return buffer.toString();
  }
  
  public String gridC() {

    StringBuilder buffer = new StringBuilder();

    buffer.append("{");

    buffer.append("\n");

    if (_inputs.length <= 2) {

      return grid();
    }

    return buffer.toString() + grid(_C);
  }
  
  public String gridS() {

    StringBuilder buffer = new StringBuilder();

    buffer.append("{");

    buffer.append("\n");

    if (_inputs.length <= 2) {

      return grid();
    }

    return buffer.toString() + grid(_S);
  }
  
  private String grid(int[] G) {

    StringBuilder buffer = new StringBuilder();

    boolean nl = true;

    int max = Arrays.stream(G).max().getAsInt();
    
    int padding = 1;
    
    if (max > 0) {
      
      padding = (int) Math.log10(max) + 1;
    }
    
    for (int i = 0; i < G.length; i++) {

      if (i % _n == 0 && i != 0) {

        buffer.append("\n");

        nl = true;
      }

      if (nl) {

        buffer.append("  ");
      }

      buffer.append(String.format("%" + Integer.toString(padding) + "d ", G[i]));
    }

    buffer.append("\n}");

    return buffer.toString();
  }

  public String inputs() {

    return Common.fold(_inputs);
  }

  public String targets() {

    if (_targets == null) {
    
      return "";
    }
    
    return Common.fold(_targets);
  }
    
  private boolean parenthesize() {
    
    int pivot = _S[ctoI(1, _n)];

    String[] result1 = new String[_n];

    List<Integer> result2 = new ArrayList<>();

    for (int i = 0; i < _n; i++) {
    
      //result1[i] = "A" + (i+1) + "[" + _inputs[i] + "x" + _inputs[i+1] + "]";
      result1[i] = "A" + (i+1);
    }
    
    result2.add(pivot);

    boolean result = parenthesize(1, pivot, result1, result2, "L", 1) && 
        parenthesize(pivot + 1, _n, result1, result2, "R", 1);
    
    StringBuilder buffer = new StringBuilder();
    
    for (int i = 0; i < _n; i++) {

      if (!buffer.isEmpty()) {
        
        buffer.append(" x ");
      }
      
      buffer.append(result1[i]);
    }
    
    _solution = buffer.toString();
    
    return result;
  }

  private boolean parenthesize(int i, int j, String[] result1, List<Integer> result2, String LR, int level) {

    if (i >= j) {
    
      return true;
    }

    result1[i-1] = "( " + result1[i-1];

    result1[j-1] = result1[j-1] + " )";

    if ((i+1) >= j) {
      
      return true;
    }

    int pivot = _S[ctoI(i, j)];
    
    result2.add(pivot);
    
    return parenthesize(i, pivot, result1, result2, LR + 'L', level + 1) && 
        parenthesize(pivot + 1, j, result1, result2, LR + 'R', level + 1);
  }

  public int minimum() {
    
    return _C[ctoI(1, _n)];
  }
}
