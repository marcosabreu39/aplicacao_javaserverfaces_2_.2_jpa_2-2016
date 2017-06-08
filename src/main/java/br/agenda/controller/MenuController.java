package br.agenda.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.graphicimage.GraphicImage;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import br.agenda.filter.Filtro;
import br.agenda.util.Util;

@ManagedBean(name = "menuView")
@ViewScoped
public class MenuController implements Serializable {

	/**
	 * nullreturn null;
	 */
	private static final long serialVersionUID = 8365679822632441695L;

	private MenuModel model;
	private DefaultMenuItem menuHome;
	private DefaultSubMenu menuCadastro;
	private DefaultMenuItem menuLogin;
	private DefaultSubMenu menuCadastroLogado;
	private DefaultSubMenu menuUsuario;
	private DefaultSubMenu menuContato;

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	@PostConstruct
	public void init() {
		resetarComponentes();
		model = new DefaultMenuModel();

		menuHome();

		filtrarAcesso();

	}

	private void menuHome() {
		try {
			menuHome = new DefaultMenuItem("Home");
			menuHome.setUrl("/index.jsf");
			menuHome.setIcon("fa fa-home");
			model.addElement(menuHome);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void menuCadastro() throws Exception {
		menuCadastro = new DefaultSubMenu("Cadastro");
		DefaultMenuItem menuNovoCadastro = new DefaultMenuItem("Novo Cadastro");
		menuNovoCadastro.setUrl("/pages/usuario/cadastro.jsf");
		menuCadastro.addElement(menuNovoCadastro);
		model.addElement(menuCadastro);
	}

	private void menuLogin() throws Exception {
		menuLogin = new DefaultMenuItem("Login");
		menuLogin.setUrl("/pages/usuario/login.jsf");
		menuLogin.setStyle("position: absolute; right: 6px;");
		model.addElement(menuLogin);
	}

	private void menuCadastroLogado() throws Exception {
		menuCadastroLogado = new DefaultSubMenu("Cadastro");
		DefaultMenuItem menuAlterarCadastro = new DefaultMenuItem("Alterar Cadastro");
		menuAlterarCadastro.setUrl("/pages/usuario/alterarCadastro.jsf");
		menuCadastroLogado.addElement(menuAlterarCadastro);
		model.addElement(menuCadastroLogado);
	}

	private void menuUsuario() throws Exception {
		Util util = new Util();
		menuUsuario = new DefaultSubMenu(util.pegarNaSessao());
		menuUsuario.setIcon("fa fa-user-o");
		menuUsuario.setStyle("position: absolute; right: 6px;");
		DefaultMenuItem menuLogout = new DefaultMenuItem("Logout");
		menuLogout.setIcon("fa fa-power-off");
		menuLogout.setCommand("#{usuarioBean.sairDaSessao}");
		menuUsuario.addElement(menuLogout);
		model.addElement(menuUsuario);
	}

	private void menuContato() throws Exception {
		menuContato = new DefaultSubMenu("Contato");
		DefaultMenuItem menuAdicionarContato = new DefaultMenuItem("Adicionar Contato");
		menuAdicionarContato.setUrl("/pages/contato/adicionarContato.jsf");
		menuContato.addElement(menuAdicionarContato);
		DefaultMenuItem menuListarContatos = new DefaultMenuItem("Listar Contatos");
		menuListarContatos.setUrl("/pages/contato/listarContatos.jsf");
		menuListarContatos.setRendered(true);
		menuContato.addElement(menuListarContatos);
		model.addElement(menuContato);
	}

	private String filtrarAcesso() {
		Util util = new Util();

		String redirecionar = null;
		try {
			String naSessao = util.pegarNaSessao();
			if (naSessao == null || naSessao.equals("")) {

				menuCadastro();
				menuLogin();

				redirecionar = "/index.jsf?faces-redirect=true";

			} else {

				menuCadastroLogado();
				menuContato();
				menuUsuario();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return redirecionar;

	}

	private void resetarComponentes() {
		model = null;
		menuHome = null;
		menuCadastro = null;
		menuLogin = null;
		menuCadastroLogado = null;
		menuUsuario = null;

	}

	public String gerenciarFimDeSessao() {
		Util util = new Util();
		try {
			util.sairDaSessao();
			init();
			util.msgInfoGrowl("Mensagem de Agenda Jsf e Jpa", "Sessão Expirada.", "fimDeSessao");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/index.jsf?faces-redirect=true";

	}

	/*public void onIdle() {
		Util util = new Util();
		try {
			util.msgInfoGrowl("Mensagem de Agenda Jsf e Jpa", "Sessão Expirada.", null);
			gerenciarFimDeSessao();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onActive() {
		Util util = new Util();
		try {
			util.msgInfoGrowl("Mensagem de Agenda Jsf e Jpa", "Bem vindo novamente.", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
}
