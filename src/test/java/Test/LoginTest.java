package Test;

import org.testng.annotations.Test;
import KeywordExcelRead.Keywordread;

public class LoginTest {
	
	@Test
	public void loginTest() {
		
		Keywordread kr = new Keywordread();
		kr.startExecution("TestData");
		
	}
}
