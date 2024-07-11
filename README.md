### Summary

A java code analysing the Bending Stiffness of Actin Filament Experiment.

- Reads coordinates from `snake.txt` of a measured filament (an example is in the `images` directory)
- Calculates the mean squared displacement (MSD) and contour length from data
- Error calculation in persistence length and contour length are saved in `mathematica` directory.

### Running the Demo

1. Place your `snake.txt` and `elongation.txt` files in the `data` directory (an example exists).
2. Compile and run the `Snake` class.

```sh
javac -d out src/Snake.java
java -cp out Snake