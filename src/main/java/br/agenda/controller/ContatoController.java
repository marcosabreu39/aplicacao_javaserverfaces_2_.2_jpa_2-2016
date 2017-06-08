package br.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.agenda.dao.ContatoDao;
import br.agenda.dao.UsuarioDao;
import br.agenda.filter.Filtro;
import br.agenda.model.Contato;
import br.agenda.model.Usuario;
import br.agenda.util.Util;

@ViewScoped
@ManagedBean(name = "contatoBean")
public class ContatoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7621591580033576379L;

	private Contato contato;

	private Contato contatoSelecionado;

	private Contato contatoParaAtualizar;

	private Usuario usuario;

	@EJB
	private ContatoDao contatoDao;

	@EJB
	private UsuarioDao usuarioDao;

	private Util util = new Util();

	Filtro filtro = new Filtro();

	private List<Contato> contatosPorNome;

	private List<Contato> contatosPorData;

	@PostConstruct
	public void init() {
		contato = new Contato();
	}

	public List<Contato> getContatosPorNome() {

		return contatosPorNome;
	}

	public void setContatosPorNome(List<Contato> contatosPorNome) {
		this.contatosPorNome = contatosPorNome;
	}

	public List<Contato> getContatosPorData() {

		return contatosPorData;
	}

	public void setContatosPorData(List<Contato> contatosPorData) {
		this.contatosPorData = contatosPorData;
	}

	public void popularContatosPorNome() {

		setContatosPorNome(listarContatos("nome"));

		contatosPorData = null;

	}

	public void popularContatosPorData() {

		setContatosPorData(listarContatos("dataCadastro"));

		contatosPorNome = null;

	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Contato getContatoSelecionado() {
		return contatoSelecionado;
	}

	public void setContatoSelecionado(Contato contatoSelecionado) {
		this.contatoSelecionado = contatoSelecionado;
	}

	public Contato getContatoParaAtualizar() {
		return contatoParaAtualizar;
	}

	public void setContatoParaAtualizar(Contato contatoParaAtualizar) {
		this.contatoParaAtualizar = contatoParaAtualizar;
	}

	public Boolean emailApto(String email) {
		boolean apto = true;

		if (contatoDao.emailExiste(email)) {
			apto = false;

			try {
				util.msgErro("E-mail já cadastrado.", "email");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return apto;
	}

	public String insere() {
		try {
			if (emailApto(contato.getEmail())) {
				Usuario usuario = new Usuario();
				usuario.setLogin(util.pegarNaSessao());
				usuario.setId(usuarioDao.obterIdDoUsuario(util.pegarNaSessao()));
				contato.setDataCadastro(util.gerarDataEhoraAtual());
				contato.setIdUsuario(usuario);
				contatoDao.insere(contato);

				contato = new Contato();

				util.msgInfoGrowl("Mensagem de Agenda Jsf Jpa", "Contato inserido com sucesso.", "submit");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<Contato> listarContatos(String atributo) {
		List<Contato> contatos = new ArrayList<>();
		usuario = new Usuario();
		try {
			Integer idDoUsuario = usuarioDao.obterIdDoUsuario(util.pegarNaSessao());
			usuario.setId(idDoUsuario);
			contatos = contatoDao.listarOrdenadoPorAtributo(usuario, atributo);

			if (contatos.isEmpty()) {
				util.msgAtencao("Você não possui nenhum contato cadastrado.", "formPrimeiraAcao");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contatos;
	}

	public String obterContatoEspecifico(Contato contato) {

		setContatoSelecionado(contato);

		contatosPorNome = null;

		contatosPorData = null;

		return "/listarContatos.jsf?faces-redirect=true";

	}

	public String retornarContatoParaEditar(Contato contato) {

		setContatoParaAtualizar(contato);

		contatoSelecionado = null;

		contatosPorNome = null;

		contatosPorData = null;

		return "/listarContatos.jsf?faces-redirect=true";
	}

	public String atualizarContato(Contato contato) {
		try {
			contatoSelecionado = contato;

			contatoDao.atualiza(contato);

			contatoParaAtualizar = null;

			util.msgInfoGrowl("Mensagem de Agenda Jsf Jpa", "Contato atualizado com sucesso.", "salvar");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/listarContatos.jsf?faces-redirect=true";
	}

	public String excluirContato(Contato contato) {

		try {
			contatoDao.remove(contato);

			contatoSelecionado = null;

			contatoParaAtualizar = null;

			util.msgInfoGrowl("Mensagem de Agenda Jsf Jpa", "Contato Excluído com sucesso.", "excluir");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/listarContatos.jsf?faces-redirect=true";
	}

}
