package pt.c02classes.s01knowledge.s02app.actors;
import java.util.ArrayList;
import java.util.Stack;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		
		Stack<Position> pilha = new Stack<Position>();
		Stack<String> resposta = new Stack<String>();
		Stack<String> respostaAux = new Stack<String>();
		ArrayList<Position> caminhoPassados = new ArrayList<Position>();
		
		Position p = new Position(0,0);
		pilha.push(p);
		caminhoPassados.add(p);
		resposta.push("Entrada");
		String temp = "";
		
		int x,y ;
		boolean acioneiPilha = false;
		
		while (!responder.ask("aqui").equalsIgnoreCase("saida"))
		{
			if ( (responder.ask("leste").equalsIgnoreCase("passagem")||responder.ask("leste").equalsIgnoreCase("saida")) && !jaPassei(caminhoPassados, (p.getX()+1),p.getY()) )
			{
				if ( acioneiPilha )
				{
					acioneiPilha = false ;
					resposta.push(temp);
				}
				x = p.getX()+1;
				y = p.getY()  ;
				p = new Position(x,y);
				pilha.push(p);
				resposta.push("Leste");
				caminhoPassados.add(p);
				responder.move("leste");
			}
			else if ( (responder.ask("norte").equalsIgnoreCase("passagem")||responder.ask("norte").equalsIgnoreCase("saida")) && !jaPassei(caminhoPassados, p.getX(),(p.getY()+1)) )
			{
				if ( acioneiPilha )
				{
					acioneiPilha = false ;
					resposta.push(temp);
				}
				x = p.getX()  ;
				y = p.getY()+1;
				p = new Position(x,y);
				pilha.push(p);
				resposta.push("Norte");
				caminhoPassados.add(p);
				responder.move("norte");
			}
			else if ( (responder.ask("sul").equalsIgnoreCase("passagem")||responder.ask("sul").equalsIgnoreCase("saida")) && !jaPassei(caminhoPassados, p.getX(),(p.getY()-1)) )
			{
				if ( acioneiPilha )
				{
					acioneiPilha = false ;
					resposta.push(temp);
				}
				x = p.getX()  ;
				y = p.getY()-1;
				p = new Position(x,y);
				pilha.push(p);
				resposta.push("Sul");
				caminhoPassados.add(p);
				responder.move("sul");
			}
			else if ( (responder.ask("oeste").equalsIgnoreCase("passagem")||responder.ask("oeste").equalsIgnoreCase("saida")) && !jaPassei(caminhoPassados, (p.getX()-1),p.getY()) )
			{
				if ( acioneiPilha )
				{
					acioneiPilha = false ;
					resposta.push(temp);
				}
				x = p.getX()-1;
				y = p.getY()  ;
				p = new Position(x,y);
				pilha.push(p);
				resposta.push("Oeste");
				caminhoPassados.add(p);
				responder.move("oeste");
			}
			else
			{
				acioneiPilha = true; 
				x = p.getX()  ;
				y = p.getY()  ;
				p = pilha.pop();
				temp = resposta.pop();
				
				int disX = p.getX() - x  ;
				int disY = p.getY() - y  ;
				
				if ( disX < 0 && disY == 0 )
					responder.move("oeste");
				else if ( disX >0 && disY == 0)
					responder.move("leste");
				else if ( disX == 0 && disY < 0)
					responder.move("sul");
				else if ( disX == 0 && disY > 0)
					responder.move("norte");
				
			}
			
		}
		
		resposta.push("Saida");
		
		while ( !resposta.empty() )
			respostaAux.push( resposta.pop() );
		
		if (responder.finalAnswer("cheguei"))
			System.out.println("Voce encontrou a saida!");
		else
			System.out.println("Fuem fuem fuem!");
		
		String res = respostaAux.pop();
		res += "-> ";
		
		while( !respostaAux.empty() )
			res += respostaAux.pop()+"-> ";
		
		System.out.println(res);
		
		return true;
		
	}
	
	private boolean jaPassei( ArrayList<Position> vet, int x, int y )
	{
		for ( Position p : vet )
			if ( p.getX() == x && p.getY() == y )
				return true;
		return false;
	}

}
