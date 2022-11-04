package de.amthor.gendb.utils;

import java.util.regex.Pattern;

public class PasswordCheck
{
	
	/**
	 * Tests if a password string complies with the following rules:<ul>
	 * <li>It is at least 8 and at most 24 characters long</li>
	 * <li>It contains only printable ASCII characters within the ASCII code range from 33 to 126 (inclusive)</li>
	 * <li>It contains at least one character of each of the following character categories:<ul>
	 * 	<li>a lower case letter</li>
	 * 	<li>an upper case letter</li>
	 * 	<li>a digit</li>
	 * 	<li>a "special" character, i.e., a character that is neither a letter nor a digit</li></ul>
	 * </ul>
	 * 
	 * @param pwd the password string to test
	 * @return <code>true</code> if the string complies with the rules
	 * @throws IllegalArgumentException if <code>pwd</code> is <code>null</code>
	 */
	public boolean isAcceptablePassword(String pwd)
	{
        //throw new UnsupportedOperationException("Please implement this method");
        
        if ( pwd == null )
            throw new IllegalArgumentException("no pwd provided");
        
        if ( pwd.strip().length() < 8 || pwd.length() > 24 )
        	return false;
        
        if ( !pwd.chars().allMatch(c -> c >= 0x20 && c < 0x7F ))
        	return false;
        
        boolean flag = true;
        
        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        
        if (!specialCharPatten.matcher(pwd).find()) {
            flag=false;
        }
        if (!UpperCasePatten.matcher(pwd).find()) {
            flag=false;
        }
        if (!lowerCasePatten.matcher(pwd).find()) {
            flag=false;
        }
        if (!digitCasePatten.matcher(pwd).find()) {
            flag=false;
        }
        
        return flag;
        
	}

}