package net.seniorsoftwareengineer.testing.service.utility;


import lombok.extern.slf4j.Slf4j;
@Slf4j
public class WebPageUtility {
	public static Integer random(int max) {
		final Double random = new Double(Math.random() * max);
		if (random < 1) {
			return 1;
		}
		return random.intValue();
	}
}
