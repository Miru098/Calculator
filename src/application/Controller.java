package application;


import java.util.Vector;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
	
	
	@FXML 
	private Label inputLabel, outputLabel;
	
	private String operand = "";
	
	private String PLUS = "+", MINUS = "−", DIVIDE = "÷", MULTIPLY = "×", POWER = "^";
	

 	private boolean isOperator(String s) {
		String opStr = PLUS + MINUS + DIVIDE + MULTIPLY;
		if(opStr.contains(s)) {
			return true;
		}
		return false;
	}
	
 	private boolean isDigit(String s) {
 		String digitStr = "0123456789";
 		if(digitStr.contains(s)) {
 			return true;
 		}
 		return false;
 	}
 	
	
	public void input(ActionEvent e) {
		Button button = (Button) e.getTarget();
		
		String prev = inputLabel.getText();
		int prevLen = prev.length();
		String next = button.getText();
		String exp;
		
		if(operand.equals("0") && isDigit(next)) { // if the digit is 0
			// if previous digit is also 0
			exp = prev.substring(0, prevLen - 1) + next;
			operand = next;
		}
		else if(next.equals(".") && operand.contains(".")) {
			exp = prev;
		}
		else if(prevLen != 0 && isOperator(next) && isOperator(prev.substring(prevLen - 1))) {
			exp = prev.substring(0, prevLen - 1) + next;
		}
		else {
			if(isOperator(next)) {
				operand = "";
			}else {
				operand += next;
			}
			exp = prev + next;
		}
			
		inputLabel.setText(exp);
		
		showResult(exp);
		
	}
	
	// method for converting infix String to postfixList 
	private Vector<String> convertToPostfix(String infix) {
		System.out.println(infix);
		String operand = "";
		Vector<String> postfixList = new Vector<String>();
		Vector<String> operatorList = new Vector<String>();
		
		// if the last char of infix is operator then delete it
		if(isOperator(infix.substring(infix.length() - 1))) {
			infix = infix.substring(0, infix.length() - 1);
		}
		
		for(int i = 0; i < infix.length(); i++) {
			String s = String.valueOf(infix.charAt(i));
			
			// if the char is digit or . then store it in operand
			if(isDigit(s) || s.equals(".")) {
				operand += s;
			}else {
				postfixList.add(operand);
				operand = "";
				
				// adding operator using precedence
				while(operatorList.size() > 0 && precedence(s) <= precedence(operatorList.lastElement())) {
					postfixList.add(operatorList.removeLast());
				}
				operatorList.add(s);
			}
		}
		
		// add the last operand to postfixList
		if(!operand.isEmpty()) {
			postfixList.add(operand);			
		}
		
		// adding all the remaining operator from operatorList to postfixList
		while(operatorList.size() > 0) {
			postfixList.add(operatorList.removeLast());
		}
		
		return postfixList;
		
//		for(int i = 0; i < postfixList.size(); i++) {
//			System.out.print(postfixList.get(i) + " ");
//		}
//		System.out.println();
		
	}
	
	private int precedence(String operator) {
		if(operator.equals(PLUS)) {
			return 1;
		}else if(operator.equals(MINUS)) {
			return 1;
		}else if(operator.equals(DIVIDE)) {
			return 2;
		}else if(operator.equals(MULTIPLY)) {
			return 2;
		}else {
			return -1;
		}
	}

	
	private double operate(double operand1, double operand2, String operator) {
		if(operator.equals(PLUS)) {
			return operand1 + operand2;
		}else if(operator.equals(MINUS)) {
			return operand1 - operand2;
		}else if(operator.equals(DIVIDE)) {
			return operand1/operand2;
		}else if(operator.equals(MULTIPLY)) {
			return operand1 * operand2;
		}else {
			return 1;
		}
	}
	
	private String evaluate(Vector<String> postfixList) {
		Vector<Double> stack = new Vector<Double>();
		for(int i = 0; i < postfixList.size(); i++) {
			if(isOperator(postfixList.get(i))) {
				double op2 = stack.removeLast();
				double res = op2;
				if(stack.size() != 0) {					
					double op1 = stack.removeLast();
					res = operate(op1, op2, postfixList.get(i));
				}
				stack.add(res);
			}else {
				stack.add(Double.parseDouble(postfixList.get(i)));
			}
//			System.out.print(postfixList.get(i) + " ");
		}
//		System.out.println("  "+ postfixList.size());
		
		double res = stack.removeLast();
		if(res * 10 == ((int) res) * 10){			
			return String.valueOf((int) res); 
		}else {
			return String.valueOf(res);
		}
	}
	
	private void showResult(String infix) {
		Vector<String> postfixList = convertToPostfix(infix);
		String result = evaluate(postfixList);
		outputLabel.setText(result);
	}
	
	public void clear(ActionEvent e) {
		inputLabel.setText("");
		outputLabel.setText("");
		operand = "";
	}

	
	public void backspace(ActionEvent e) {
		String prev = inputLabel.getText();
		int len = prev.length();
		if(len == 0) {
			return;
		}
		
		// if lastChar is not operator then delete 1 char from operand
//		if(!isOperator(prev.substring(len - 1))) {
//			operand = operand.substring(0, len - 1);
//		}
		
		
		inputLabel.setText(prev.substring(0, len - 1));
		prev = inputLabel.getText();
		if(prev.length() == 0) {
			outputLabel.setText("");
			return;
		}
		showResult(inputLabel.getText());
	}

	public void equalBtnClicked(ActionEvent e) {
		String resultText = outputLabel.getText();
		if(!resultText.equals("Infinity")) {
			inputLabel.setText(outputLabel.getText());
			outputLabel.setText("");
			operand = "";			
		}
	}

	public void plusMinusBtnClicked(ActionEvent e) {		
		String prev = inputLabel.getText();
		
		int operandLen = operand.length();
		
		if(operandLen != 0) { // if operand is not empty
			operand = "(-" + operand + ")";
			System.out.println(operand);
			prev = prev.substring(0, prev.length() - operandLen) + operand;
			inputLabel.setText(prev);
			showResult(inputLabel.getText());
		}
	}

}