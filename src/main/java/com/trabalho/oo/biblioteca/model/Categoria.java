package com.trabalho.oo.biblioteca.model;

import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import java.io.Serializable;

public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nome;

	public Categoria(String nome) throws ValorInvalidoException {
		setNome(nome);
	}

	// Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws ValorInvalidoException {
		if (nome == null || nome.trim().isEmpty()) {
			throw new ValorInvalidoException("O nome da categoria não pode ser vazio.");
		}
		this.nome = nome;
	}

	@Override
	public String toString() {
		return this.nome;
	}
}
