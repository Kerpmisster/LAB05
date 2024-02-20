package NguyenTuanAnh.com.beans;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.Connection;


@WebServlet("/productCreate")
public class ProductCreateServlet extends HomeServlet {
    private static final long serialVersionUID = 1L;

    public ProductCreateServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/productCreate.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException , IOException{
		request.setCharacterEncoding("UTF-8");
		String errorString = null;
		//lay du lie tren form
		String code =(String) request.getParameter("code");
		String name = (String) request.getParameter("name");
		String priceStr = (String) request.getParameter("price");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		}catch (Exception e) {
			errorString = e.getMessage();
			
		}
		Product product = new Product(code , name , price);
		if(errorString !=null) {
			request.setAttribute("errorString", errorString);
			request.setAttribute("product", product);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/productEdit.jsp");
			dispatcher.forward(request, response);
		}
		Connection conn = null;
		try {
			conn = ConnectSQL.getMSSQLConnection();
			ProductUtils.updateProduct(conn, product);
			response.sendRedirect(request.getContextPath()+"/productList");
		
		}catch (Exception e) {
			e.printStackTrace();
			errorString = e.getMessage();
			request.setAttribute("errorString", errorString);
			request.setAttribute("product", product);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/productEdit.jsp");
			dispatcher.forward(request, response);
		}
	}

}

