![GitHub top language](https://img.shields.io/github/languages/top/Ramy-Badr-Ahmed/bendingStiffness?cacheSeconds=1)
![GitHub](https://img.shields.io/github/license/Ramy-Badr-Ahmed/bendingStiffness)

### Summary

A java code analysing the Bending Stiffness of Actin Filament Experiment.

- Reads coordinates from `snake.txt` of a measured filament (an example is in the `images` directory)
- Calculates the mean squared displacement (MSD) and contour length from data

### Running the Demo

1. Place your `snake.txt` and `elongation.txt` files in the `data` directory (an example exists).
2. Compile and run the `Snake` class.

```sh
javac -d out src/Snake.java
java -cp out Snake
