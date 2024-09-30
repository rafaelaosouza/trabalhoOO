package com.trabalho.oo.biblioteca.utils;

public class UsuarioJaCadastradoException extends Exception {
	public UsuarioJaCadastradoException() {
		super("Usuário já cadastrado!");
	}
}
