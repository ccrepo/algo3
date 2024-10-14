package cc.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LCS {

  final private int[] _rows;

  final private int[] _cols;

  final private int[] _C;

  final private int[] _S;
  
  final private int _height;

  final private int _width;
  
  private String _solution = "";

  private enum DIRECTION { DIRECTION_UP, DIRECTION_LEFT, DIRECTION_DIAG }
  
  public LCS(int[] rows, int[] cols) {

    _rows = rows;

    _cols = cols;

    _height = _rows.length;

    _width = _cols.length;

    _C = new int[_height * _width];
    
    _S = new int[_height * _width];
  }
  
  public String solution() {
    
    return _solution;
  }
  
  public int height() {
    
    return _rows.length;
  }

  public int width() {
    
    return _cols.length;    
  }

  public String rows() {

    return Common.fold(_rows);
  }

  public String cols() {

    return Common.fold(_cols);
  }

  private boolean same(int row, int col) {

    if (row > _height ||
        col > _width ||
        row <= 0 ||
        col <= 0) {
      
      throw new IllegalArgumentException("bad arguments");
    }
    
    return _rows[row - 1] == _cols[col - 1];
  }

  public boolean solve() {

    for (int i = 0; i < _C.length; i++) {

      int[] cell = itoC(i);
      
      if(!same(cell[0], cell[1])) {
        
        if (value(up(cell)) > value(left(cell))) {
        
          _C[i] = value(up(cell));
          
          _S[i] = DIRECTION.DIRECTION_UP.ordinal();
          
          System.out.println("cell (" + cell[0] + "," + cell[1] + ") set from UP");
          
        } else {
          
          _C[i] = value(left(cell));
          
          _S[i] = DIRECTION.DIRECTION_LEFT.ordinal();

          System.out.println("cell (" + cell[0] + "," + cell[1] + ") set from LEFT");
        }
        
      } else {
        
        int value = value(diag(cell));
       
        _C[i] = value + 1;
        
        _S[i] = DIRECTION.DIRECTION_DIAG.ordinal();
        
        System.out.println("cell (" + cell[0] + "," + cell[1] + ") set from DIAG");
      }
    }
    
    return backtrack();
  }

  private int[] up(int[] cell) {
    
    return new int[] {cell[0]-1, cell[1]};
  }

  private int[] left(int[] cell) {
    
    return new int[] {cell[0], cell[1]-1};
  }

  private int[] diag(int[] cell) {
    
    return new int[] {cell[0]-1,cell[1]-1};
  }

  public int ctoI(int row, int col) {

    return ((row - 1) * _width) + col - 1;
  }

  private int value(int[] cell) {
    
    if (cell[0] > _height ||
        cell[1] > _width ||
        cell[0] <= 0 ||
        cell[1] <= 0) {
      
      return 0;
    }
    
    int index = ctoI(cell[0], cell[1]);
    
    return _C[index];
  }

  private int cvalue(int[] cell) {
    
    if (cell[1] > _width ||
        cell[1] <= 0) {
      
      throw new IllegalArgumentException("bad arguments");
    }

    return _cols[cell[1]-1];
  }

  public int[] itoC(int index) {

    int row = (index / _width) + 1;
    
    int col = (index % _width) + 1;

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

  public String gridC() {

    StringBuilder buffer = new StringBuilder();

    buffer.append("{");

    buffer.append("\n");

    return buffer.toString() + grid(_C);
  }
  
  public String gridS() {

    StringBuilder buffer = new StringBuilder();

    buffer.append("{");

    buffer.append("\n");

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

      if (i % _width == 0 && i != 0) {

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

  private boolean backtrack() {
    
    int[] cell = itoC(_S.length-1);

    ArrayList<Integer> buffer = new ArrayList<>();

    boolean result = backtrack(cell, buffer);
    
    Collections.reverse(buffer);
    
    _solution = buffer.toString();
    
    return result;    
  }
  
  private boolean backtrack(int[] cell, ArrayList<Integer> buffer) {
    
    int index = ctoI(cell[0], cell[1]);
    
    if (_S[index] == DIRECTION.DIRECTION_LEFT.ordinal()) {
    
      int[] left_cell = left(cell);

      if (!off(left_cell)) {

        System.out.println("back (" + cell[0] + "," + cell[1] + ") to LEFT");

        return backtrack(left_cell, buffer);
      }
    
      return true;
    }
    
    if (_S[index] == DIRECTION.DIRECTION_UP.ordinal()) {
      
      int[] up_cell = up(cell);

      if (!off(up_cell)) {

        System.out.println("back (" + cell[0] + "," + cell[1] + ") to UP");

        return backtrack(up_cell, buffer);
      }

      return true;
    }
    
    if (_S[index] == DIRECTION.DIRECTION_DIAG.ordinal()) {
       
      buffer.add(cvalue(cell));
      
      int[] diag_cell = diag(cell);
      
      if (!off(diag_cell)) {

        System.out.println("back (" + cell[0] + "," + cell[1] + ") to DIAG");

        return backtrack(diag_cell, buffer);
      }
      
      return true;
    }
    
    return false;
  }
  
  private boolean off(int[] cell) {
    
    return cell[0] <= 0 || cell[1] <= 0;
  }
}
