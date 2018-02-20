package kni.webstore.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.service.ProductService;

@RestController
@RequestMapping("/api")
public class ExampleRestController {

	@Autowired
	private ProductService prodService;
	
}