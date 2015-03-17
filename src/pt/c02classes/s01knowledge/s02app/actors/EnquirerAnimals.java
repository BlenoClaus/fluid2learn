package pt.c02classes.s01knowledge.s02app.actors;

import java.util.ArrayList;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IDeclaracao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerAnimals implements IEnquirer {

	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		/*Duas Colecoes que auxilia para nao repetir perguntas*/
		ArrayList<String> perguntasFeitas = new ArrayList<String>();
		ArrayList<String> respostasFeitas = new ArrayList<String>();
		
		/*	Testanto Tiranossauro :		*/
		boolean ehTiranosauro = testaAnimal("tiranossauro", responder, perguntasFeitas, respostasFeitas);
		
		if ( ehTiranosauro )
			return finalizaTentativa(responder,"tiranossauro");
		
		/*	Testanto Humano :	*/
		boolean ehHumano = testaAnimal("humano", responder, perguntasFeitas, respostasFeitas);
		
		if ( ehHumano )
			return finalizaTentativa(responder,"humano");
		
		/*	Testanto Aranha :	*/
		boolean ehAranha = testaAnimal("aranha", responder, perguntasFeitas, respostasFeitas);

		if ( ehAranha )
			return finalizaTentativa(responder,"aranha");
		
		/*	Testanto Camarao :	*/
		boolean ehCamarao = testaAnimal("camarao", responder, perguntasFeitas, respostasFeitas);

		if ( ehCamarao )
			return finalizaTentativa(responder,"camarao");
		
		/*	Testanto Pikachu :	*/
		boolean ehPikachu = testaAnimal("pikachu", responder, perguntasFeitas, respostasFeitas);

		if ( ehPikachu )
			return finalizaTentativa(responder,"pikachu");
		
		return false;	
	}
	
	private boolean testaAnimal( String animal, IResponder responder, ArrayList<String> s1, ArrayList<String> s2 )
	{
		
		IBaseConhecimento bc = new BaseConhecimento();
		bc.setScenario("animals");
		String pergunta, respostaEsperada, resposta ; 
		
		IObjetoConhecimento obj = bc.recuperaObjeto(animal);
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
