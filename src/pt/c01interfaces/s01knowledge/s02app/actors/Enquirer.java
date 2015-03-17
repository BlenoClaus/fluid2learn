package pt.c01interfaces.s01knowledge.s02app.actors;

import java.util.ArrayList;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.impl.Declaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj;
	
	public Enquirer()
	{
	}
	
	@Override
	public void connect(IResponder responder)
	{
		/*Duas Colecoes que auxilia para nao repetir perguntas*/
		ArrayList<String> perguntasFeitas = new ArrayList<String>();
		ArrayList<String> respostasFeitas = new ArrayList<String>();
		
		/*	Testanto Tiranossauro :		*/
		boolean ehTiranosauro = testaAnimal("tiranossauro", responder, perguntasFeitas, respostasFeitas);
		
		if ( ehTiranosauro ){
			finalizaTentativa(responder,"tiranossauro");
			return;
		}
		
		/*	Testanto Humano :	*/
		boolean ehHumano = testaAnimal("humano", responder, perguntasFeitas, respostasFeitas);
		
		if ( ehHumano ){
			finalizaTentativa(responder,"humano");
			return;
		}
		
		/*	Testanto Aranha :	*/
		boolean ehAranha = testaAnimal("aranha", responder, perguntasFeitas, respostasFeitas);

		if ( ehAranha ){
			finalizaTentativa(responder,"aranha");
			return;
		}
		
		/*	Testanto Camarao :	*/
		boolean ehCamarao = testaAnimal("camarao", responder, perguntasFeitas, respostasFeitas);

		if ( ehCamarao ){
			finalizaTentativa(responder,"camarao");
			return;
		}
		
		/*	Testanto Pikachu :	*/
		boolean ehPikachu = testaAnimal("pikachu", responder, perguntasFeitas, respostasFeitas);

		if ( ehPikachu ){
			finalizaTentativa(responder,"pikachu");
			return;
		}
		
	}
	
	private boolean testaAnimal( String animal, IResponder responder, ArrayList<String> s1, ArrayList<String> s2 )
	{
		IBaseConhecimento bc = new BaseConhecimento();
		String pergunta, respostaEsperada, resposta ; 
		
		obj = bc.recuperaObjeto(animal);
		IDeclaracao decl = obj.primeira();
		
		boolean achou = true;
		while (decl != null && achou) {
			pergunta = decl.getPropriedade();
			respostaEsperada = decl.getValor();
			
			/* Caso ja tenha feito essa pergunta, busco sua resposta que ja foi salva
			 * e nao eh necessario utilizar a funcao ask */
			if ( s1.contains(pergunta))
				resposta = s2.get( s1.indexOf(pergunta) );
			else
			{
				resposta = responder.ask(pergunta);
				s1.add(pergunta);
				s2.add(resposta);
			}
			
			if (resposta.equalsIgnoreCase(respostaEsperada))
				decl = obj.proxima();
			else
				achou = false;		
		}
		
		return achou;
	}
	
	private boolean finalizaTentativa(IResponder responder, String animal)
	{
		boolean acertei = responder.finalAnswer(animal);
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");
		
		return acertei;
	}

}
