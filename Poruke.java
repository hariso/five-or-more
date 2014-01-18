/**
 * 	Klasa Poruke se koristi za instanciranje objekata koji æe u GUI-ju igre informirati
 * korisnika o sljedeæa tri broja odnosno sljedeæe tri boje koje dolaze te bodovima.
 */
import javax.swing.*;
import java.awt.*;

public class Poruke extends JPanel {
	JLabel poruka; //Prikazivanje poruke poput "Sljedeca tri broja"
	JLabel bodovi; //Prikazivanje bodova
	JButton boja1, boja2, boja3; 
	//Tri dugmeta koja cemo koristiti za prikazivanje sljedece tri boje
	JPanel boje =  new JPanel(); //Panel na kojem cemo prikazivati sljedece tri boje
	
	
	public Poruke()
	{
		setLayout(new BorderLayout());
		
		poruka = new JLabel("Sljedeæa tri: ");
		bodovi = new JLabel("Bodovi:        0");		
		
		boja1 =  new JButton();
		boja1.setPreferredSize(new Dimension (50, 50));
		boja1.setVisible(false);		
		
		boja2 = new  JButton();		
		boja2.setPreferredSize(new Dimension (50, 50));
		boja2.setVisible(false);
		
		boja3 = new JButton ();
		boja3.setPreferredSize(new Dimension (50, 50));
		boja3.setVisible(false);
		
		add(poruka, BorderLayout.WEST);	
		
		boje.add(boja1);
		boje.add(boja2);
		boje.add(boja3);
		
		add(boje, BorderLayout.CENTER);
		
		add(bodovi, BorderLayout.EAST);	
	}
	
	public void postaviBodove (int b)
	{
		bodovi.setText("Bodovi:        " + b);
	}
	
	//postaviPoruku prima niz brojeva, i te brojeve "trpa" u poruku
	public void postaviPoruku (int [] a)
	{
		poruka.setText("Sljedeæa tri:   "+a[0] +"   "+a[1] +"   "+a[2]);
	}
	
	//Tri boje proslijedjene kao parametar postavlja kao sljedece tri
	public void postaviPorukuSaBojama (Color b1, Color b2, Color b3)
	{
		poruka.setText("Sljedeæe tri boje");
		boja1.setBackground(b1);
		boja2.setBackground(b2);
		boja3.setBackground(b3);
	}
	
	//Skriva odnosno pokazuje dugmice. Koristimo pri mijenjaju tema
	public void pokaziDugmice (boolean arg)
	{
		boja1.setVisible(arg);
		boja2.setVisible(arg);
		boja3.setVisible(arg);
	}
}
