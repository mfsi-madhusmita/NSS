package be.nss.vit2print.utility;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Helper Class to perform String related functions
 */
@Component
public class StringParser {

	public String[] doSplit(String stringToSplit, String token) {
		return stringToSplit.split(token);
	}

	public String doTokenizeArray(String[] arrayToTokenize, String token) {
		return StringUtils.arrayToDelimitedString(arrayToTokenize, token);
	}

	public String doTokenizeList(List<String> listToTokenize, String token) {
		return StringUtils.collectionToDelimitedString(listToTokenize, token);
	}

	public String doDeleteStringPattern(String inputString,
			String patternToDelete) {
		return StringUtils.delete(inputString, patternToDelete);
	}
}
