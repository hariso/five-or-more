import java.util.Random;

public class Igra {
	
	/**
	 * Privatni atributi: 
	 */
	private int [][] tabela;
	private int vrste, kolone;
	private int figure;
	private int bodovi;
		private int trkac;
	private int potrcko;
	private int mjesto;
		
	//Prvi niz: dodajemo susjedne cvorove, drugi niz: biljezimo odakle smo dosli
	private int [][] niz;	
	private int [][] pomocniNiz ;
	
	//Niz u kojem cuvamo putanju od izv. do odr. polja
	private int [][] kretanjePolja;
	private int duzinaPutanje;
	
	private int [] nasumicnaTri = new int [3];	
	
	private int[][] pomocnaTab;
	private int brojac [][] = new int [3][3];//brojac u metodi za trazenje petica
	
	public Igra (int v, int k, int f) 
	{
		tabela = new int[v][k];	
		figure = f;
		vrste = v;
		kolone = k;
		
		for (int i = 0; i < vrste; i++)
			for (int j = 0; j <  kolone; j++)
			{
				tabela [i][j] = 0;			
			}
		
		nabaviTri();
		
		
	}
	
	public boolean postojiLiPut (int px, int py, int qx, int qy) 
	{
		niz = new int [vrste * kolone][2];		
		pomocniNiz = new int [vrste * kolone][2];
		
		//Postavimo niz i pomocniNiz na vrijednosti koje sigurno nisu indexi
		for (int i = 0; i < vrste * kolone; i++)
		{
			niz[i][0] = -1; 
			niz[i][1] = -1;			
			pomocniNiz[i][0] = -1; 
			pomocniNiz[i][1] = -1;
		}
		
		
		
		niz[1][0] = px; niz[1][1] = py;
		
		trkac = 1; //Aktivira se pri svakom dodavanju
		potrcko = 1; //Ide jedan poo jedan clan, provjerava da li ima susjedne itd.
		
		boolean pronadjen = ((px == qx) && (py == qy));
		
		while (potrcko <= trkac 
				&& !pronadjen) 
		{
			int a = niz[potrcko][0], b = niz[potrcko][1];
			
			if (valid (a+1, b) && tabela [a+1][b] == 0 && !pronadjen) 
				if ( !postoji(niz, a+1, b) ) 
				{
					trkac++;
					niz[trkac][0] = a +1; niz[trkac][1] = b;
					pomocniNiz[trkac][0] = a; pomocniNiz[trkac][1] = b;
					
					if ((a+1) == qx)
						if (b == qy)
						{
							pronadjen = true;
							mjesto = trkac;
						}
					
				}
			   
			
			if (valid (a, b+1) && tabela [a][b+1] == 0 && !pronadjen)
				if ( !postoji(niz, a, b+1) ) 
				{
					trkac++;
					niz[trkac][0] = a; niz[trkac][1] = b+1;
					pomocniNiz[trkac][0] = a; pomocniNiz[trkac][1] = b;
					if (a == qx && (b+1) == qy)
					{
						pronadjen = true;
						mjesto = trkac;
					}
				}
				
			if (valid (a-1, b) && tabela [a-1][b] == 0 && !pronadjen)
				if ( !postoji(niz, a-1, b)) 
				{
					trkac++;
					niz[trkac][0] = a-1; niz[trkac][1] = b;
					pomocniNiz[trkac][0] = a; pomocniNiz[trkac][1] = b;
					if ((a-1) == qx && b == qy)
					{
						pronadjen = true;
						mjesto = trkac;
					}
				}
				
			if (valid (a, b-1) && tabela [a][b-1] == 0 && !pronadjen)
				if ( !postoji(niz, a, b-1) ) 
				{
					trkac++;
					niz[trkac][0] = a; niz[trkac][1] = b-1;
					pomocniNiz[trkac][0] = a; pomocniNiz[trkac][1] = b;
					
					if (a == qx && (b - 1) == qy)
					{
						pronadjen = true;
						mjesto = trkac;
					}
				}
			
			potrcko += 1;
			
			//System.out.println("Mjesto: "+mjesto);
			}
		//Test dva niza
		//System.out.println("Niz: ");
		//ispisi(niz);
		//System.out.println("Pomocni niz: ");
		//ispisi(pomocniNiz);
		
		//Kretanje
		kretanjePolja = new int [vrste * kolone][2];
		duzinaPutanje = 0;
		int index = mjesto;
		while (niz[index][0] != -1)
		{
			kretanjePolja[duzinaPutanje][0] = niz[index][0];
			kretanjePolja[duzinaPutanje][1] = niz[index][1];
			duzinaPutanje++;
			index = indeksUNizu(niz, pomocniNiz[index][0], pomocniNiz[index][1]);
		}
		
		//System.out.println("Kretanje polja: ");
		//ispisi(kretanjePolja);
		
	return pronadjen;	
	}
	
	//-------------------------------------------------
	//Provjera da li je tabela prazna
	//-------------------------------------------------
	public boolean jePrazna ()
	{
		return (brojPraznih() == vrste * kolone);
	}
	   
	//-------------------------------------------------
	//Provjera da li je tabela popunjena.
	//-------------------------------------------------	
	public boolean jePuna ()
	{
		return (brojPraznih() == 0);
	}

	//-------------------------------------------------
	//Odabira tri slucajna broja
	//-------------------------------------------------
	
	
	
   public int [] nasumicnaTriBroja ()
   {
	  return nasumicnaTri;	 
   }
   
   private  void nabaviTri ()
	{	
	   //System.out.println("pozvana nabaviTri()");
		Random r = new Random();		   
		   for (int i = 0; i < 3; i++)
			   nasumicnaTri[i] = r.nextInt(figure) + 1;
		   
	}
   
   //-------------------------------------------------
   //Postavlja vec odabrana tri broja na "slucajna" mjesta
   //-------------------------------------------------
   public void postaviTri (int [] trojka) 
   {
	   for (int i = 0; i <= 2; i++)
	   {
		   int v,  k;		   
		   Random r = new Random();
		   
		   do {
			   v = r.nextInt(vrste);
			   k = r.nextInt(kolone);
		   } while (tabela[v][k] != 0);
		   
		   tabela[v][k] = trojka[i];
		   
	   }
	   nabaviTri();
   }
   
   //-------------------------------------------------
   //Metoda koja kao argument vraca tabelu koja opisuje stanje igre: 
   //zauzeta polja i njihove vrijednosti, slobodna polja itd.
   //-------------------------------------------------
   public int[][]  dajTabelu()
   {
	   int[][] rezultat = new int[vrste][kolone];
	   
	   for (int i = 0; i < vrste; i++)
	   {
		   for (int j = 0; j <  kolone; j++)
				rezultat[i][j]  = tabela [i][j];
	   }
			   
	   return rezultat;
   }
   
   //-------------------------------------------------
   //Metoda koja preseljava jedno polje na drugo mjesto
   //-------------------------------------------------
   public boolean premjesti (int prvix, int prviy, int drugix, int drugiy) 
   {
	   if (
			   ! postojiLiPut(prvix, prviy, drugix, drugiy) 
			   || (tabela[drugix][drugiy] != 0)
			   || (prvix < 0)
			   || (prvix > vrste)
			   || (prviy < 0)
			   || (prviy > kolone)
			   || (drugix < 0)
			   || (drugix > vrste)
			   || (drugiy < 0)
			   || (drugiy > kolone)
			   
	)
		   return false;
	   
	   else 
	   {
		   tabela[drugix][drugiy] = tabela[prvix][prviy];
		  // pom[drugix][drugiy] = 0;
		   tabela[prvix][prviy] = 0;
		   //pom[prvix][prviy] = 	1;

		   
		   return true;
	   }
   }
   
   //-------------------------------------------------
   //Provjera da li zadato polje ima slobodne susjedne
   //-------------------------------------------------
   private boolean imaSusjednih (int a, int b) 
   {
	   boolean result = false;
	   
	   if (valid (a+1, b))
		   if (tabela [a+1][b] == 0)
			   result = true;
	   
	   if (valid (a, b+1))
		   if (tabela [a][b+1] == 0)
			   result = true;
	   
	   if (valid (a-1, b))
		   if (tabela [a-1][b] == 0)
			   result = true;
	   
	   if (valid (a, b-1))
		   if (tabela [a][b-1] == 0)
			   result = true;  
	
	   return result;
		   
   }
   
   //-------------------------------------------------
   //Provjera da li su indexi "inbounds"
   //-------------------------------------------------
   public boolean valid (int x, int y)   
   {
	   boolean res = false;
	   
	   if (x >= 0 && x < vrste && y >= 0 && y < kolone)
		   res = true;
	   
	   return res;
   }
   
   //-------------------------------------------------
   //Postoji li dati par brojeva u matrici m x 2
   //-------------------------------------------------   
   private boolean postoji (int [][] niz, int a, int b)
   {
	  return (indeksUNizu(niz, a, b) != -1);
   }
   
   //-------------------------------------------------
   //Pronadji dati par brojeva u matrici m x 2
   //-------------------------------------------------   
   private int indeksUNizu (int [][] niz, int a, int b)
   {
	   int result = -1;
	   
	   for (int i = 0; i < niz.length; i++)
		   if (niz[i][0] == a && niz[i][1] == b)
			   result = i;
	   
	   return result;
   }   
   
   //-------------------------------------------------
   //Postoji li petica
   //-------------------------------------------------   
   
   public boolean postojiPetica (int argx, int argy) 
	{
	   //Pomocna za trazenje petice
	  boolean rezultat = false;
	  pomocnaTab = new int[vrste][kolone];
	  for (int i = 0; i < 3; i++)
		  for (int j = 0; j < 3; j++)
			  {
			  brojac[i][j] = 0;			  
			  }
	  
	   for (int i = vrste - 1; i >= 0; i--)
			for (int j = kolone - 1; j >= 0; j--)
				pomocnaTab[i][j] = 0;
		
		pomocnaTab[argx][argy] = 1;	
		
		
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
			{	
				
				if ((i!=0) || (j!=0))
				{	//provjera da li smo eventualno na samom polju...		
					
					int ix = i;
					int iy= j;
					//System.out.println(""+ix+"");
					while (
							valid(argx + ix, argy + iy)
							&& (tabela[argx + ix][argy+iy] == tabela[argx][argy]) 
							)
					{
						pomocnaTab[argx + ix][argy+ iy] = 1;						
						ix += i;
						iy += j;
						brojac[i+1][j+1]++;
					}
				}
				
				//System.out.println("brojac["+i+"]["+j+"]: "+brojac[i+1][j+1]);	
			}
		//System.out.println("=================");
		//System.out.println();
		
		int noviBodovi = 0;
		for (int i = -1; i<= 1; i++)
			for (int j = -1; j <= 1; j++) //Prolazak svih "pravaca": lijevo, desno, gore desno dijagonala itd.
			{
				if (i != 0 || j != 0) //"Pravac" (0,0) je nepotreban
				{
					if ((brojac[i+1][j+1] + brojac[-i+1][-j+1] + 1) >= 5)
					{
						rezultat = true;
						tabela[argx][argy] = 0;
						for (int k = 1; k <= brojac[i+1][j+1]; k++)
							{
								tabela[argx + k*i][argy + k*j] = 0;
								noviBodovi++;					
							}
						
					}
				}
			}
	if (noviBodovi >= 4)
		noviBodovi++;
	bodovi += noviBodovi;
	//System.out.println("Bodovi: "+bodovi);
	return rezultat;
	}
   
   //-----------------------------------------
   //Vrati kretanje polja
   //-----------------------------------------
   public int [][] putanja ()
   {
	   return kretanjePolja;
   }
   
   public int duzinaPuta ()
   {
	   return duzinaPutanje;
   }

   public int brojPraznih ()
 {
	 int brojac = 0;
	 for (int i = 0; i < vrste; i++)
	 	for (int j = 0; j < kolone; j++)
	 		if (tabela[i][j] == 0)
	 			brojac++;		 
	
	return brojac;
	
 }
 
   public void ispisi (int [][] mat)
 {
	 for (int i = 0; i < mat.length; i++)
	 {
		 for (int j = 0; j < mat[i].length; j++)
			 System.out.print(mat[i][j]+" ");
		 System.out.println();
	 }
 }

   public int dajBodove ()
  {
	return bodovi;  
  }
  //KRAJ KLASE
}