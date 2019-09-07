package com.fbleague.infoserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class InfoserverApplicationTest {

	@Test
	public void shouldBeAbleToStartApplicationWithoutTomcat() {
		// Coverage for main class
	}
	
}
