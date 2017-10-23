package org.podvesnoy.rectangle;

public class Rectangle {
  public int length;
  public int width;

  public Rectangle (int length, int width) {
    this.length = length;
    this.width = width;
  }

  public int getArea() {
    return length * width;
  }

  public int getPerimeter() {
    return (length + width) * 2;
  }
}
