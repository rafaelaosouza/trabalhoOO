package com.trabalho.oo.biblioteca.service;

import com.trabalho.oo.biblioteca.model.Administrador;
import com.trabalho.oo.biblioteca.model.Categoria;
import com.trabalho.oo.biblioteca.model.Emprestimo;
import com.trabalho.oo.biblioteca.model.Leitor;
import com.trabalho.oo.biblioteca.model.Livro;
import com.trabalho.oo.biblioteca.model.Usuario;
import com.trabalho.oo.biblioteca.utils.LivroIndisponivelException;
import com.trabalho.oo.biblioteca.utils.NaoPodeSerExcluidoException;
import com.trabalho.oo.biblioteca.utils.UsuarioJaCadastradoException;
import com.trabalho.oo.biblioteca.utils.ValorInvalidoException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Sistema {
	private List<Usuario> usuarios;
	private List<Livro> livros;
	private List<Emprestimo> emprestimos;
	private List<Categoria> categorias;
	private Usuario usuarioLogado;

	public Sistema() throws ValorInvalidoException, UsuarioJaCadastradoException {
		this.usuarios = new ArrayList<>();
		this.livros = new ArrayList<>();
		this.emprestimos = new ArrayList<>();
		this.categorias = new ArrayList<>();
		this.usuarioLogado = null;
		carregarLivros();
		carregarUsuarios();
		carregarCategorias();
		carregarEmprestimos();

		popularSistema();
	}

	public void cadastrarUsuario(Usuario usuario) throws UsuarioJaCadastradoException {
		if(validaUsuario(usuario)){
			usuarios.add(usuario);
			salvarUsuarios();
		}
	}
	public void atualizarUsuario(Usuario usuario, String nome, Boolean isAdmin) throws ValorInvalidoException {
			usuario.setNome(nome);
			usuario.setAdmin(isAdmin);
			salvarUsuarios();
	}

	public Usuario login(String cpf, String senha) throws ValorInvalidoException {
		Usuario usuarioALogar = null;
		for (Usuario usuario : usuarios) {
			if (usuario.getCpf().equals(cpf) && usuario.getSenha().equals(senha)) {
				usuarioALogar = usuario;
				break;
			}
		}
		if(usuarioALogar == null) {
			throw new ValorInvalidoException("Usuário não encontrado.");
		}
		if(!usuarioALogar.getSenha().equals(senha)) {
			throw new ValorInvalidoException("Senha incorreta.");
		}
		usuarioLogado = usuarioALogar;
		return usuarioLogado;
	}

	public void deslogar() {
		usuarioLogado = null;
	}

	public List<Livro> getLivros() {
		carregarLivros();
		return livros;
	}

	public void carregarLivros() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("livros.dat"))) {
			livros = (List<Livro>) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de livros não encontrado. Criando um novo.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void salvarUsuarios() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"))) {
			oos.writeObject(usuarios);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarUsuarios() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("usuarios.dat"))) {
			usuarios = (List<Usuario>) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de usuários não encontrado. Criando um novo.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	public List<Categoria> getCategorias() {
		return categorias;
	}

	public boolean emprestarLivro(Usuario leitor, Livro livro) throws LivroIndisponivelException {
		if (livro.isDisponivel()) {
			Emprestimo emprestimo = new Emprestimo(livro, leitor, new Date());
			emprestimos.add(emprestimo);
			System.out.println(leitor.getNome() + " pegou emprestado o livro: " + livro.getTitulo());
			salvarEmprestimos();
			salvarLivros();
			carregarEmprestimos();
			return true;
		}
		else{
			throw new LivroIndisponivelException();
		}
	}

	public void adicionarCategoria(Categoria categoria) {
		if(getCategoriaPorNome(categoria.getNome()) == null){
			categorias.add(categoria);
			salvarCategorias();
		}
	}

	public void salvarCategorias() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("categorias.dat"))) {
			oos.writeObject(categorias);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarCategorias() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("categorias.dat"))) {
			categorias = (List<Categoria>) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de livros não encontrado. Criando um novo.");
		}catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void carregarEmprestimos() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("emprestimos.dat"))) {
			emprestimos = (List<Emprestimo>) ois.readObject();
			System.out.println("Empréstimos carregados: " + emprestimos.size());
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de empréstimos não encontrado, criando um novo.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void adicionarLivro(Livro livro) {
		livros.add(livro);
		salvarLivros();
	}

	public void salvarLivros() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("livros.dat"))) {
			oos.writeObject(livros);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvarEmprestimos() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("emprestimos.dat"))) {
			oos.writeObject(emprestimos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<Emprestimo> getEmprestimosPorLeitor(Usuario leitor) {
		List<Emprestimo> emprestimosLeitor = new ArrayList<>();
		for (Emprestimo emprestimo : emprestimos) {
			if (emprestimo.getLeitor().equals(leitor)) {
				emprestimosLeitor.add(emprestimo);
			}
		}
		return emprestimosLeitor;
	}

	public Emprestimo getEmprestimoPorLeitorELivroEStatus(Usuario leitor, Livro livro) {
		List<Emprestimo> emprestimosLeitor = getEmprestimosPorLeitor(leitor);
		for (Emprestimo emprestimo : emprestimosLeitor) {
			if (emprestimo.getLivro().equals(livro) && emprestimo.isDevolucaoPendente()) {
				return emprestimo;
			}
		}
		return null;
	}

	public void devolverLivro(Emprestimo emprestimo, int avaliacao) {
		emprestimo.devolver(new Date(), avaliacao);
		salvarEmprestimos();
		salvarLivros();

	}

	public void excluirLivro(Livro livro) throws NaoPodeSerExcluidoException {
		for (Emprestimo emprestimo : emprestimos) {
			if (emprestimo.getLivro().equals(livro)) {
				throw new NaoPodeSerExcluidoException("Livro não pode ser removido, pois há empréstimos associados a ele.");
			}
		}
		livros.remove(livro);
		salvarLivros();
	}

	public Usuario getUsuarioPorCpf(String cpf) {
		for (Usuario usuario : usuarios) {
			if (usuario.getCpf().equals(cpf)) {
				return usuario;
			}
		}
		return null;
	}

	public void removerUsuario(String cpf) {
		Usuario usuario = getUsuarioPorCpf(cpf);
		if (usuario != null) {
			usuarios.remove(usuario);
			salvarUsuarios();
		}
	}

	public void removerCategoriaPorNome(String nome) throws NaoPodeSerExcluidoException {
		Categoria categoriaARemover = null;
		for (Categoria categoria : categorias) {
			if (categoria.getNome().equalsIgnoreCase(nome)) {
				categoriaARemover = categoria;
				break;
			}
		}
		for (Livro livro : livros) {
			if (livro.getCategoria().equals(categoriaARemover)) {
				throw new NaoPodeSerExcluidoException("Categoria não pode ser removida, pois há livros associados a ela.");
			}
		}
		if (categoriaARemover != null) {
			categorias.remove(categoriaARemover);
			salvarCategorias();
			System.out.println("Categoria removida com sucesso: " + nome);
		} else {
			throw new NaoPodeSerExcluidoException("Categoria não encontrada: " + nome);
		}
	}
	public List<Emprestimo> getEmprestimosDoLeitor(Usuario leitor) {
		carregarEmprestimos();
		return emprestimos.stream().filter(
				emprestimo -> emprestimo.getLeitor().getCpf().equals(leitor.getCpf()))
				.collect(Collectors.toList());

	}

	public boolean validaUsuario(Usuario usuario) throws UsuarioJaCadastradoException {
		for (Usuario usuarioCadastrado : usuarios) {
			if (usuarioCadastrado.getCpf().equals(usuario.getCpf())) {
				throw new UsuarioJaCadastradoException();
			}
		}
		return true;
	}

	public Categoria getCategoriaPorNome(String nome) {
		for (Categoria categoria : categorias) {
			if (categoria.getNome().equalsIgnoreCase(nome)) {
				return categoria;
			}
		}
		return null;
	}

	public void atualizarEmprestimo(Emprestimo emprestimo, int avaliacao) {
		emprestimo.setAvaliacao(avaliacao);
		salvarEmprestimos();
	}

	public void popularSistema() throws ValorInvalidoException, UsuarioJaCadastradoException {
		if(categorias.size() == 0){
			adicionarCategoria(new Categoria("Ficção"));
			adicionarCategoria(new Categoria("Não-Ficção"));
			adicionarCategoria(new Categoria("Fantasia"));
			adicionarCategoria(new Categoria("Biografia"));
			adicionarCategoria(new Categoria("Ciência"));
			adicionarCategoria(new Categoria("Autoajuda"));
			adicionarCategoria(new Categoria("História"));
		}

		if(livros.size() == 0) {
			adicionarLivro(new Livro("Dom Casmurro", "Machado de Assis", "gsdv", categorias.get(0),
					5, 1899, "Livro clássico da literatura brasileira"));
			adicionarLivro(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "aaaa", categorias.get(2),
					3, 1954, "Livro de fantasia épica"));
			adicionarLivro(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "bbbb",
					categorias.get(0), 2, 1943, "Livro infantil"));
			adicionarLivro(new Livro("Sapiens: Uma Breve História da Humanidade", "Yuval Noah Harari", "cccc",
					categorias.get(4), 7, 2014, "Livro sobre a história da humanidade"));
			adicionarLivro(new Livro("1984", "George Orwell", "dddd", categorias.get(0),
					4, 1949, "Romance distópico sobre totalitarismo"));
			adicionarLivro(new Livro("A Biografia de Steve Jobs", "Walter Isaacson", "eeee", categorias.get(3),
					3, 2011, "Biografia autorizada de Steve Jobs"));
			adicionarLivro(new Livro("O Poder do Hábito", "Charles Duhigg", "ffff", categorias.get(5),
					6, 2012, "Livro sobre como os hábitos moldam nossas vidas"));
			adicionarLivro(new Livro("História do Brasil", "Boris Fausto", "gggg", categorias.get(6),
					2, 1994, "Uma análise da história do Brasil desde o descobrimento"));
		}

		if(usuarios.size() == 0) {
			Administrador admin = new Administrador("Admin Sobrenome", "58530940687", "admin123");
			cadastrarUsuario(admin);

			Leitor leitor = new Leitor("Leitor Sobrenome", "15354065690", "leitor123");
			cadastrarUsuario(leitor);

			try {
				emprestarLivro(admin, livros.get(0));
				devolverLivro(emprestimos.get(0), 5);

				emprestarLivro(admin, livros.get(3));

				emprestarLivro(leitor, livros.get(1));
				devolverLivro(emprestimos.get(2), 3);

				emprestarLivro(leitor, livros.get(4));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
