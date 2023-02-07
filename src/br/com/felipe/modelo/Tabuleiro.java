package br.com.felipe.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador{//testar toda a class

	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Campo>campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>>observadores = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		gerarCampos();
		associarOsVizinhos();
		sortearAsMinas();
	}
	
	public void paraCadaCampo(Consumer<Campo>funcao) {
		campos.forEach(funcao);
	}
	
	public void registarObservador(Consumer<ResultadoEvento>observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(boolean resultado) {
		observadores.stream()
		.forEach(o -> o.accept(new ResultadoEvento(resultado)));
		
	}
	public void abrir(int linhas, int colunas) {
	
		 campos.parallelStream()		
			.filter(c->c.getLinha() == linhas && c.getColuna()==colunas)
			.findFirst()//função terminadora retornando um optional
			.ifPresent(c->c.abrir());//s tiver presente faça tal coisa
		
	}
	
	
	public void alterarMarcacao(int linhas, int colunas) {
		campos.parallelStream()
		.filter(c->c.getLinha() == linhas && c.getColuna()==colunas)
		.findFirst()//função terminadora retornando um optional
		.ifPresent(c->c.alterarMarcacao());//s tiver presente faça tal coisa
	}
	
	//esse metodo vai definir quem pode ser vizinho de acordo com a proximidade
	private void associarOsVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2:campos) {
				c1.adicionarVizinho(c2);
				
			}
		}
		
	}

	private void gerarCampos() {
		for(int l = 0; l<linhas;l++) {
			for(int c = 0;c<colunas; c++) {
				Campo campo = new Campo(l,c);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
		
	}
	private void sortearAsMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c ->c.isMinado();
		
		do {
			
			int aleatorio = (int)(Math.random()*campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas<minas);
	}
	
	public boolean objetivoAlcancado() {//testar
		
		
		return campos.stream().allMatch(c -> c.objetivoAlcancado());//se ao percorrer campos ver que para cada atributo deu Match o objetivo vou alcançado por que vai retornar true
	}
	
	public void reiniciar() {
		campos.stream().forEach(c->c.reiniciar());
		sortearAsMinas();
	}
	
	

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		if(evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		}else if(objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	
	private void mostrarMinas() {
		
		campos.stream()
		.filter(c ->c.isMinado())
		.filter(c ->!c.isMarcado())
		.forEach(c -> c.setAberto(true));
	}
	
	
	
}
