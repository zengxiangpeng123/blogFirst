package com.example.back_end;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class BackEndApplicationTests {

	@Test
	void contextLoads() {
		Date date = new Date();
		System.out.println(date);

		String format = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
		System.out.println(format);

		DateTime date1 = DateUtil.date();
		System.out.println(date1);

	}

}
