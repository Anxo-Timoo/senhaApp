package brq.com.senha.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import brq.com.senha.dao.UsuarioDAO;
import brq.com.senha.model.Usuario;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/changepwd")
public class SenhaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private UsuarioDAO usuarioDao = new UsuarioDAO();
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SenhaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean result = false;
		String retorno = "";
		String erroJCO ="";
		int sysubrc = 0;
		String usuarioSap = request.getParameter("login");
		String email = request.getParameter("email");
		String codigo = request.getParameter("codigo");
		Date date = new Date(); 
		// 
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		int ano = cal.get(Calendar.YEAR);
		
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setLoginSap(usuarioSap);
		usuario.setSenha(codigo);
		
		try {
			result = usuarioDao.changeUserPwd(usuario);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		catch(Exception ex)
		{
			erroJCO = ex.getMessage();
		}
		
		//response.sendRedirect("");
		//doGet(request, response);
		if (result)
		{
			retorno = "Sua senha foi reinicializada para: Sap@brq"+Integer.toString(ano)
			       +". Lembre-se: sua senha deve conter uma letra maiúscula, "
			       + "um caractere especial,um número e no mínimo oito letras."; 
			request.getSession().setAttribute("message", retorno);
			request.getSession().setAttribute("sysubrc", 0);
	        //response.sendRedirect("index.jsp");
	        request.getRequestDispatcher("pageRetorno.jsp").forward(request, response);
		}
		else 
		{
			retorno = "Ocorreu um erro ao realizar sua solicitação: " + erroJCO;
			request.getSession().setAttribute("message", retorno);
			request.getSession().setAttribute("sysubrc", 1);
			request.getRequestDispatcher("pageRetorno.jsp").forward(request, response);
	        //response.sendRedirect("index.jsp");
		}
	}

}
