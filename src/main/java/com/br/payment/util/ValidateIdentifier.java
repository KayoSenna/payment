package com.br.payment.util;

public class ValidateIdentifier {
	
	
	public static Boolean validateCPF(String cpf) {

	        if (cpf == null || cpf.length() != 11) {
	            return false;
			}
	        
	        if (cpf.matches("(\\d)\\1{10}")) {
	            return false;
	        }
	            
	        int sum = 0;
	        for (int i = 0; i < 9; i++) {
	            sum += (10 - i) * Character.getNumericValue(cpf.charAt(i));
	        }
	        int firstDigit = 11 - (sum % 11);
	        if (firstDigit > 9)
	            firstDigit = 0;

	        sum = 0;
	        for (int i = 0; i < 10; i++) {
	            sum += (11 - i) * Character.getNumericValue(cpf.charAt(i));
	        }
	        int secondDigit = 11 - (sum % 11);
	        if (secondDigit > 9)
	            secondDigit = 0;

	        return (Character.getNumericValue(cpf.charAt(9)) == firstDigit &&
	                Character.getNumericValue(cpf.charAt(10)) == secondDigit);
	    }
	
	    public static Boolean validateCNPJ(String cnpj) {
	    	
	        if (cnpj == null || cnpj.length() != 14) {
	            return false;
	        }

	        if (cnpj.matches("(\\d)\\1{13}")) {
	            return false;
	        }
	
	        int[] weights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	        int sum = 0;
	        for (int i = 0; i < 12; i++) {
	            sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i];
	        }
	        int firstDigit = 11 - (sum % 11);
	        if (firstDigit > 9)
	            firstDigit = 0;

	        sum = 0;
	        for (int i = 0; i < 13; i++) {
	            sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i];
	        }
	        int secondDigit = 11 - (sum % 11);
	        if (secondDigit > 9)
	            secondDigit = 0;
	
	        return (Character.getNumericValue(cnpj.charAt(12)) == firstDigit &&
	                Character.getNumericValue(cnpj.charAt(13)) == secondDigit);
	    }
	    
	    public static Boolean validateEU(String eu) {
	    	
	    	if (eu == null || eu.length() != 8) {
	            return false;
	    	}
	    	
	    	Integer verif = Integer.valueOf(eu.charAt(0))+Integer.valueOf(eu.charAt(7));
	    	
	    	if(verif!=9) {
	    		return false;
	    	}else {
	    		return true;
	    	}
	    	
	    }
	    
	    public static Boolean validateAP(String ap) {
	    	
	    	if(ap == null || ap.length()!=10) {
	    		return false;
	    	}
	    	
	    	String finalDigit = String.valueOf(ap.charAt(9));
	    	
	    	 for (int i = 0; i < (ap.length()-1); i++) {
	             
	    		 String a = String.valueOf(ap.charAt(i)); 
	             
	    		 if(a.equals(finalDigit)) {
	    			return false; 
	    		 }

	         }
	    	
	    	return true;
	    	
	    }
	    
	    
}

