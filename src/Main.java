import xyz.kumaraswamy.skiplist.SkipList;

public class Main {
   public static void main(String[] args) {
      SkipList list = new SkipList();
      for (int i = 1; i < 17; i++) {
         list.add(i);
      }

      System.out.println(list.regular);
      System.out.println(list.fast);

      System.out.println(list.get(16));
   }
}