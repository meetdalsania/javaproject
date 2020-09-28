# Promotion Serach Engine

To calculate a final discounted price of total items present in a cart based on promotion type that need to be calculated for each item.


# Prerequisites

This project is required JAVA 8 or higher and gradle 6.6.1 to run successfully.

1. To download and install [JAVA](https://openjdk.java.net/)

2. To download and install [Gradle](https://gradle.org/install/)

# How to run

1. To download the dependencies, apply the below commands at project root.
```
gradle clean

gradle build
```
2. Look at `CartServiceTest` class to check the implementation and different scenarios of promotion.

3. (Optional) Add a class which contains a main method to test it rather than using test cases as mention below:

```
public class Main {
  
  public static void main(String[] args) {
        final Cart cart = Cart.getInstance(item..);
        cart.checkOut(promotion); // Retuns the final discounted price.
    }
}
```
