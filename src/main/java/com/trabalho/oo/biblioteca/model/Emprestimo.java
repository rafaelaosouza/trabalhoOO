package com.trabalho.oo.biblioteca.model;

import java.io.Serializable;
import java.util.Date;

public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Livro livro;
	private Usuario leitor;
	private Date dataEmprestimo;
	private Date dataDevolucao;
	private String status;
	private int avaliacao;

	public Emprestimo(Livro livro, Usuario leitor, Date dataEmprestimo) {
		this.livro = livro;
		livro.diminuirQuantidadeDisponivel();
		this.leitor = leitor;
		this.dataEmprestimo = dataEmprestimo;
		this.status = "pendente";
		this.dataDevolucao = null;
		this.avaliacao = -1;
	}

	public void devolver(Date dataDevolucao, int avaliacao) {
		livro.aumentarQuantidadeDisponivel();
		livro.avaliar(avaliacao);
		this.dataDevolucao = dataDevolucao;
		this.status = "devolvido";
		this.avaliacao = avaliacao;
	}

	// Getters e Setters
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Usuario getLeitor() {
		return leitor;
	}

	public void setLeitor(Usuario leitor) {
		this.leitor = leitor;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public boolean isDevolucaoPendente() {
		return status.equals("pendente");
	}
	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int novaAvaliacao) {
		livro.trocarAvaliacao(this.avaliacao, novaAvaliacao);
		this.avaliacao = novaAvaliacao;
	}
}
