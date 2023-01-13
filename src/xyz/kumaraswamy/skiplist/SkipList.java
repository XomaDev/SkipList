package xyz.kumaraswamy.skiplist;

import java.util.ArrayList;
import java.util.List;

public class SkipList {

   public static class Element {
      public Object value;
      public int index;

      public Element(Object value, int index) {
         this.value = value;
         this.index = index;
      }

      @Override
      public String toString() {
         return String.valueOf(value);
      }
   }

   private final int FAST_TRACK_INTERVAL = 5;

   public final List<Element> fast = new ArrayList<>();
   public final List<Element> regular = new ArrayList<>();

   private int size = 0;

   public void add(Object value) {
      Element element = new Element(value, ++size);
      if (size != 0 && size % FAST_TRACK_INTERVAL == 0) {
         fast.add(element);
      }
      regular.add(element);
   }

   public Element get(Object value) {
      int fastIndex = 0;
      int fastTrackSize = fast.size();

      for (; fastIndex < fastTrackSize; fastIndex++) {
         Element element = fast.get(fastIndex);

         int index = element.index;
         Object elementValue = element.value;

         if (elementValue == value) {
            return element;
         }

         int keyIsAbove = firstIsAbove(elementValue, value);

         if (keyIsAbove == 1) {
            int start = index - FAST_TRACK_INTERVAL;
            System.out.printf("[%d:%d]\n", start, index);
            return searchInRange(value, start, index);
         } else {
            boolean isEnd = fastIndex + 1 >= fastTrackSize;
            // if { true } this is the last element of the fast
            // track list

            if (isEnd) {
               int end = index + FAST_TRACK_INTERVAL;
               System.out.printf("[%d:%d]\n", index, end);
               return searchInRange(value, index, end);
            }
         }
      }
      return null;
   }

   public Element searchInRange(Object value, int from, int till) {
      for (int i = from; i < till; i++) {
         Element element = regular.get(i);
         if (element.value == value) {
            return element;
         }
      }
      return null;
   }

   public int firstIsAbove(Object n, Object x) {
      return switch (n.getClass().getSimpleName()) {
         case "int", "Integer" -> (int) n > (int) x ? 1 : 0;
         case "float", "Float" -> (float) n > (float) x ? 1 : 0;
         case "double", "Double" -> (double) n > (double) x ? 1 : 0;
         case "long", "Long" -> (long) n > (long) x ? 1 : 0;
         default -> -1;
      };
   }
}
