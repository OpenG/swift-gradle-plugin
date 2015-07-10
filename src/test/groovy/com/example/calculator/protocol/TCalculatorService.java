package com.example.calculator.protocol;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

@ThriftService
public interface TCalculatorService {

    @ThriftMethod
    int calculate(int num1, int num2, TOperation op) throws TDivisionByZeroException;
}
