package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;
	
	@ApiOperation(value = "Retorna uma lista de postagens")
	@GetMapping
	public ResponseEntity<List<Postagem>> Getall(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@ApiOperation(value = "Retorna uma postagem que contenha um número identificador específico")
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable Long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value = "Retorna uma postagem que contenha um título específico")
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@ApiOperation(value = "Cria uma nova postagem")
	@PostMapping
	public ResponseEntity<Postagem> post (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@ApiOperation(value = "Altera uma postagem")
	@PutMapping
	public ResponseEntity<Postagem> put (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	@ApiOperation(value = "Deleta uma postagem que contenha um número identificador específico")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
