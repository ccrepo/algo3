package cc.algo;

public class Common {
  
  static public String fold(int[] is) {

    StringBuilder buffer = new StringBuilder();

    for (int i : is) {

      if (!buffer.isEmpty()) {

        buffer.append(" ");
      }

      buffer.append(i);
    }

    return "{ " + buffer.toString() + " }";
  }
}
