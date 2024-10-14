package cc.algo;

public class Runner {

  int[] _mcp0 = new int[] { 3, 4, 5, 6, 4 };

  int[] _mcp1 = new int[] { 5, 10, 3, 12, 5, 50, 6 };

  //
  
  int[] _lcs1_row = new int[] { 1, 0, 0, 1, 0, 1, 0, 1 };

  int[] _lcs1_col = new int[] { 0, 1, 0, 1, 1, 0, 1, 1, 0 };

  //

  int[] _lcs_nob1_row = new int[] { 1, 0, 0, 1, 0, 1, 0, 1 };

  int[] _lcs_nob1_col = new int[] { 0, 1, 0, 1, 1, 0, 1, 1, 0 };

  int[] _lcs_nob1_solution = new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1,  
      1, 1, 2, 2, 2, 2, 2, 2, 2,  
      1, 1, 2, 2, 2, 3, 3, 3, 3,  
      1, 2, 2, 3, 3, 3, 4, 4, 4,
      1, 2, 3, 3, 3, 4, 4, 4, 5, 
      1, 2, 3, 4, 4, 4, 5, 5, 5, 
      1, 2, 3, 4, 4, 5, 5, 5, 6, 
      1, 2, 3, 4, 5, 5, 6, 6, 6 };
  
  //
  
  int[] _k_V1 = new int[] { 3, 2, 60, 2 };

  int[] _k_W1 = new int[] { 2, 3, 6, 5};

  int _k_N1 = 4;
  
  int _k_M1 = 8;

  int[] _k_V2 = new int[] { 1, 2, 30, 4 };

  int[] _k_W2 = new int[] { 1, 12, 14, 13};

  int _k_N2 = 4;
  
  int _k_M2 = 16;
  
  //
  
  public void runMCP() {

    MCP mcp = new MCP(_mcp1);

    System.out.println("input:    " + mcp.inputs());

    if (!mcp.solve()) {

      System.out.println("status: failed");

    } else {

      System.out.println("targets:  " + mcp.targets());

      System.out.println("count:    " + mcp.count());

      System.out.println("n:        " + mcp.n());

      System.out.println("gridC:    " + mcp.gridC());

      System.out.println("gridS:    " + mcp.gridS());

      System.out.println("minimum:  " + mcp.minimum());

      System.out.println("solution: " + mcp.solution());

      System.out.println("status:   ok");
    }
  }

  public void runLCS() {

    LCS lcs = new LCS(_lcs1_row, _lcs1_col);

    System.out.println("rows:      " + lcs.rows());

    System.out.println("cols:      " + lcs.cols());

    System.out.println("height:    " + lcs.height());

    System.out.println("width:     " + lcs.width());

    if (!lcs.solve()) {

      System.out.println("status: failed");

    } else {

      System.out.println("gridC:    " + lcs.gridC());

      System.out.println("gridS:    " + lcs.gridS());
      
      System.out.println("solution: " + lcs.solution());

      System.out.println("status:   ok");
    }
  }

  public void runLCS_NOB() {

    LCS_NOB lcs_NOB = new LCS_NOB(_lcs_nob1_row, _lcs_nob1_col, _lcs_nob1_solution);

    System.out.println("rows:      " + lcs_NOB.rows());

    System.out.println("cols:      " + lcs_NOB.cols());

    System.out.println("height:    " + lcs_NOB.height());

    System.out.println("width:     " + lcs_NOB.width());

    System.out.println("gridC:     " + lcs_NOB.gridC());

    if (!lcs_NOB.solve()) {

      System.out.println("status: failed");

    } else {

      System.out.println("solution: " + lcs_NOB.solution());

      System.out.println("status:   ok");
    }
  }

  public void runKNAPSACK() {

    KNAPSACK k = new KNAPSACK(_k_M1, _k_N1, _k_V1, _k_W1);
 
    System.out.println("M:         " + k.M());

    System.out.println("n:         " + k.n());

    System.out.println("V:         " + k.V());

    System.out.println("W:         " + k.W());
    
    if (!k.solve()) {

      System.out.println("status: failed");

    } else {

      System.out.println("solution: " + k.solution());

      System.out.println("status:   ok");
    }
  }
  
  public void run() {

    //runMCP();
    
    //runLCS();
    
    //runLCS_NOB();
    
    runKNAPSACK();
  }  

  public static void main(String[] args) {

    System.out.println("start.");

    Runner runner = new Runner();

    runner.run();

    System.out.println("fini.");
  }
}
