import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;


public class GrafickiInterfejs {
	static Panel noviPanel;
	static Poruke novePoruke; 
	//public static void main (String [] args)  
	//{
		//postaviGUI();
	//}
	
	//Metoda postaviGUI koja radi haman pa sve
	public static void postaviGUI ()
	{
		final JFrame prozor = new JFrame ("Pet ili vise");
		prozor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		prozor.setResizable(false);
		prozor.setLayout(new BorderLayout());
		prozor.setBackground(Color.green);
		
		//---------------------------------------------
		//Postavljanje panela za dugmice i za poruke
		//---------------------------------------------
		noviPanel = new Panel(10,10,5);
		novePoruke = new Poruke ();
		novePoruke.postaviPoruku(noviPanel.dajTri());
		//Poruke novePoruke = new Poruke();
		
		//---------------------------------------------
		//Postavljanje menija
		//---------------------------------------------

		JMenuBar meniBar = new JMenuBar();    	    
		
		//Meniji
		JMenu igra = new JMenu("Igra");
		igra.setMnemonic('I');
		
		JMenu opcije = new JMenu("Opcije");
		opcije.setMnemonic('O');
		
		JMenu pomoc = new JMenu("Pomoc");
		pomoc.setMnemonic('P');
		
		//---------------------------------------------
		//Menu items za menu Igra
		
		//igra_novaIgra
		JMenuItem igra_novaIgra = new JMenuItem("Nova igra");		
		igra_novaIgra.addActionListener(new ActionListener () 
		{
			public void actionPerformed (ActionEvent e)
			{	
				prozor.getContentPane().remove(noviPanel);				
				noviPanel = new Panel(10,10,5);
				prozor.getContentPane().add(noviPanel, BorderLayout.CENTER);			
				
				noviPanel.updateUI();
				
				prozor.getContentPane().remove(novePoruke);				
				novePoruke = new Poruke();
				novePoruke.postaviPoruku(noviPanel.dajTri());
				prozor.getContentPane().add(novePoruke, BorderLayout.SOUTH);
				novePoruke.updateUI();

				
			}
		});
		
		igra_novaIgra.setToolTipText("Zapoèni novu igru");
		igra_novaIgra.setMnemonic('N');	
		//igra_novaIgra.setAccelerator(KeyStroke.getKeyStrokeForEvent(KeyEvent.VK_F2));
		
		//igra_sacuvajIgru
		JMenuItem igra_sacuvajIgru = new JMenuItem("Saèuvaj igru");
		igra_sacuvajIgru.setMnemonic('S');
		igra_sacuvajIgru.setToolTipText("Saèuvaj ovu igru kako bih je mogao nastaviti kasnije, ako Bog da ;)");		
		igra_sacuvajIgru.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent arg0) {
    		String ime="";
    		while(ime.isEmpty()){
    			ime = JOptionPane.showInputDialog("Ime pod kojim cuvam igru: ");
    			if(ime==null) break;
    			}
        		if(ime!=null){
        			//snimi(ime);
        			}
        		}
    	});
		
		
		//igra_ucitajIgru
		JMenuItem igra_ucitajIgru = new JMenuItem("Uèitaj igru"); 
		igra_ucitajIgru.setToolTipText("Nastavi ranije zapoèetu igru");
		igra_ucitajIgru.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		String ime="";
    		String si="";
    		
    		while(ime.isEmpty()){
    			ime = JOptionPane.showInputDialog("Ime pod kojim cuvam igru: ");
    			if(ime==null) break;
    			}
    		
    		/*try{
    			FileInputStream ulaz = new FileInputStream(ime+".fm");
    			DataInputStream hrk = new DataInputStream(ulaz);
				InputStreamReader hrkin = new InputStreamReader(hrk);
				BufferedReader red = new BufferedReader(hrkin);
				String Ime = red.readLine();
				si = red.readLine();
		    	brRedova= new Integer(red.readLine()).intValue();
				brKolona= new Integer(red.readLine()).intValue();
				brFigura= new Integer(red.readLine()).intValue();
				brBodova=new Integer(red.readLine()).intValue();
				for(int i=0;i<brFigura;i++){
					nizFigura[i]= new Integer(red.readLine()).intValue();
					}
				for(int i=0; i<brRedova; i++){
					for(int j=0; j<brKolona; j++){
						matrica[i][j]= new Integer(red.readLine()).intValue();
						}
					}
				hrk.close();
				}
    		catch(Exception e){
    			}
    		
    		frame.setVisible(false);
    		frame.dispose();
      		
    		postaviDimenzije(brRedova, brKolona, brFigura, 3);
    		postaviFrame();
    		
    		SljedecenizFigura(brFigura);
    		UnistiPetPostaviTabelu(matrica,brRedova, brKolona); */
    		
    	}	
    });
		
		//igra_tabela
		JMenuItem igra_tabela = new JMenuItem("'Highscores'");
		igra_tabela.setToolTipText("Spisak prvaka u trošenju vremena igrajuæi ovu igricu");
		igra_tabela.setEnabled(false);
		
		//igra_izlaz
		JMenuItem igra_izlaz= new JMenuItem("Izlaz"); 
		igra_izlaz.setToolTipText("Napusti aplikaciju"); 
		igra_izlaz.setMnemonic('z');
		igra_izlaz.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_X, ActionEvent.ALT_MASK));
		
		//Listener za zatvaranje prozora
		igra_izlaz.addActionListener(new ActionListener () 
		{
			public void actionPerformed (ActionEvent e)
			{
				prozor.dispose();
			}
		});
		
		
		//---------------------------------------
		//Menu items za Opcije
		JMenuItem opcije_podesavanja= new JMenuItem("Podešavanja polja");
		opcije_podesavanja.setEnabled(false);
		opcije_podesavanja.setToolTipText("Podešavanje opcija vezanih za tablu: velièina, broj loptica itd.");
		
		opcije_podesavanja.setMnemonic('P');
		opcije_podesavanja.addActionListener(new ActionListener () 
		{
			public void actionPerformed (ActionEvent e)
			{
				
			}
		});
		
		
		JMenu opcije_tema= new JMenu("Tema ");
		opcije_tema.setToolTipText("Odabir brojeva/slièica");
		opcije_tema.setMnemonic('T');
		
		final JRadioButtonMenuItem brojevi = new JRadioButtonMenuItem("Brojevi");
		brojevi.setSelected(noviPanel.dajTemu());
		
//		Ako kliknemo na ovaj rabio button, koristimo brojeve
		brojevi.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				noviPanel.postaviTemu(true);
				//System.out.println("Brojevi: Pozvano noviPanel.postaviTemu()");
				noviPanel.iscrtajPolje();
				//System.out.println("Brojevi: Pozvano noviPanel.iscrtajPolje();");
				novePoruke.pokaziDugmice(false);
				novePoruke.postaviPoruku(noviPanel.dajTri());
				
			}
		}
		);

		final JRadioButtonMenuItem temaBoje = new JRadioButtonMenuItem("Boje");
		temaBoje.setSelected(!noviPanel.dajTemu());
		
		//Ako kliknemo na ovaj rabio button, koristimo boje
		temaBoje.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
			// TODO Auto-generated method stub
			noviPanel.postaviTemu(false);
			//System.out.println("Slicice: Pozvano noviPanel.postaviTemu()");
			noviPanel.iscrtajPolje();
			//System.out.println("Slicice: Pozvano noviPanel.iscrtajPolje();");
			
			int a = noviPanel.dajTri()[0];
			int b =  noviPanel.dajTri()[1];
			  int c = noviPanel.dajTri()[2];
				 
			  GrafickiInterfejs.novePoruke.postaviPorukuSaBojama(noviPanel.dajBoje()[a-1], noviPanel.dajBoje()[b-1], noviPanel.dajBoje()[c-1]);
			  novePoruke.pokaziDugmice(true);			
			
			}
			
		});
		
		ButtonGroup odabirTema = new ButtonGroup();
		odabirTema.add(brojevi);
		odabirTema.add(temaBoje);
		
		opcije_tema.add(brojevi);
		opcije_tema.add(temaBoje);
		
		
		//---------------------------------------------
		//Menu items za Pomoc
		JMenuItem pomoc_oProgramu= new JMenuItem("O programu..."); 
		pomoc_oProgramu.setToolTipText("Donate via PayPal! Ma šalim se...");
		pomoc_oProgramu.setMnemonic('O');
		pomoc_oProgramu.addActionListener(new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				String s = new  String ("Projekat:\n'Pet ili više'\n\nHaris Osmanagic\n\nPMF Sarajevo, 2009");
				JOptionPane.showMessageDialog(prozor,
					    s, 
					    "O programu", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		
		igra.add(igra_novaIgra); 
		igra.addSeparator(); 
		
		igra.add(igra_sacuvajIgru);
		igra.add(igra_ucitajIgru);
		igra.add(igra_tabela);
		
		igra.addSeparator();		
		igra.add(igra_izlaz); 
		
		opcije.add(opcije_podesavanja);
		opcije.add(opcije_tema);
		
		pomoc.add(pomoc_oProgramu);
		
		meniBar.add(igra); 
		meniBar.add(opcije); 
		meniBar.add(pomoc);		
		
		prozor.add(meniBar, BorderLayout.NORTH);		
		noviPanel = new Panel(10,10,5);
		novePoruke = new Poruke ();
		novePoruke.postaviPoruku(noviPanel.dajTri());
		//Poruke novePoruke = new Poruke();
		
		prozor.getContentPane().add(noviPanel, BorderLayout.CENTER);
		prozor.getContentPane().add(novePoruke, BorderLayout.SOUTH);

		prozor.pack();
		prozor.setVisible(true);	
	
	}
	
	//postavljanje meni-bara
	
	
}