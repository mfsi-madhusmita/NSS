public class SpringStringParser {

	public static void main(String[] args) {
		// 3+My images|3_5+Test|3_8+25HO52 FullFamily_Brochure
		// 3+My images|3_5+Test
		String parentsAsString = "3+My images";
		// String[] strArray = StringUtils.split(parentsAsString, "\\|");
		String[] strArray = getData(parentsAsString);

		for (String str : strArray)
			System.out.println(str);
	}

	private static String[] getData(String str) {
		String[] arr = str.split("\\+");
		return arr;
	}
}
