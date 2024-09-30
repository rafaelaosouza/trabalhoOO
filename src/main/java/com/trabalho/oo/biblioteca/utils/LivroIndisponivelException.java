package com.trabalho.oo.biblioteca.utils;

public class LivroIndisponivelException extends Exception {
	public LivroIndisponivelException() {
		super("Livro indisponível no momento.");
	}
}
