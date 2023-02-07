package br.com.felipe.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.felipe.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getLinhas()));
	
		tabuleiro.paraCadaCampo(c->add(new BotaoCampo(c)));
		
		tabuleiro.registarObservador(e ->{
			//TODO mostrar resultado do jogo para usuario
			SwingUtilities.invokeLater(()->{//mostra se ganhou ou se perdeu invocando depois se ganhou ou se perdeu posteriormente
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "Ganhou mlk bom :)");
				}else {
					JOptionPane.showMessageDialog(this, "perdeu já eras :(");
				}
				
				tabuleiro.reiniciar();
			});
			
		});
	}
}
