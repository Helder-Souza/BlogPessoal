package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.generation.blogPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@ApiOperation(value = "Loga o usuário no sistema")
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user) {
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@ApiOperation(value = "Cadastra o usuário no sistema")
	@PostMapping("/cadastrar")
	public ResponseEntity<Object> Post (@RequestBody Usuario usuario) {
		Optional<Usuario> usuarioCadastrado = usuarioService.CadastrarUsuario(usuario);
		if(usuarioCadastrado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Usuário existente");
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado.get());
		}
		
	}
	
	@ApiOperation(value = "Retorna uma lista de usuários")
	@GetMapping
	public ResponseEntity<List<Usuario>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@ApiOperation(value = "Retorna um usuário que contenha um número identificador específico")
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value = "Altera um usuário")
	@PutMapping
	public ResponseEntity<Usuario> put(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(repository.save(usuario));
	}
}
