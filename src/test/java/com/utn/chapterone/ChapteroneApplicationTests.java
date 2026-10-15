package com.utn.chapterone;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Requiere base de datos MySQL activa en localhost:3306")
class ChapteroneApplicationTests {

	@Test
	void contextLoads() {
	}

}
