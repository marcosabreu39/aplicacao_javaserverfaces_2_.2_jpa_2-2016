package br.agenda.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.agenda.controller.MenuController;
import br.agenda.util.Util;

@WebFilter(urlPatterns = { "/alterarCadastro.jsf/*", "/adicionarContato.jsf/*", "/listarContatos.jsf/*" })
public class Filtro implements javax.servlet.Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			String logado = "";

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			HttpSession session = req.getSession(false);
			
			session = session == null ? req.getSession(true) : session;

			// session.setMaxInactiveInterval(60);

			logado = (String) session.getAttribute("login");

			if (logado == null || logado.equals("")) {

				res.sendRedirect(req.getContextPath() + "/index.jsf");

			} else {
				chain.doFilter(request, response);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
