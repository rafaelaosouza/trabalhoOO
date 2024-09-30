package com.trabalho.oo.biblioteca.gui;

import com.trabalho.oo.biblioteca.gui.emprestimo.TelaDevolucao;
import com.trabalho.oo.biblioteca.gui.emprestimo.TelaEmprestimo;
import com.trabalho.oo.biblioteca.gui.emprestimo.TelaHistoricoEmprestimo;
import com.trabalho.oo.biblioteca.gui.livro.categoria.TelaCadastroCategoria;
import com.trabalho.oo.biblioteca.gui.livro.TelaCadastroLivros;
import com.trabalho.oo.biblioteca.gui.livro.TelaListagemLivros;
import com.trabalho.oo.biblioteca.gui.livro.categoria.TelaListagemCategorias;
import com.trabalho.oo.biblioteca.gui.usuario.TelaCadastro;
import com.trabalho.oo.biblioteca.gui.usuario.TelaListagemUsuarios;
import com.trabalho.oo.biblioteca.gui.usuario.TelaLogin;
import com.trabalho.oo.biblioteca.model.Leitor;
import com.trabalho.oo.biblioteca.service.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenu extends JFrame {
	private Sistema sistema;

	public TelaMenu(Sistema sistema) {
		this.sistema = sistema;

		setTitle("Menu Principal");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		JLabel bemVindoLabel = new JLabel("Seja bem vindo " + sistema.getUsuarioLogado().getTipo() + "!\n Selecione o que deseja:");
		bemVindoLabel.setBounds(75, 0, 300, 30);
		add(bemVindoLabel);

		JButton listarLivrosButton = new JButton("Listar Livros");
		listarLivrosButton.setBounds(100, 30, 200, 30);
		add(listarLivrosButton);
		listarLivrosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaListagemLivros(sistema).setVisible(true);
				dispose();
			}
		});

		JButton emprestimoButton = new JButton("Empréstimo");
		emprestimoButton.setBounds(100, 70, 200, 30);
		add(emprestimoButton);

		JButton devolucaoButton = new JButton("Devolução");
		devolucaoButton.setBounds(100, 110, 200, 30);
		add(devolucaoButton);

		JButton historicoButton = new JButton("Histórico de Empréstimos");
		historicoButton.setBounds(100, 150, 200, 30);
		add(historicoButton);

		emprestimoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaEmprestimo telaEmprestimo = new TelaEmprestimo(sistema);
				telaEmprestimo.setVisible(true);
				dispose();
			}
		});

		devolucaoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaDevolucao telaDevolucao = new TelaDevolucao(sistema);
				telaDevolucao.setVisible(true);
				dispose();
			}
		});

		historicoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TelaHistoricoEmprestimo(sistema, sistema.getUsuarioLogado()).setVisible(true);
				dispose();
			}
		});

		if (sistema.getUsuarioLogado() != null && sistema.getUsuarioLogado().isAdmin()) {
			JButton cadastroLivroButton = new JButton("Cadastro de Livro");
			cadastroLivroButton.setBounds(100, 190, 200, 30);
			add(cadastroLivroButton);

			JButton cadastroCategoriaButton = new JButton("Cadastro de Categoria");
			cadastroCategoriaButton.setBounds(100, 230, 200, 30);
			add(cadastroCategoriaButton);

			JButton listarCategoriaButton = new JButton("Listar Categorias");
			listarCategoriaButton.setBounds(100, 270, 200, 30);
			add(listarCategoriaButton);

			JButton cadastroAdminButton = new JButton("Cadastro de Admin");
			cadastroAdminButton.setBounds(100, 310, 200, 30);
			add(cadastroAdminButton);

			JButton listarUsuariosButton = new JButton("Listar Usuários");
			listarUsuariosButton.setBounds(100, 350, 200, 30);
			add(listarUsuariosButton);

			cadastroLivroButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TelaCadastroLivros telaCadastroLivros = new TelaCadastroLivros(sistema);
					telaCadastroLivros.setVisible(true);
					dispose();
				}
			});

			cadastroCategoriaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TelaCadastroCategoria telaCadastroCategoria = new TelaCadastroCategoria(sistema);
					telaCadastroCategoria.setVisible(true);
					dispose();
				}
			});

			listarCategoriaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new TelaListagemCategorias(sistema).setVisible(true);
					dispose();
				}
			});

			cadastroAdminButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TelaCadastro telaCadastro = new TelaCadastro(sistema, true);
					telaCadastro.setVisible(true);
					dispose();
				}
			});

			listarUsuariosButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new TelaListagemUsuarios(sistema, sistema.getUsuarioLogado()).setVisible(true);
					dispose();
				}
			});
		}

		JButton sairButton = new JButton("Sair");
		sairButton.setBounds(100, 390, 200, 30);
		sairButton.setForeground(Color.BLACK);
		sairButton.setBackground(Color.RED);
		add(sairButton);

		sairButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaLogin telaLogin = new TelaLogin(sistema);
				telaLogin.setVisible(true);
				sistema.deslogar();
				dispose();
				dispose();
			}
		});
	}
}
