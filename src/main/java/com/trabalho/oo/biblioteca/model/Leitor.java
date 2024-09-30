package com.trabalho.oo.biblioteca.model;

import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import java.io.Serializable;

public class Leitor extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	public Leitor(String nome, String cpf, String senha) throws ValorInvalidoException {
		super(nome, cpf, senha, false);
	}

	@Override
	public void acessarSistema() {
		System.out.println("Leitor acessando o sistema.");
	}

	@Override
	public String getTipo() {
		return "leitor";
	}

}
