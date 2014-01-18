import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

import javax.swing.*;


public class Panel extends JPanel 
{
	private boolean klik = false;
	private boolean brojevi = true;	
	//public final int BROJEVI = 1;
	//public final int BOJE = 0;
	
	int izvornoPoljeX, izvornoPoljeY, odredisnoPoljeX, odredisnoPoljeY, dp, blic = 3;	

	private static int duzina;
	private static int visina;
	private static int figure;
	
	private Color [] boje;
	
	
	private Igra novaIgra; //= new Igra (duzina, visina, figure);
	

	private JButton [][] dugmici;	
		
	private Timer tajmerPutanje, tajmerBlica;
	
	//------------------------------------------
	//Konstruktor
	//------------------------------------------
	Panel(int duzinaArg, int visinaArg, int figureArg) 
	{
		duzina = duzinaArg;
		visina = visinaArg; 
		figure = figureArg;
		
		boje = new Color [figure];
	
		dugmici = new JButton [duzina][visina];
		                               
		novaIgra = new Igra (duzina, visina, figure);
		
		setLayout(new GridLayout(duzina, visina));
		Misji t = new Misji();
		postaviBoje();
		
		System.out.println("Duzina: "+duzina);
		System.out.println("Visina: "+visina);
		
		for (int i = 0; i < duzina; i++)
			for (int j = 0; j < visina; j++)
			{
				dugmici[i][j] = new JButton("");
				dugmici[i][j].setPreferredSize(new Dimension(50, 50));
				dugmici[i][j].setBackground(Color.lightGray);
				
				dugmici[i][j].setVisible(true);
				dugmici[i][j].addMouseListener(t);				
				add (dugmici[i][j]);
			}
		
		postaviTrojku();		
	}
	
	//-------------------------------------------
	//
	//-------------------------------------------
	public void postaviTrojku () 
	{
		novaIgra.postaviTri(novaIgra.nasumicnaTriBroja());	
		iscrtajPolje();
	}
	
	//-------------------------------------------
	//
	//-------------------------------------------
	private class Putanja implements ActionListener
	   {
		  
	      public void actionPerformed (ActionEvent event)
	      {
	    	  //int [] v = new int [3];
	    	  //v = novaIgra.nasumicnaTriBroja();
	    	  if (dp >= 0)
	    		 {
	    		  if (brojevi)
	    			  dugmici[novaIgra.putanja()[dp][0]][novaIgra.putanja()[dp][1]].setBackground(Color.green);
	    		  else
	    		  {
	    			  int tipFigure = novaIgra.dajTabelu()[odredisnoPoljeX][odredisnoPoljeY];			  
	    			  dugmici[novaIgra.putanja()[dp][0]][novaIgra.putanja()[dp][1]].setBackground(boje[tipFigure - 1]);
	    			  
	    		  }  
	    		  
	    		  //!!!!
	    		  
	    		  dugmici[novaIgra.putanja()[dp+1][0]][novaIgra.putanja()[dp+1][1]].setBackground(Color.lightGray);
	    		  System.out.println("Vrijednost dp+1   :"+(dp+1)+ "   novaIgra.putanja()[dp+1][0]:   "+novaIgra.putanja()[dp+1][0]+"   novaIgra.putanja()[dp+1][1]   "+novaIgra.putanja()[dp+1][1]);
	    		  
	    		 }
	    	  
	    	  dp--;
	    	  if (dp == -1)
	    	  {
	    		  dugmici[novaIgra.putanja()[dp+1][0]][novaIgra.putanja()[dp+1][1]].setBackground(Color.lightGray);
	    		  klik = false;
	    		  dugmici[izvornoPoljeX][izvornoPoljeY].setBackground(Color.lightGray);
	    		  
	    		  if (!novaIgra.postojiPetica(odredisnoPoljeX, odredisnoPoljeY))
	    		  {
	    			  //Ukoliko ima mjesta, postavljaj nova tri
	    			  if (novaIgra.brojPraznih() > 3)
	    			  {
	    				  if (novaIgra.brojPraznih() == duzina * visina )
	    				  {
	    					  JOptionPane.showMessageDialog(GrafickiInterfejs.noviPanel,
		    						    "Ne može više, što si igrao/la, igrao/la si!",
		    						    "Bravo ti!",
		    						    JOptionPane.INFORMATION_MESSAGE);
	    				  }
	    				  
	    				  else
	    				  {
	    					  postaviTrojku();
			    			  iscrtajPolje();
			    			  if (brojevi)
			    				  GrafickiInterfejs.novePoruke.postaviPoruku(novaIgra.nasumicnaTriBroja());
			    			  else
			    			  {
			    				  int a = novaIgra.nasumicnaTriBroja()[0];
			    				  int b = novaIgra.nasumicnaTriBroja()[1];
			    				  int c = novaIgra.nasumicnaTriBroja()[2];
			    					 
			    				  GrafickiInterfejs.novePoruke.postaviPorukuSaBojama(boje[a-1], boje[b-1], boje[c-1]);

			    			  }

	    				  }
	    				
	    					  
	    			  }
	    			  
	    			  else // ukoliko nema mjesta
	    			  {
	    				  JOptionPane.showMessageDialog(GrafickiInterfejs.noviPanel,
	    						    "Ne može više, što si igrao/la, igrao/la si!",
	    						    "Pojela maca!",
	    						    JOptionPane.ERROR_MESSAGE);
	    			  }
	    		  }	    			 
	    		  
	    		  else 
	    		//Ako postoji petica, ponistice se u pozivu if-a, samo treba iscrtati polje i azurirati bodove
	    			  {
	    			  	iscrtajPolje();
	    			  	GrafickiInterfejs.novePoruke.postaviBodove(novaIgra.dajBodove());	    			  	
	    			  }
	    		  
	    		  tajmerPutanje.stop();
	    		  
	    	  }
	      }
	    	  
	   }
	
	private class crveniBlic implements ActionListener 
	{
		public void actionPerformed (ActionEvent event)
		{
			for (int i = 0; i < duzina; i ++)
				for (int j = 0; j < visina; j++)
					//Metoda event.getSource() nam vraca objekat koji je u ovom slucaju kliknut.
					//Taj objekat poredimo sa svakim objektom /tj. dugmetom/ u nasoj matrici, da bismo nasli koji je tacno!
					if (event.getSource()==dugmici[i][j]) 
					{
						izvornoPoljeX = i;
						izvornoPoljeY = j;
					}
			if ((blic % 2) == 0) 
				if (brojevi)
					dugmici[izvornoPoljeX][izvornoPoljeY].setBackground(Color.lightGray);
				else
					dugmici[izvornoPoljeX][izvornoPoljeY].setBackground(boje[novaIgra.dajTabelu()[izvornoPoljeX][izvornoPoljeY] -1]);
			else 
				dugmici[izvornoPoljeX][izvornoPoljeY].setBackground(Color.red);
			
			blic--;
			
			if (blic == -1){
				klik = false;
				tajmerBlica.stop();
				
			}
				
			
		}
	}

	//-------------------------------------------
	//
	//-------------------------------------------
	private class Misji implements MouseListener
	{

		public void mousePressed (MouseEvent event) {}
		public void mouseDragged (MouseEvent event){}
		public void mouseClicked (MouseEvent event) //Mis je kliknut
		{
			if (klik == false) //Ukoliko prethodno nije nista klinuto, znaci ovo je prvi klik. A prvi klik uzima izvorisno polje!
			{
				for (int i = 0; i < duzina; i ++)
					for (int j = 0; j < visina; j++)
						//Metoda event.getSource() nam vraca objekat koji je u ovom slucaju kliknut.
						//Taj objekat poredimo sa svakim objektom /tj. dugmetom/ u nasoj matrici, da bismo nasli koji je tacno!
						if (event.getSource()==dugmici[i][j]) 
						{
							izvornoPoljeX = i;
							izvornoPoljeY = j;
						}
				
				//Ako polje NIJE prazno onda ga obojimo i varijablu KLIK postavimo na true i time oznacimo da je smo jednom kliknuli
				//te sljedeci cekamo odredisno polje
				if (novaIgra.dajTabelu()[izvornoPoljeX][izvornoPoljeY] != 0)
				{
					dugmici[izvornoPoljeX][izvornoPoljeY].setBackground(Color.yellow);
					klik = true;
				}
				

			}
			
			else //Ukoliko je klik = true, znaci vec smo dosli sa nalijepljenim "brojem"
			{
				for (int i = 0; i < duzina; i++)
					for (int j = 0; j < visina; j++)
						//Identifikujemo odredisno polje
						if (event.getSource()==dugmici[i][j])
						{
							odredisnoPoljeX = i;
							odredisnoPoljeY = j;
						}
				
				if (izvornoPoljeX == odredisnoPoljeX && izvornoPoljeY == odredisnoPoljeY) 
				{					
					if (brojevi)
						dugmici[izvornoPoljeX][izvornoPoljeY].setBackground(Color.lightGray);
					else
						dugmici[izvornoPoljeX][izvornoPoljeY].setBackground(boje[novaIgra.dajTabelu()[izvornoPoljeX][izvornoPoljeY] -1]);
						System.out.println("Izvor: "+izvornoPoljeX+izvornoPoljeY);
					
					klik = false;
				}
				else 
				{
					if (novaIgra.premjesti(izvornoPoljeX, izvornoPoljeY, odredisnoPoljeX, odredisnoPoljeY)) 
					{						
						dp = novaIgra.duzinaPuta() - 2;
						tajmerPutanje = new Timer(250, new Putanja());
						tajmerPutanje.start();						
					}
						else
						{
							dugmici[izvornoPoljeX][izvornoPoljeY].setBackground(Color.red);
							blic = 3;
							tajmerBlica = new Timer(300, new crveniBlic());
							tajmerBlica.start();
						}
							
				}
				
			}
			
							
		}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		public void mouseExited (MouseEvent event) {}
		public void mouseMoved (MouseEvent event) {}
}

	//--------------------------------------------------------------
	
	public void iscrtajPolje () 
	{
	if (brojevi)
	{
		
		for (int i = 0; i < duzina; i++)
			for (int j = 0; j < visina; j++)				
				{
					if (novaIgra.dajTabelu()[i][j] != 0)					
						dugmici[i][j].setText(""+novaIgra.dajTabelu()[i][j]);
					else
						dugmici[i][j].setText("");
					
					dugmici[i][j].setBackground(Color.lightGray);
				}
	}
	
	if (!brojevi)
	{

		for (int i = 0; i < duzina; i++)
			for (int j = 0; j < visina; j++)
			{
				if (novaIgra.dajTabelu()[i][j] != 0)
				{
					dugmici[i][j].setBackground(boje[novaIgra.dajTabelu()[i][j]-1]);
					dugmici[i][j].setText("");
				}					
				else
				{
					dugmici[i][j].setBackground(Color.lightGray);
					dugmici[i][j].setText("");
				}
					
			}
	}	

	
	}	

	public int [] dajTri ()
	{
		return novaIgra.nasumicnaTriBroja();
	}
	
	//Postavi temu
	public void postaviTemu(boolean arg)
	{
		//arg = true znaci brojevi se koriste, arg = false boje
		brojevi = arg;
	}
	
	//Metoda za pravljenje niza boja koje æemo koristiti za predstavaljanje polja
	private void postaviBoje ()
	{
		Random r = new Random();
		for (int i = 0; i < figure; i++)
		{
			int a,b,c;
			a = r.nextInt(256);
			b = r.nextInt(256);
			c = r.nextInt(256);
			boje[i] = new Color(a,b,c);
		}
	}
	
	//Daj temu
	public boolean dajTemu()
	{
		return brojevi;
	}
	
	public Color [] dajBoje ()
	{
		return boje;
	}
	
	public int dajBrojRedova ()
	{
		return visina;
	}
	
	public int dajBrojKolona ()
	{
		return duzina;
	}
}

	