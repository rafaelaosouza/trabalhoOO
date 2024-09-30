package com.trabalho.oo.biblioteca.model;

import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nome;
	private String cpf;
	private String senha;
	private boolean isAdmin;

	public Usuario(String nome, String cpf, String senha, boolean isAdmin) throws ValorInvalidoException {
		setNome(nome);
		setCpf(cpf);
		setSenha(senha);
		this.isAdmin = isAdmin;
	}

	// Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws ValorInvalidoException {
		if (!validarNome(nome)) {
			throw new ValorInvalidoException("Nome inválido! Escreva o nome completo.");
		}
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) throws ValorInvalidoException {
		if (!validarCPF(cpf)) {
			throw new ValorInvalidoException("CPF inválido!");
		}
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws ValorInvalidoException {
		if (!validarSenha(senha)) {
			throw new ValorInvalidoException("Senha inválida! Deve ter 8 ou mais caracteres.");
		}
		this.senha = senha;
	}

	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public abstract void acessarSistema();
	public abstract String getTipo();

	public static boolean validarCPF(String cpf) {
		if (cpf.isEmpty()) {
			return false;
		}
		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11 || cpf.equals("00000000000") || cpf.equals("11111111111") ||
				cpf.equals("22222222222") || cpf.equals("33333333333") ||
				cpf.equals("44444444444") || cpf.equals("55555555555") ||
				cpf.equals("66666666666") || cpf.equals("77777777777") ||
				cpf.equals("88888888888") || cpf.equals("99999999999")) {
			return false;
		}

		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
		}
		int digito1 = 11 - (soma % 11);
		if (digito1 >= 10) {
			digito1 = 0;
		}

		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
		}
		int digito2 = 11 - (soma % 11);
		if (digito2 >= 10) {
			digito2 = 0;
		}

		return (digito1 == Character.getNumericValue(cpf.charAt(9)) &&
				digito2 == Character.getNumericValue(cpf.charAt(10)));
	}

	public static boolean validarNome(String nome) {
		if (nome.isEmpty()) {
			return false;
		}
		String[] partes = nome.trim().split("\\s+");
		return partes.length > 1 && nome.length() > 2;
	}

	public static boolean validarSenha(String senha) {
		return !senha.isEmpty() && senha.length() >= 8;
	}
}
