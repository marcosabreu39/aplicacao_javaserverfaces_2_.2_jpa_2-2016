package br.agenda.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.agenda.dao.UsuarioDao;
import br.agenda.filter.Filtro;
import br.agenda.model.Usuario;
import br.agenda.util.Util;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7960443192684297906L;

	private Usuario usuario;

	private Usuario usuarioParaEditar;

	private boolean senha;

	@EJB
	UsuarioDao usuarioDao;

	@PostConstruct
	public void init() {

		usuario = new Usuario();

		// usuarioParaEditar = new Usuario();

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioParaEditar() {
		return usuarioParaEditar;
	}

	public void setUsuarioParaEditar(Usuario usuarioParaEditar) {
		this.usuarioParaEditar = usuarioParaEditar;
	}

	public boolean isSenha() {
		return senha;
	}

	public void setSenha(boolean senha) {
		this.senha = senha;
	}

	public Boolean emailApto(String email) {
		boolean apto = true;

		if (usuarioDao.emailExiste(email)) {
			apto = false;
			Util util = new Util();
			try {
				util.msgErro("E-mail já cadastrado.", "email");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return apto;
	}

	public Boolean loginApto(String login) {
		boolean apto = true;

		if (usuarioDao.loginExiste(login)) {
			apto = false;
			Util util = new Util();
			try {
				util.msgErro("Login já cadastrado.", "login");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return apto;
	}

	public String insere() {
		if (loginApto(usuario.getLogin()) & emailApto(usuario.getEmail())) {
			Util util = new Util();
			usuario.setDataCadastro(util.gerarDataEhoraAtual());
			usuarioDao.insere(usuario);
			try {

				util.msgInfoGrowl("Mensagem de Agenda Jsf Jpa", "Cadastro concluído com sucesso.", "submit");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			usuario = null;
		}
		return null;

	}

	public String acessa() {
		Util util = new Util();

		String retorno = null;
		try {
			if (usuarioDao.checarUsuario(usuario)) {

				//util.sairDaSessao();

				util.colocarNaSessao(usuario.getLogin());

				// util.colocarNaSessao(usuario.getLogin());

				MenuController menu = new MenuController();
				menu.init();
				retorno = "/index.jsf?faces-redirect=true";
			} else {

				util.msgErroGrowl("Mensagem de Agenda Jsf Jpa", "Usuário ou senha incorreta.", "acessa");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	public String sairDaSessao() {
		Util util = new Util();

		try {
			util.sairDaSessao();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MenuController menu = new MenuController();
		menu.init();
		return "/index.jsf?faces-redirect=true";
	}

	public void popularUsuario() {
		setUsuario(usuarioDao.obterUsuario());
	}

	public void popularUsuarioParaEditar(Usuario usuario) {
		usuarioParaEditar = new Usuario();
		setUsuarioParaEditar(usuario);
		setUsuario(null);

		exibirSenha(false);
	}

	public String atualizarUsuario() {
		Util util = new Util();

		try {
			usuarioDao.atualiza(usuarioParaEditar);

			setUsuario(usuarioParaEditar);

			usuarioParaEditar = null;

			util.msgInfoGrowl("Mensagem de Agenda Jsf Jpa", "Usuário atualizado com sucesso.", "atualizar");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "null";
	}

	public void exibirSenha(Boolean opcao) {
		setSenha(opcao);

	}

}
