package br.agenda.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Util {

	public Date gerarDataEhoraAtual() {
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataEhoraCadastroSTR = fmt.format(data);
		Date dataEhoraAtual = null;
		try {
			dataEhoraAtual = (Date) fmt.parse(dataEhoraCadastroSTR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataEhoraAtual;
	}

	public void msgInfo(String mensagem, String idComponente) throws Exception {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
		FacesContext.getCurrentInstance().addMessage(idComponente, fm);
	}

	public void msgErro(String mensagem, String idComponente) throws Exception {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
		FacesContext.getCurrentInstance().addMessage(idComponente, fm);
	}

	public void msgAtencao(String mensagem, String idComponente) throws Exception {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null);
		FacesContext.getCurrentInstance().addMessage(idComponente, fm);
	}

	public void msgInfoGrowl(String detalhes, String mensagem, String componente) throws Exception {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, detalhes, mensagem);
		FacesContext.getCurrentInstance().addMessage(componente, fm);

	}

	public void msgErroGrowl(String detalhes, String mensagem, String componente) throws Exception {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, detalhes, mensagem);
		FacesContext.getCurrentInstance().addMessage(componente, fm);

	}

	public void colocarNaSessao(String login) throws Exception {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session = session == null ? (HttpSession) fc.getExternalContext().getSession(true) : session;
		session.setAttribute("login", login);

	}

	public String pegarNaSessao() throws Exception {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session = session == null ? (HttpSession) fc.getExternalContext().getSession(true) : session;
		String loginNaSessao = (String) session.getAttribute("login");

		return loginNaSessao;
	}

	public void sairDaSessao() throws Exception {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();

	}

	public String redirecionador() {

		return "/index.jsf?faces-redirect=true";

	}

}