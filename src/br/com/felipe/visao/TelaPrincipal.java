package br.com.felipe.visao;

import javax.swing.JFrame;

import br.com.felipe.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{
	
	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16,30,50);
		add(new PainelTabuleiro(tabuleiro));//painel trabuleiro será adicionado na tela principal
		setTitle("Campo Minado");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	
	}
	
	public static void main(String[] args) {
		new TelaPrincipal();
	}
}
