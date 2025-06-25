A Reverse Polish Notation Calculator

Design and implement a command line application that takes in an input file of calculations and returns the answers. The equations are specified in Reverse Polish Notation. The parameters should be separated by a space.

For example the file: (Separated by a space)

1.0 2.0 + 3 4 * 6 3 * 2 - sqrt 1 + 1 4 2 - 2 - 1000 *

Would return the output:

1.0 2.0 + = 3.0
3 4 * = 12.0
6 3 * 2 - sqrt = 4.0
1 + 1 - Not Reverse Polish Notation try backwards
4 2 - 2 - 1000 * =  0.0

The answer is calculated from left to right, the first numbers are calculated with the first operator read. Then each value / operator pair is applied to the current calculated value.

The calculator must support the following standard operations + (additions), - (minus) * (multiplication) and division (/)

Also, the following other operators are required:
sqrt – the square root of one value
sin – the sine of the one value
cos – the cosines of one value
avg – the average of two values
mod – the modulus of two values

Your solution should include source code, and a way to build the code. You should not include any binaries in the submission.

Please note you are only allowed to use standard language features, no external frameworks, with the exception of a build tool (if needed), and a Test framework (if required). The solution you provide should be written in Java and be “production” ready, i.e a potentially shippable product.

Please complete your solution as a git repository and provide a link. 
