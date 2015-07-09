namespace java.swift com.example.calculator.protocol


enum TOperation {
  ADD, SUBTRACT, MULTIPLY, DIVIDE
}

exception TDivisionByZeroException {
}

service TCalculatorService {
  i32 calculate(1:  i32 arg0, 2:  i32 arg1, 3:  TOperation arg2) throws (1: TDivisionByZeroException ex1);
}
