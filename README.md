# multithreaded-matrix-multiplication
Comparison between conventional, Divide and Conquer, and Strassen methods for matrix multiplication

Open project in desired IDE or compile from command line and run MatrixMultiply with debug set to false for full test
Will test square matrix multiplication on 2x2 through 2048x2048 matrices. For matrices >= 512x512, the program will  
utilize multi-threading to highlight the benefit when compared with D&C or Strassen methods. 

Tests ran on 9900K with 32GB of 3200 MHz of RAM (times in milliseconds):
| Size | Base Case Size | Conventional | MultiThreaded | Divide & Conquer | Strassen |
|------|----------------|--------------|---------------|------------------|----------|
| 2    | 1              | 0            | 0             | 0                | 0        |
| 4    | 1              | 0            | 0             | 0                | 0        |
| 8    | 1              | 0            | 0             | 0                | 0        |
| 16   | 1              | 0            | 0             | 1                | 1        |
| 32   | 1              | 0            | 0             | 18               | 12       |
| 64   | 1              | 0            | 0             | 96               | 82       |
| 128  | 1              | 2            | 1             | 643              | 431      |
| 256  | 4              | 14           | 14            | 202              | 158      |
| 256  | 8              | 14           | 13            | 53               | 48       |
| 256  | 16             | 14           | 12            | 22               | 22       |
| 512  | 4              | 144          | 41            | 1618             | 1112     |
| 512  | 8              | 148          | 35            | 426              | 337      |
| 512  | 16             | 146          | 39            | 178              | 158      |
| 1024 | 4              | 1375         | 550           | 12943            | 7797     |
| 1024 | 8              | 1670         | 614           | 3419             | 2376     |
| 1024 | 16             | 1489         | 590           | 1434             | 1107     |
| 2048 | 4              | 49200        | 6359          | 103565           | 54587    |
| 2048 | 8              | 47679        | 6497          | 27368            | 16672    |
| 2048 | 16             | 46946        | 6772          | 11487            | 7770     |
