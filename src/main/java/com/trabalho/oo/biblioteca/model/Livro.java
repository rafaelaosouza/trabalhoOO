package com.trabalho.oo.biblioteca.model;

import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;
	private String titulo;
	private String autor;
	private String isbn;
	private Categoria categoria;
	private int anoLancamento;
	private String descricao;
	private int quantidadeDisponivel;
	private List<Integer> avaliacoes;

	public Livro(String titulo, String autor, String isbn, Categoria categoria, int quantidadeDisponivel,
	             int anoLancamento, String descricao) throws ValorInvalidoException {
		setTitulo(titulo);
		setAutor(autor);
		setIsbn(isbn);
		setAnoLancamento(anoLancamento);
		setQuantidadeDisponivel(quantidadeDisponivel);
		setCategoria(categoria);
		setDescricao(descricao);
		this.avaliacoes = new ArrayList<>();
	}

	// Getters e Setters
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) throws ValorInvalidoException {
		if (titulo == null || titulo.trim().isEmpty()) {
			throw new ValorInvalidoException("Título não pode ser vazio.");
		}
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) throws ValorInvalidoException {
		if (autor == null || autor.trim().isEmpty()) {
			throw new ValorInvalidoException("Autor não pode ser vazio.");
		}
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) throws ValorInvalidoException {
		if (isbn == null || isbn.trim().isEmpty()) {
			throw new ValorInvalidoException("ISBN não pode ser vazio.");
		}
		this.isbn = isbn;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(int anoLancamento) throws ValorInvalidoException {
		int anoAtual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		if (anoLancamento < 0 || anoLancamento > anoAtual) {
			throw new ValorInvalidoException("O ano de lançamento deve ser um ano válido (ex: 2021) e não pode ser maior que o ano atual.");
		}
		this.anoLancamento = anoLancamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(int quantidadeDisponivel) throws ValorInvalidoException {
		if(quantidadeDisponivel < 0) {
			throw new ValorInvalidoException("A quantidade disponível não pode ser negativa.");
		}
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public boolean isDisponivel() {
		return quantidadeDisponivel > 0;
	}

	public void diminuirQuantidadeDisponivel() {
		this.quantidadeDisponivel -= 1;
	}

	public void aumentarQuantidadeDisponivel() {
		this.quantidadeDisponivel += 1;
	}

	public void avaliar(int nota) {
		if (nota >= 1 && nota <= 5) {
			avaliacoes.add(nota);
		} else {
			throw new IllegalArgumentException("A avaliação deve ser entre 1 e 5.");
		}
	}

	public double calcularMediaAvaliacoes() {
		if (avaliacoes.isEmpty()) {
			return -1;
		}

		int soma = 0;
		for (int nota : avaliacoes) {
			soma += nota;
		}
		return (double) soma / avaliacoes.size();
	}

	public void trocarAvaliacao(int antigaAvaliacao, int novaAvaliacao) {
		if (avaliacoes.contains(antigaAvaliacao)) {
			avaliacoes.remove(Integer.valueOf(antigaAvaliacao));
			avaliar(novaAvaliacao);
		}
	}

	@Override
	public String toString() {
		return titulo + " - " + autor + " (" + categoria.getNome() + ")";
	}
}
