package com.trabalho.oo.biblioteca.model;

import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import java.io.Serializable;

public class Administrador extends Usuario implements Serializable {
	public Administrador(String nome, String cpf, String senha) throws ValorInvalidoException {
		super(nome, cpf, senha, true);
	}

	@Override
	public void acessarSistema() {
		System.out.println("Administrador acessando o sistema.");
	}

	@Override
	public String getTipo() {
		return "administrador";
	}

}
