package brq.com.senha.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.conn.jco.JCoRuntimeException;

import brq.com.senha.dao.UsuarioDAO;
import brq.com.senha.model.Usuario;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/register")
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private UsuarioDAO usuarioDao = new UsuarioDAO();
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int result = 0;
		String retorno = "";
		String erroJCO = "";
		int sysubrc = 0;
		Random r = new Random();
		String usuarioSap = request.getParameter("usuario");
		String email = request.getParameter("email");
		
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setLoginSap(usuarioSap);
		short randomNumber = (short)r.nextInt(Short.MAX_VALUE + 1);
		usuario.setSenha(String.valueOf(randomNumber));
		
		try {
			result = usuarioDao.registerUserSAP(usuario);
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			erroJCO = e.getMessage();
			e.printStackTrace();
		}
		
		catch(JCoRuntimeException ej) {
			erroJCO = ej.getMessage();
		}
		catch(Exception ex) {
			erroJCO = ex.getMessage();
		}
		
		//response.sendRedirect("");
		//doGet(request, response);
		if (result > 0)
		{
			retorno = "Solicitação realizada com sucesso.Um email foi enviado para " + usuario.getEmail() + " com um código para renovar sua senha."
					+ "Por favor aguarde...";
			request.getSession().setAttribute("message", retorno);
			request.getSession().setAttribute("sysubrc", result);
			request.getSession().setAttribute("login", usuario.getLoginSap());
			request.getSession().setAttribute("emailBRQ", usuario.getEmail());
			
	        //response.sendRedirect("index.jsp");
	        request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		else 
		{
			retorno = "Ocorreu um erro ao realizar sua solicitação: " + erroJCO ;
			request.getSession().setAttribute("message", retorno);
			request.getSession().setAttribute("sysubrc", result);
			request.getRequestDispatcher("pageRetorno.jsp").forward(request, response);
	        //response.sendRedirect("index.jsp");
		}
	}

}
