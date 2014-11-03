package com.gigaspaces.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;


import com.gigaspaces.common.model.Order;
import com.gigaspaces.common.model.OrderState;
import com.gigaspaces.common.model.Payment;
import com.gigaspaces.common.model.PaymentState;
import com.gigaspaces.internal.client.cache.localcache.LocalCacheContainer;

import com.j_spaces.core.IJSpace;



public class ReconServlet extends HttpServlet {       
    
	protected GigaSpace gigaSpace;  

	@Override
	public void init() throws ServletException { 
		IJSpace mySpace = new SpaceProxyConfigurer("mySpace").lookupTimeout(20000).space(); 
		gigaSpace = new GigaSpaceConfigurer(mySpace).gigaSpace();	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("ReconciliationView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LocalCacheContainer localCatch = (LocalCacheContainer) getServletContext().getAttribute("localCache");
		
		HttpSession session = request.getSession(true);  
        
        if(request.getParameter("countButton") != null){
        	
        	
        	int Ordercount = gigaSpace.count(new Order());
        	session.setAttribute("orderAmount", Integer.toString(Ordercount));
        	
        	int paymentCount = gigaSpace.count(new Payment());
        	session.setAttribute("paymentAmount", Integer.toString(paymentCount));
        	
        	Order orphanTemplate = new Order();
        	orphanTemplate.setState(OrderState.ORPHANED);
        	
        	int orphanCount = gigaSpace.count(orphanTemplate);
        	session.setAttribute("orderOrphaned", Integer.toString(orphanCount));
        	
        	String orphanPercent = percentToReadableFormat(((double)orphanCount/(double) Ordercount) * 100d) ;
        	
        	session.setAttribute("orderOrphanPercent", orphanPercent);
        	
        	Payment priceAdjust = new Payment();
        	priceAdjust.setState(PaymentState.ADJUSTED);
        	
        	int priceCount = gigaSpace.count(priceAdjust);
        	session.setAttribute("paymentAdjusted", Integer.toString(priceCount));
        	
        	String pricepercent = percentToReadableFormat(((double)priceCount/(double)paymentCount) * 100d) ;
        	
        	session.setAttribute("paymentPercent", pricepercent );
        	
         }
      
                 
        request.getRequestDispatcher("ReconciliationView.jsp").forward(request, response);
	}
	
	private String percentToReadableFormat(double decimal){
		String raw = "" +decimal;
		if(raw.length() > 5){
			return raw.substring(0, 4) + " %";
		}else{
			return raw + "%";
		}
		//TODO:Simplify this
	}

}
