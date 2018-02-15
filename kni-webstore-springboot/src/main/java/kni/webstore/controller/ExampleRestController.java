package kni.webstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleRestController {

	@GetMapping("/api/example")
	public String example() {
		return "Przykladowy rest odebrany z serwera";
	}
}