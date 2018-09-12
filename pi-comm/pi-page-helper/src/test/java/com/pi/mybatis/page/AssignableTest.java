package com.pi.mybatis.page;

import javax.swing.text.BoxView;
import javax.swing.text.html.BlockView;

public class AssignableTest {
  public static void main(String[] args) throws Exception {
    String[] str = new String[3];
    BlockView block = new BlockView(null, 1);
    BlockView block2 = new BlockView(null, 1);
    BoxView box = new BoxView(null, 1);
    System.out.println(block.getClass().isAssignableFrom(box.getClass()));
    System.out.println(box.getClass().isAssignableFrom(block.getClass()));
    System.out.println(block.getClass().isAssignableFrom(block2.getClass()));
    System.out.println(block2.getClass().isAssignableFrom(block2.getClass()));
  }
}
