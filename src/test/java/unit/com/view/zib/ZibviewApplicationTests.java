package com.view.zib;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootTest
class ZibviewApplicationTests {

	@Test
	void contextLoads() {
		String password = "asldkf3jkasdfkj"; // a random password

		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder(15); // 10 is default
		String encode = passEncoder.encode(password);

		long start = System.currentTimeMillis();
		passEncoder.matches(password, encode);
		long end = System.currentTimeMillis();

		System.out.println("end - start = " + (end - start));
		// when strength is 14, it takes about 1190ms on my machine
		// when strength is 15, it takes about 2373ms on my machine which is very slow
	}
}
