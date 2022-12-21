from sympy import *
from sympy.parsing.sympy_parser import parse_expr

eq = "((((3 * (((3 * 3) * ((2 * (13 - 2)) + (((4 * (1 + 6)) * 2) / 8))) + ((5 * 5) + (9 * 3)))) + (((((((((8 + ((1 + (2 * 3)) * (10 + 19))) * 3) * ((5 * 5) * (3 + (8 + 6)))) + ((13 * 2) * (((2 * 16) * (5 * (((2 + 5) * (2 * 3)) - 11))) + (((((5 * 3) - 2) + (14 + (((((17 * 2) / 2) * 3) - (2 * 5)) * 2))) * ((((4 + 3) + (2 * 3)) - 2) * 2)) + (3 * ((((2 + 8) * 3) + (4 + (14 / 2))) * (((6 * 4) / 4) + 1))))))) * ((((((((2 + 5) * (2 * 3)) - 5) + (3 * (5 * 5))) + ((5 * ((11 * 2) + 15)) / 5)) * (((9 * (10 + (7 + 14))) * (2 * ((4 * ((1 + 7) + 3)) + (((3 * 3) * 3) + (4 * 4))))) + ((((13 * 9) * (2 * ((5 * 5) + (2 * (10 + 1))))) - ((2 * ((13 * 13) + (2 * 14))) + ((5 + (2 * (3 * 3))) + 20))) + (4 * (2 * (((((((5 * 4) - 1) + (5 * ((6 + 5) * 3))) + (((4 * 2) * 8) * 4)) + (((10 * 2) + (3 * 3)) * 4)) + (2 * ((((2 * 11) * (7 * 2)) / 2) + (13 + (((3 * 5) + 6) * (4 * 3)))))) + ((3 * 3) * 5))))))) * (5 + 2)) + ((((19 + (2 * (3 * 3))) * 3) * 19) * ((((6 + 17) + (20 * 4)) + (((4 + 3) * 11) + (((((7 * 2) + (3 * (3 * 3))) + 5) / 2) * 4))) + ((((3 * 2) * (((13 * (2 * 4)) - 8) + (5 * 7))) / 6) + ((((((3 * 3) * 3) + (2 + 11)) + (9 * 3)) - (3 * (3 * 2))) * (5 * 3))))))) * 3) + (((((((2 * ((3 * 19) + (2 * (2 * 4)))) + (3 * (((5 + 16) * ((7 * 8) + ((17 * 3) - 4))) + ((2 * (((1 + (3 * (2 + 9))) * 2) + 11)) + (2 * (((((((3 * 3) + (7 * 5)) / 2) * 3) + 1) * 4) + 15)))))) + ((2 * (((13 + 19) + (3 * 5)) * 3)) * (2 * ((10 * 2) + (((3 * 3) + 4) * 17))))) * ((((((3 + 4) + (2 * 17)) * 2) * ((((3 * (((2 * (1 + (20 / 2))) * (((8 * (18 + (2 + 13))) / 8) - 5)) + ((2 * ((5 * (2 * (((2 * 5) * 4) / 2))) + (5 * ((((((3 * ((2 + (2 * 6)) + 1)) + 1) / 2) * 2) / 2) + 8)))) / 2))) * 5) + (((2 * 19) + (2 * (2 * 5))) * (12 + 5))) * 3)) / 3) - ((((((((3 + 8) * 3) + (2 * (2 * 13))) * ((3 * 2) + (15 * 2))) + ((((((8 * 3) + ((10 * 5) + 13)) + (4 * (16 + (4 + 3)))) * (4 * 2)) + ((5 * 5) * (13 + (14 + 8)))) + ((7 * (7 * 6)) + (1 + (4 + 3))))) * 13) + ((((9 * 3) * (11 + 2)) * ((2 * 13) + ((((2 * 5) + (3 * 3)) * (20 / 2)) + ((5 * (12 + (5 * 7))) - ((2 + 4) * (1 + 6)))))) + ((((19 * 4) * ((((4 * 2) * 12) - ((((8 * 3) + 5) * 3) / 3)) * 3)) + ((((3 * 5) + (4 * 17)) * 4) + (((((3 * 3) + (((2 * 3) + ((2 * 6) * (2 * (2 * 3)))) / 5)) + (4 + (2 * 15))) + ((((((3 * 2) + ((4 + 15) - 6)) + ((5 * ((3 * 2) + 5)) + 2)) + 3) - (1 + (8 + 17))) - (5 + 8))) * ((3 * 3) - 2)))) + ((((2 * (2 * (((3 * 3) * (1 + 12)) + (2 * (((5 * 2) + (4 * 4)) + 11))))) / 2) / 2) * ((3 * 10) * (4 * 3)))))) + (((((3 + 4) * 3) * 4) + (13 * 9)) * (8 * ((8 * 4) + (3 * 3))))))) + (((((((4 * 2) * (5 * 7)) + ((((((8 + 1) * 2) + (13 + ((((2 * 3) + 7) - 3) * 4))) * 2) + (3 * (9 + 4))) - ((14 * 2) + 12))) * ((2 * 4) + (((5 * ((5 * 2) * 2)) + ((9 + ((3 * (3 * 3)) + ((((5 * 3) * 2) + 10) + 10))) * (2 * 3))) / 2))) + ((((((((((16 + ((13 * 7) / (8 - 1))) * 2) - 17) - 12) * 3) * ((2 * 3) * (((2 * 5) + 9) * 2))) - (((((5 * 3) * 11) + (2 * ((12 * 6) + (5 * (14 / 2))))) * 4) + ((11 * 17) * 13))) - ((19 + 4) * 4)) + (((((4 * (2 * 4)) + (((((5 + 6) + (((2 * 4) + 3) * 3)) + ((4 + 17) * 3)) + 2) - (((4 + 6) + 9) + 9))) * 2) * (13 * 2)) + ((2 * ((((6 + ((7 * 5) + (8 * (2 * 3)))) * 2) + (13 * ((2 * (4 + (3 * (3 * 3)))) + 5))) + (((4 * 4) * 4) * ((3 * 2) * 10)))) + ((2 * 5) * (3 * ((4 * (7 + 3)) / 4)))))) * 5)) * 2) * (((((1 + (4 * ((2 * ((((4 + ((4 * 3) + 7)) + (9 * 9)) + 12) + (((4 * 3) + 1) * 5))) / 2))) * ((13 * (((5 * 9) + (11 * 2)) * 2)) + (((4 * 4) + 1) * (2 + (2 + 9))))) - (((((((7 * 5) * (((5 + (((4 + 3) * 3) + ((14 / 2) * 3))) * 2) / 2)) + (((((5 * 2) * (4 + 3)) + (((2 * (2 + 5)) * 2) - (2 * 3))) + ((3 * 2) * (2 + 9))) + (((14 + (3 + ((3 * 16) + 2))) * 3) - 7))) * (5 * 9)) + (2 * (((2 * (2 + 4)) * 17) + (((((((7 * (1 + (2 + 4))) + (2 + 6)) * 3) * (((2 * 3) + 1) + 6)) + (((((((((2 * 11) + 5) - (2 + (2 + 4))) + ((2 * ((2 * 3) + 1)) / 2)) * (3 * 3)) / 3) + (17 * 5)) + ((((4 * 8) - (3 * 3)) * 2) * 4)) + ((1 + ((3 + 11) * 2)) * 13))) / ((7 * 2) / 2)) * 5)))) * 4) - ((((3 * 7) + 8) * (2 * 3)) * ((((((1 + (3 * 2)) * 2) / 2) + (3 + 4)) * 3) + ((5 * (8 + 5)) * 2))))) - ((2 * ((((2 * 3) + 1) + (3 + (3 * 3))) - 2)) * (((((11 + 6) * 2) * (2 * ((3 + (2 * 13)) + (10 + ((3 * 2) + 8))))) + (((((4 + 7) * ((7 * 2) + (16 + 1))) + (((4 * 2) + (7 * ((5 * 2) + 1))) * 5)) + ((7 * 5) * ((2 * ((2 * (5 + ((3 * 2) * 4))) / 2)) / 2))) - (((3 + 4) * (3 * (3 * 3))) + (((4 + (((2 + 5) + 20) + (6 * 2))) * 3) * 2)))) + (((13 + ((3 * (5 + 1)) + (4 + 7))) * (3 * (1 + (4 * 5)))) / 2)))) * 3))) * 3) * (3 * 2))) - (5 * (((((((4 + 3) * ((2 * 12) + (2 * 5))) + (((((((3 * (5 + 2)) + (2 + (2 * 7))) * 9) + ((2 * 10) * ((((5 * (3 * (2 + 5))) + ((2 * 5) + 1)) * 7) + (((((12 * ((4 * ((8 + 3) + 1)) + (5 * 5))) + (((((((13 * 2) + (7 + 10)) * 9) + (((5 + 2) * (((((4 * 2) + (5 * 3)) + 18) + (2 + 4)) + 2)) + ((((((2 * ((((2 * (((((((2 * ((3 * 11) + ((14 * 10) - ((12 + 11) * 2)))) - (2 * 17)) + 3) - (6 * 11)) + (5 * 3)) + (((((((3 * (1 + (14 * 2))) - (13 * 2)) * (1 + 7)) / (4 * 2)) - 14) * 2) * 3)) / 2)) * 2) - (18 * 9)) + (((2 * ((20 * ((3 * 3) - 3)) + (((((((((((((((((((2 * ((((3 * 3) * (3 * 3)) * (3 + 4)) + (((11 * (((((((((((((((2 * 9) * 4) + ((2 * (((16 + 3) * 7) - (2 * 16))) - ((3 * 3) * 5))) + x) + ((((((((11 + (2 + ((5 * 5) + 3))) * 5) + (2 * ((((2 * 5) * (5 * 2)) + 6) / 2))) + (14 * (3 * 3))) + ((7 * 11) + (((5 * 2) * 16) - 11))) + (((2 * 3) + ((6 * 2) + 5)) * 4)) / 5) * 3)) / 2) - (((2 * (5 + 18)) + 1) * 7)) * (18 + 9)) - (((((5 * 2) * 5) + ((((3 * 2) + 1) * (5 * 2)) + (6 + 11))) * 2) * 2)) * 2) + (((11 + (3 * 3)) * (2 * ((((3 * 3) + (3 * 2)) + (18 * 3)) / 3))) / 4)) / 2) + (((((2 * ((16 + 18) - 5)) * 2) - (4 + (11 * 3))) + (2 * (2 * (3 * 2)))) * (3 * 2))) / ((3 * 2) + 1)) - ((18 + ((2 * (4 + 19)) / 2)) + ((5 * (5 + (2 * 7))) + (((12 + 5) + (2 * 3)) + ((5 + 17) * 3)))))) - (2 * ((((2 * ((4 + (((2 * 17) + 12) / 2)) + 14)) / 2) - 12) + (((1 + 11) * (2 + 15)) / 2)))) / 2))) - (((((3 * (5 + 2)) + 8) * 4) + ((4 + 3) + ((5 * 2) * 4))) * 3)) / 3) + (2 * ((5 * (((4 * ((3 + 10) + (2 * 5))) + (3 * 19)) - 20)) / 3))) * 2) - ((3 * 3) * (3 * (2 + ((2 * 3) + (((3 + 10) + 6) + 4)))))) * 2) + ((((4 + 15) * 2) + (((1 + 6) * 2) + (10 + (4 + (3 * 3))))) + ((((17 + 2) * (12 * 2)) / 8) + (7 + (((5 * 7) + 12) * 2))))) / (3 * 3)) + ((7 * ((4 + 7) + (5 * 4))) * 2)) * 4) - (((11 * 13) + (4 * 17)) * 3)) + ((2 * ((((3 * 5) + (11 * 2)) * 2) + (7 * 5))) * 2)) / 11) + (((2 * (((((4 + 13) + 6) + ((1 + (6 + ((5 * 3) - 4))) / 3)) * 2) / 2)) * (2 * 3)) + ((3 * 5) * 8))) + ((2 * 13) * 4)) / 5) - ((17 + ((2 + ((3 * 2) + 2)) - 1)) * (((2 + (4 * 5)) / 2) + (19 + 4)))) * (2 * (2 * 16))))) - ((((5 * 5) - 2) * 2) * (3 * (6 + 1)))) / 2))) - ((13 * 4) + (5 * (((2 * 3) * 2) + (((6 * (5 * 5)) / (3 * 2)) + (2 * ((3 * 7) + 2))))))) + (7 * (9 * 5))) / 2) - (((((((((((((3 + 16) * 2) * 2) - 3) + (2 * (10 + (((((2 + 5) * 3) + 4) + ((14 + 2) - 3)) / 2)))) * 4) / 4) + ((7 + (4 * 6)) + (6 * (3 * 2)))) / 2) + ((2 * 11) + (4 * 4))) + (((3 * (2 * 7)) - 14) + 5)) + ((((((2 * ((13 * 5) + 8)) / 2) + (12 + (2 * (2 * 3)))) + (3 * 3)) * 2) + (3 * 5))) * 2)) * 2))) / 4) - (2 * (((7 * (3 * 3)) + (((((4 * 2) + ((2 * ((2 * 17) - (2 + (3 * 3)))) / 2)) + (((3 * (3 + 3)) * 5) / 6)) + ((((3 * 11) + (4 + 15)) * (7 + 4)) - (((((((3 * 7) / 3) * 3) * (1 + 7)) / 2) + ((5 * ((5 * 5) + (3 * 2))) - 2)) - ((3 + 16) * 4)))) * 3)) / 6))) * 2)) / 3) - (((14 + (((3 + (5 * 2)) - 4) + 3)) * 19) + (5 * 5))) / 3)))) * 2) - (3 * (15 + ((((2 * 3) * 16) + 7) + (((6 + 1) * 7) + (20 + ((12 + (3 * 3)) + 6))))))) / 6)) * 2) - (2 * (3 + ((((2 * (3 * (3 * 3))) - 10) + ((5 * 5) + ((6 * 4) + ((((2 * (2 + (5 * 7))) / 2) + (3 * 8)) + ((14 / 2) + (5 * 3)))))) / 4)))) / 5) + (((((6 * 18) / 2) / 2) + (5 * (3 * 11))) + (10 * (13 * 2)))))) + ((2 * (((2 * 4) + 9) + (2 * 3))) * ((4 + 3) * 3)))) / 4),((((((((((11 * 3) * 3) + (2 * ((((13 * 2) + 5) * 3) + (4 + 10)))) + (((9 + (3 + (19 + 1))) + (3 * ((12 / 2) + 1))) * 2)) * (((2 * (2 + 5)) + 8) + (2 + 5))) * 11) + ((2 * ((3 * 4) + 13)) * ((13 * 3) * 3))) * 2) * (((((((((12 * 6) / (2 * 3)) + 7) + (11 * 2)) * (3 + (14 * 2))) - ((4 * 2) + (2 * (((2 * 11) * 3) + 7)))) * 11) + ((((5 + ((10 + 1) * 4)) - (4 + (2 + 5))) * ((1 + 5) * 17)) - (((((2 * 3) * 3) + (2 * ((2 * 4) + ((2 * 4) + (9 + 4))))) * (2 * (4 * 4))) / 2))) * ((((((5 * (9 + 4)) + (19 * 3)) * 2) * 4) + ((2 * (2 + (((2 * 5) + 13) * 3))) + (5 * 3))) - ((((3 + ((3 * 4) + (14 * 4))) + (4 * (4 * (1 + 12)))) + ((3 * ((((3 * 2) + 4) * (2 * 3)) - 9)) - (2 + 5))) - (((2 * ((1 + (6 * 2)) + (6 * 4))) / 2) + (4 * (3 * 2))))))) + ((7 * (((((3 * 2) * 3) * ((((13 + ((15 * 2) / 5)) * (((4 * (3 * (8 - 1))) + ((((5 + 2) + 8) * 8) / 2)) + (((((3 * 7) + 2) * 3) + ((9 + 10) * 2)) + 6))) * 2) + ((3 * (3 + ((7 + 6) * 2))) * (((5 + 8) * 3) + (3 * 3))))) + (((2 * 6) + (5 * ((16 * 11) - (7 + (4 * 3))))) * (2 + (((5 * 5) * 2) + (11 + (10 * 5)))))) * 3)) * ((13 * (1 + (4 + 2))) * ((((6 * (((3 * 7) + (3 + (10 - 3))) * 3)) - (5 * ((5 + 3) + 3))) * (2 + ((2 * (14 + 2)) - 1))) + (((6 + (8 * (((7 + 4) * 2) + 3))) + ((11 + ((5 * 14) / 2)) / 2)) * ((1 + (2 * 3)) + ((1 + (2 + 5)) + 5))))))))"
parsedExpression = sympify(eq)
print(solve(f"Eq({parsedExpression[0]}, {parsedExpression[1]})"))