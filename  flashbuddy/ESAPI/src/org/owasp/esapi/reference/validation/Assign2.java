package org.owasp.esapi.reference.validation;

import java.text.DateFormat;
import java.util.*;
import java.util.regex.Pattern;

import org.owasp.esapi.reference.DefaultEncoder;

public class Assign2 {

	public static void main(String[] args) {
		
		CreditCardValidationRule ccVal = new CreditCardValidationRule("Zack", DefaultEncoder.getInstance());		
		StringValidationRule stringRule = new StringValidationRule("String Rule");
		
		Scanner input = new Scanner(System.in);
		
		ccVal.setMaxCardLength(14);
		
		int maxLength = ccVal.getMaxCardLength();
		
		System.out.println("The max card length is " + maxLength);
		
		StringValidationRule ccRule = ccVal.getStringValidatorRule();
		
		System.out.println("The string validation rule for the credit card is " +ccRule);
		
		System.out.println("Enter the your Credit Card number:");
		
		String ccInput = input.next();
		
		try{
			String valCC = ccVal.getValid("User Input", ccInput);
			
			System.out.println("Valid Credit Card Object is " + valCC);
		} catch(Exception e){
			System.out.println("The number is not valid.");
		}
		
		String sanitCC = ccVal.sanitize("User Input", ccInput);
		
		System.out.println("Sanitize creditcard object is " + sanitCC);
		
		ccVal.setStringValidatorRule(stringRule);
		
		boolean addCCVal = ccVal.validCreditCardFormat(ccInput);
		
		System.out.println("The additional validation is " + addCCVal);
		
		//End of the Credit Card Validation method calls.
		
		DateFormat df = DateFormat.getDateInstance();
		
		DateValidationRule dateRule = new DateValidationRule("DateRule", DefaultEncoder.getInstance(), df);
		
		System.out.println("Enter the date: ");
		
		String dateInput = input.next();
		
		try{
		java.util.Date valDate = dateRule.getValid("User Input", dateInput);
		
		System.out.println("The valid date is " + valDate);
		} catch (Exception e) {
			System.out.println("Date Validation Failed");
		}
		
		java.util.Date sanitizedDate = dateRule.sanitize("User Input", dateInput);
		
		System.out.println("The sanitized date object is " + sanitizedDate);
		
		dateRule.setDateFormat(DateFormat.getDateInstance());
		
		//End Date Validation class method calls
		
		HTMLValidationRule htmlRule = new HTMLValidationRule("HTML Rule");
		
		System.out.println("Enter HTML input:");
		
		String htmlInput = input.next();
		
		try{
		String valHTML = htmlRule.getValid("User Input", htmlInput);
		
		System.out.println("The valid HTML string is " + valHTML);
		}catch (Exception e) {
			System.out.println("HTML is not valid");
		}
		
		String sanitizedHTML = htmlRule.sanitize("User Input", htmlInput);
		System.out.println("The sanitized HTML object is " + sanitizedHTML);
		
		//End HTML Validation class method calls.
		
		IntegerValidationRule intRule = new IntegerValidationRule("The Int Rule", DefaultEncoder.getInstance(), 0, 500);
		
		System.out.println("Enter an integer between 0 and 500:");
		
		String intInput = input.next();
		
		try{
		int valInt = intRule.getValid("User Input", intInput);
		
		System.out.println("The valid int is " + valInt);
		}catch (Exception e){
			System.out.println("The int is not valid");
		}
		
		int sanitizedInt = intRule.sanitize("User Input", intInput);
		
		System.out.println("The sanitized integer is " + sanitizedInt);
		 
		//End of Integer Validation Rule class method calls.
		
		NumberValidationRule numRule = new NumberValidationRule("Number Rule", DefaultEncoder.getInstance(), 0.0, 500.0);
		
		System.out.println("Enter a double between 0 and 500:");
		
		String numInput = input.next();
		
		try{
		double valNum =	numRule.getValid("User Input", numInput);
		
		System.out.println("The valid double is " + valNum);
		}catch (Exception e){
			System.out.println("The number is not valid.");
		}
		
		double sanitizedNum = numRule.sanitize("User Input", numInput);
		
		System.out.println("The sanitized double is " + sanitizedNum);
		
		//End of the Number Validation Rule class method calls.
		
		System.out.println("Enter a valid string (123 or the whitelist):");
		
		String stringInput = input.next();
		
		java.util.regex.Pattern blacklist = Pattern.compile("ABC");
		java.util.regex.Pattern whitelist = Pattern.compile("123");
		
		stringRule.setMaximumLength(35);
		
		stringRule.setMinimumLength(3);
		
		stringRule.addBlacklistPattern(blacklist);
		
		stringRule.addBlacklistPattern("the blacklist");
		
		stringRule.addWhitelistPattern(whitelist);
		
		stringRule.addWhitelistPattern("the whitelist");
		
		try{
		String valString = stringRule.getValid("User Input", stringInput);
		
		System.out.println("The valid string is " + valString);
		}catch (Exception e){
			System.out.println("The string is not valid.");
		}
		
		String sanitizedString = stringRule.sanitize("User Input", stringInput);
		
		System.out.println("The sanitized string is " + sanitizedString);
		
		stringRule.setValidateInputAndCanonical(false);
		
		//End of String Validation Rule class method calls.
		
	}
}
